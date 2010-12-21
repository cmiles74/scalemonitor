package com.nervestaple.scalemonitor;

import com.nervestaple.scalemonitor.commportidentifierwrapper.CommPortIdentifierWrapper;
import com.nervestaple.scalemonitor.commportidentifierwrapper.CommPortIdentifierWrapperFactory;
import com.nervestaple.scalemonitor.serialportreader.SerialPortReader;
import com.nervestaple.scalemonitor.serialportreader.SerialPortReaderException;
import com.nervestaple.scalemonitor.serialportreader.SerialPortReaderFactory;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.RawPacketParserException;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.ScalePacket;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.rawpacketparserfilter.RawPacketParserFilter;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.rawpacketparserfilter.RawPacketParserFilterListener;
import gnu.io.CommPortIdentifier;
import org.apache.log4j.Logger;

import java.util.Enumeration;

/**
 * Provides a test object for learning about the javax.comm package.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class TestPorts {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Creates a new TestPorts.
     */
    public TestPorts() {

        // get a list of ports
        logger.debug( "Getting a list of communication ports..." );
        Enumeration enumerationPorts = CommPortIdentifier.getPortIdentifiers();

        // loop through the list
        while( enumerationPorts.hasMoreElements() ) {

            // get the next port
            CommPortIdentifier commportid = (CommPortIdentifier ) enumerationPorts.nextElement();

            // wrap the port
            CommPortIdentifierWrapper commportidentifierwrapper =
                    CommPortIdentifierWrapperFactory.getCommPortIndentifierWrapper( commportid );

            // check to see if this is a serial port
            if( commportid.getPortType() == CommPortIdentifier.PORT_SERIAL ) {

                logger.debug( "Found a serial port " + commportid.getName() );

                // start listening to the port
                try {
                    listenToPort( commportidentifierwrapper );
                } catch ( Exception e ) {
                    logger.warn( commportid.getName() + ": " + e.getMessage() );
                }
            }
        }
    }

    /**
     * Opens the port and starts a new thread for listening to that port.
     * @param commportidentifierwrapper
     * @throws Exception
     */
    public void listenToPort( final CommPortIdentifierWrapper commportidentifierwrapper ) throws Exception {

        // get a serial port for the id
        final SerialPortReader serialportreader = SerialPortReaderFactory.getSerialPortReader( commportidentifierwrapper,
                                                                                               ScaleType.AND_EW_300A );

        // start a thread to read from the port
        Thread thread = new Thread( new Runnable() {
            public void run() {

                // create a new listener
                RawPacketParserFilterListener listener = new RawPacketParserFilterListener() {

                    /**
                     * Called when a new packet is filtered.
                     *
                     * @param andew300apacket
                     */
                    public void newPacket( ScalePacket andew300apacket ) {

                        logger.debug( andew300apacket );
                    }
                };

                // create a filter
                RawPacketParserFilter filter = null;
                try {
                    filter = new RawPacketParserFilter( ScaleType.AND_EW_300A, 0, 0 );
                } catch( RawPacketParserException e ) {
                    logger.warn( e );
                }

                // add out listener to the filter
                filter.addRawPacketParserFilterListener( listener );

                // add our filter listener to the port
                serialportreader.addSerialPortReaderListener( filter );

                // open the port
                try {
                    serialportreader.open();
                } catch ( SerialPortReaderException e ) {
                    logger.warn( e );
                }

                // sleep until it's time to quit
                try {
                    Thread.sleep( 50000 );
                } catch ( InterruptedException e ) {
                    logger.warn( e );
                }

                // close the port
                serialportreader.close();
            }
        });
        thread.start();
    }

    /**
     * Bootstraps the application.
     * @param args
     */
    public static void main( final String[] args ) {

        new TestPorts();
    }
}
