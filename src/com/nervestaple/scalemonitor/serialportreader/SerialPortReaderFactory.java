package com.nervestaple.scalemonitor.serialportreader;

import com.nervestaple.scalemonitor.ScaleType;
import com.nervestaple.scalemonitor.commportidentifierwrapper.CommPortIdentifierWrapper;
import com.nervestaple.scalemonitor.serialportreader.rawpacketreader.RawPacketReader;
import com.nervestaple.scalemonitor.serialportreader.rawpacketreader.RawPacketReaderException;
import com.nervestaple.scalemonitor.serialportreader.rawpacketreader.RawPacketReaderFactory;
import org.apache.log4j.Logger;

/**
 * Provides a factor for getting SerialPortReader objects.
 *
 * Created on Jul 10, 2005 at 3:33:10 PM
 *
 * @author miles
 * @version 1.0
 */
public class SerialPortReaderFactory {

    /**
     * Logger instance.
     */
    private static Logger logger = Logger.getLogger(
            "com.nervestaple.scalemonitor.serialportreader.SerialPortReaderFactory" );

    /**
     * Default start delay.
     */
    public final static int TIMER_START_DELAY = 0;

    /**
     * Default delay between checking port (milliseconds).
     */
    public final static int TIMER_DELAY = 200;

    /**
     * Default time to wait for the port to become open.
     */
    public final static int WAIT_OPEN_PORT = 2000;

    /**
     * Default buffer size.
     */
    public final static int BUFFER_SIZE = 10;

    /**
     * Creates a new SerialPortReader with default paramteters.
     * @param commportidentifierwrapper
     * @param scaleType
     * @return
     * @throws SerialPortReaderException
     */
    public static SerialPortReader getSerialPortReader( CommPortIdentifierWrapper commportidentifierwrapper,
                                                        ScaleType scaleType )
            throws SerialPortReaderException {

        SerialPortReader serialportreader = null;

        // create the new serial port reader
        serialportreader = doSerialPortReader( commportidentifierwrapper,
                                               scaleType,
                                               TIMER_START_DELAY,
                                               TIMER_DELAY,
                                               WAIT_OPEN_PORT,
                                               BUFFER_SIZE );

        return ( serialportreader );
    }
    
    /**
     * Creates a new SerialPortReader.
     * @param commportidentifierwrapper
     * @param scaleType
     * @param timerStartDelay
     * @param timerDelay
     * @param waitOpenPort
     * @param bufferSize
     * @return
     * @throws SerialPortReaderException
     */
    public static SerialPortReader getSerialPortReader( CommPortIdentifierWrapper commportidentifierwrapper,
                                                        ScaleType scaleType, int timerStartDelay, int timerDelay,
                                                        int waitOpenPort, int bufferSize )
            throws SerialPortReaderException {

        SerialPortReader serialportreader = null;

        // create the new serial port reader
        serialportreader = doSerialPortReader( commportidentifierwrapper,
                                                 scaleType,
                                                 timerStartDelay,
                                                 timerDelay,
                                                 waitOpenPort,
                                                 bufferSize );

        return ( serialportreader );
    }
    
    // private methods
    
    private static SerialPortReader doSerialPortReader( CommPortIdentifierWrapper commportidentifierwrapper, ScaleType scaleType,
                                                    int timerStartDelay, int timerDelay, int waitOpenPort,
                                                    int bufferSize )
            throws SerialPortReaderException {

        SerialPortReader serialportreader = null;

        try {

            // get a raw packet reader for the scale
            RawPacketReader rawpacketreader = RawPacketReaderFactory.getRawPacketReader( scaleType );

            // create the new serial port reader
            serialportreader = new SerialPortReader( commportidentifierwrapper,
                    scaleType,
                    rawpacketreader,
                    timerStartDelay,
                    timerDelay,
                    waitOpenPort,
                    scaleType.getEndOfLine(),
                    scaleType.getPacketSize(),
                    bufferSize );
        } catch( RawPacketReaderException e ) {

            throw new SerialPortReaderException( "Could not get a RawPacketReader for scale type " + scaleType, e );
        }

        return ( serialportreader );
    }
}
