package com.nervestaple.scalemonitor.serialportreader;

/**
 *  Provides an exception class for the SerialPortReader.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class SerialPortReaderException extends Exception {

    /**
     * Creates a new SerialPortReaderException.
     */
    public SerialPortReaderException() {
         super();
     }

    /**
     * Creates a new SerialPortReaderException.
     * @param message
     */
     public SerialPortReaderException( String message ) {
        super( message );
    }

    /**
     * Creates a new SerialPortReaderException.
     * @param message
     * @param throwable
     */
     public SerialPortReaderException( String message, Throwable throwable ) {
         super( message, throwable );
    }

    /**
     * Creates a new SerialPortReaderException.
     * @param throwable
     */
    public SerialPortReaderException( Throwable throwable ) {
         super( throwable );
     }
}
