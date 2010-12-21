package com.nervestaple.scalemonitor.serialportwrapper;

import gnu.io.UnsupportedCommOperationException;
import gnu.io.SerialPortEventListener;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.io.IOException;
import java.util.TooManyListenersException;

/**
 * Provides a class that can be extended to wrap a SerialPort. The methods exposed are those that are used in the
 * application.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public abstract class SerialPortWrapper {

    /**
     * SerialPort to wrap.
     */
    protected SerialPort serialport;

    /**
     * Notify the listener when data is available.
     * @param value
     */
    public abstract void notifyOnDataAvailable( boolean value );

    /**
     * Sets the serial port parameters.
     * @param baud
     * @param databits
     * @param stopbits
     * @param parity
     * @throws UnsupportedCommOperationException
     */
    public abstract void setSerialPortParams( int baud, int databits, int stopbits, int parity )
            throws UnsupportedCommOperationException;

    /**
     * Returns an InputStream for the port.
     * @return
     * @throws IOException
     */
    public abstract InputStream getInputStream() throws IOException;

    /**
     * Removes the event listener.
     */
    public abstract void removeEventListener();

    /**
     * Closes the port.
     */
    public abstract void close();

    /**
     * Adds an event listener.
     * @param listener
     * @throws TooManyListenersException
     */
    public abstract void addEventListener( SerialPortEventListener listener ) throws TooManyListenersException;

    /**
     * Returns the name of the port.
     * @return
     */
    public abstract String getName();
}
