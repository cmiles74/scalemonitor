package com.nervestaple.scalemonitor.serialportreader.rawpacketparser;

/**
 * Provides an exception class for the RawPacketParser.
 *
 * Created on Jul 10, 2005 at 6:18:08 PM
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class RawPacketParserException extends Exception {

    /**
     * Creates a new RawPacketParserException.
     */
    public RawPacketParserException() {
        super();
    }

    /**
     * Creates a new RawPacketParserException.
     *
     * @param message
     */
    public RawPacketParserException( String message ) {
        super( message );
    }

    /**
     * Creates a new RawPacketParserException.
     *
     * @param message
     * @param throwable
     */
    public RawPacketParserException( String message, Throwable throwable ) {
        super( message, throwable );
    }

    /**
     * Creates a new RawPacketParserException.
     *
     * @param throwable
     */
    public RawPacketParserException( Throwable throwable ) {
        super( throwable );
    }
}
