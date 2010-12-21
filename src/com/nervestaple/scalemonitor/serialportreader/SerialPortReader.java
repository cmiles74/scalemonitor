package com.nervestaple.scalemonitor.serialportreader;

import com.nervestaple.scalemonitor.ScaleType;
import com.nervestaple.scalemonitor.commportidentifierwrapper.CommPortIdentifierWrapper;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.RawPacket;
import com.nervestaple.scalemonitor.serialportreader.rawpacketreader.RawPacketReader;
import com.nervestaple.scalemonitor.serialportreader.rawpacketreader.RawPacketReaderException;
import com.nervestaple.scalemonitor.serialportwrapper.SerialPortWrapper;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import gnu.io.PortInUseException;
import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;

/**
 * Provides a class that can be extended to implement SerialPortReader objects.
 *
 * Created on Jul 10, 2005 at 3:20:56 PM
 *
 * @author miles
 * @version 1.0
 */
public class SerialPortReader {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Communication port identifier.
     */
    private CommPortIdentifierWrapper commportidentifierwrapper;

    /**
     * Scale type on the port.
     */
    private ScaleType scaletype;

    /**
     * Serial port instance.
     */
    private SerialPortWrapper serialportwrapper;

    /**
     * Buffer of read data.
     */
    private CircularFifoBuffer bufferData;

    /**
     * InputStream from the serial port.
     */
    private BufferedInputStream inputstream;

    /**
     * The packet being read.
     */
    private StringBuffer packet;

    /**
     * Timer for managin reads.
     */
    private Timer timer;

    /**
     * List of listeners.
     */
    private List listeners;

    /**
     * Initial timer delay.
     */
    private int timerStartDelay = 0;

    /**
     * Delay between timer firings.
     */
    private int timerDelay = 200;

    /**
     * Time to wait for the port to open.
     */
    private int waitOpenPort = 2000;

    /**
     * String used to demarcate a packet.
     */
    private String endOfLine = "\r\n";

    /**
     * Length of packet.
     */
    private int packetLength = 12;

    /**
     * Buffer size.
     */
    private int bufferSize = 10;

    /**
     * Raw packet reader.
     */
    private RawPacketReader rawpacketreader;

    /**
     * Creates a new SerialPortReader.
     *
     * @param commportidentifierwrapper Serial port
     */
    public SerialPortReader( CommPortIdentifierWrapper commportidentifierwrapper, ScaleType scaletype,
                             RawPacketReader rawpacketreader, int timerStartDelay, int timerDelay, int waitOpenPort,
                             String endOfLine, int packetLength, int bufferSize ) {

        Validate.isTrue( commportidentifierwrapper.getPortType() == CommPortIdentifier.PORT_SERIAL );

        // save a reference to the values
        this.scaletype = scaletype;
        this.commportidentifierwrapper = commportidentifierwrapper;
        this.rawpacketreader = rawpacketreader;
        this.timerStartDelay = timerStartDelay;
        this.timerDelay = timerDelay;
        this.waitOpenPort = waitOpenPort;
        this.endOfLine = endOfLine;
        this.packetLength = packetLength;
        this.bufferSize = bufferSize;

        // create an array to hold listeners
        listeners = new ArrayList();

        // start our first packet
        packet = new StringBuffer();
    }

    /**
     * Adds a new listener.
     *
     * @param listener
     */
    public void addSerialPortReaderListener( SerialPortReaderListener listener ) {

        if( !listeners.contains( listener ) ) {

            listeners.add( listener );
        }
    }

    /**
     * Removes a listener.
     *
     * @param listener
     */
    public void removeSerialPortReaderListener( SerialPortReaderListener listener ) {

        listeners.remove( listener );
    }

    /**
     * Returns an array of listeners.
     *
     * @return
     */
    public SerialPortReaderListener[] getSerialPortReaderListeners() {

        return ( ( SerialPortReaderListener[] )
                listeners.toArray( new SerialPortReaderListener[ listeners.size() ] ) );
    }

    /**
     * Opens the port and starts reading data into the buffer.
     *
     * @throws SerialPortReaderException
     */
    public void open() throws SerialPortReaderException {

        // open the port and get a serial port object
        try {
            serialportwrapper = commportidentifierwrapper.open( this.getClass().getName(), waitOpenPort );
            serialportwrapper.notifyOnDataAvailable( true );
        } catch( PortInUseException e ) {
            logger.warn( "Could not open the port", e );
        }

        // set port parameters
        try {
            serialportwrapper.setSerialPortParams( scaletype.getBaudRate(), scaletype.getDataBits(),
                    scaletype.getStopBits(), scaletype.getParity() );
        } catch( UnsupportedCommOperationException e ) {
            logger.warn( "Could not set port parameters", e );
        }

        // get an input stream to read from the port
        try {
            inputstream = new BufferedInputStream( serialportwrapper.getInputStream() );
        } catch( IOException e ) {
            throw new SerialPortReaderException( "Could not get an input stream: " + e );
        }

        // setup the buffer
        if( bufferData == null ) {
            bufferData = new CircularFifoBuffer( bufferSize );
        } else {
            bufferData.clear();
        }

        try {
            startPortReaderTimer();
        } catch( TooManyListenersException e ) {
            throw new SerialPortReaderException( "Could not listen to the port: " + e );
        }
    }

    /**
     * Closes the serial port.
     */
    public void close() {

        if( timer != null ) {

            // cancel and remove the timer
            timer.cancel();
            timer = null;
        }

        // close the input stream
        try {
            inputstream.close();
        } catch( IOException e ) {
            logger.warn( e );
        }

        // remove the event listener from the port
        try {
            serialportwrapper.removeEventListener();
        } catch( Exception e ) {
            logger.warn( e );
        }

        serialportwrapper.close();
    }

    /**
     * Returns a read-only version of the buffer.
     *
     * @return
     */
    public Buffer getBufferData() {

        return ( BufferUtils.unmodifiableBuffer( bufferData ) );
    }

    /**
     * Notifies listeners of the new packet.
     *
     * @param rawpacket
     */
    private void notifyListeners( RawPacket rawpacket ) {

        SerialPortReaderListener[] arrayListeners = getSerialPortReaderListeners();

        for( int index = 0; index < arrayListeners.length; index++ ) {

            try {
                arrayListeners[ index ].packetRead( rawpacket );
            } catch( Exception e ) {
                logger.warn( e, e );
            }
        }
    }

    /**
     * Starts a timer thread to read data from the buffer
     *
     * @throws TooManyListenersException
     */
    private void startPortReaderTimer() throws TooManyListenersException {

        serialportwrapper.addEventListener( new SerialPortEventListener() {
            public void serialEvent( SerialPortEvent event ) {

                if( timer == null ) {

                    timer = new Timer();

                    try {
                        clearBuffer();
                    } catch( SerialPortReaderException e ) {
                        logger.warn( e );
                    }

                    timer.schedule( new TimerTask() {
                        public void run() {

                            try {
                                doPortRead();
                            } catch( SerialPortReaderException e ) {
                                logger.warn( e );
                            }
                        }
                    }, timerStartDelay, timerDelay );
                }
            }
        } );
    }

    /**
     * Clears the buffer from the serial port.
     *
     * @throws SerialPortReaderException
     */
    private void clearBuffer() throws SerialPortReaderException {

        try {

            // read the data
            inputstream.read( new byte[ inputstream.available() ] );
        } catch( IOException e ) {
            throw new SerialPortReaderException( "Could not clear the buffer: " + e );
        }
    }

    /**
     * Reads data from the port and parses it into a packet.
     *
     * @throws SerialPortReaderException
     */
    private void doPortRead() throws SerialPortReaderException {

        try {
            while( inputstream.available() > 0 ) {

                // create an array to hold the data
                byte[] byteData = new byte[ inputstream.available() ];

                // read the data
                inputstream.read( byteData );
                String data = new String( byteData );
                logger.debug( data );

                // check to see if this chunk has the eol
                if( StringUtils.contains( data, endOfLine ) ) {

                    // split the string on the EOL
                    String[] chunks = StringUtils.split( data, endOfLine );

                    // loop through our chunks
                    for( int index = 0; index < chunks.length; index++ ) {

                        // add the chunk to the packet
                        packet.append( chunks[ index ] );

                        // do some sanity checking on the data
                        if( packet.length() == packetLength ) {

                            RawPacket rawpacket = null;

                            try {

                                // read the packet
                                rawpacket = rawpacketreader.getRawPacket( serialportwrapper.getName(), new Date(), packet.toString() );

                                // buffer the data
                                bufferData.add( rawpacket );

                                // notify listeners
                                notifyListeners( rawpacket );
                            } catch( RawPacketReaderException e ) {

                                // packet it bad, log a warning
                                logger.warn( "Bad Packet: " + e );
                            }
                        }

                        // clear the buffer
                        packet = new StringBuffer();
                    }
                } else {

                    if( data != null ) {

                        // add this data to the packet
                        packet.append( data );
                    }
                }
            }
        } catch( IOException e ) {
            throw new SerialPortReaderException( "Problem reading from the port: " + e );
        }
    }
}
