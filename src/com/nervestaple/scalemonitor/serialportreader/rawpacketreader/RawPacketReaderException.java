package com.nervestaple.scalemonitor.serialportreader.rawpacketreader;

/**
 * Provides an exception class for the RawPacketReader.
 *
 * Created on Jul 10, 2005 at 2:28:34 PM
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class RawPacketReaderException extends Exception {

    /**
     * Creates a new RawPacketReaderException.
     */
    public RawPacketReaderException() {
        super();
    }

    /**
     * Creates a new RawPacketReaderException.
     *
     * @param message
     */
    public RawPacketReaderException( String message ) {
        super( message );
    }

    /**
     * Creates a new RawPacketReaderException.
     *
     * @param message
     * @param throwable
     */
    public RawPacketReaderException( String message, Throwable throwable ) {
        super( message, throwable );
    }

    /**
     * Creates a new RawPacketReaderException.
     *
     * @param throwable
     */
    public RawPacketReaderException( Throwable throwable ) {
        super( throwable );
    }
}
