package com.nervestaple.scalemonitor.serialportwrapper;

import org.apache.log4j.Logger;

import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;

/**
 * Provides a wrapper around a SerialPort object.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class SerialPortWrapperDefaultImpl extends SerialPortWrapper {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Creates a new SerialPortWrapperDefaultImpl.
     * @param serialport
     */
    public SerialPortWrapperDefaultImpl( SerialPort serialport ) {

        this.serialport = serialport;
    }

    /**
     * Notify the listener when data is available.
     * @param value
     */
    public void notifyOnDataAvailable( boolean value ) {

        serialport.notifyOnDataAvailable( value );
    }

    /**
     * Sets the parameters on the serial port.
     * @param baud
     * @param databits
     * @param stopbits
     * @param parity
     * @throws UnsupportedCommOperationException
     */
    public void setSerialPortParams( int baud, int databits, int stopbits, int parity )
            throws UnsupportedCommOperationException {

        serialport.setSerialPortParams( baud, databits, stopbits, parity );
    }

    /**
     * Returns an InputStream for the port.
     * @return
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException {

        return( serialport.getInputStream() );
    }

    /**
     * Removes the event listener.
     */
    public void removeEventListener() {

        serialport.removeEventListener();
    }

    /**
     * Closes the port.
     */
    public void close() {

        serialport.close();
    }

    /**
     * Adds an event listener.
     * @param listener
     * @throws TooManyListenersException
     */
    public void addEventListener( SerialPortEventListener listener ) throws TooManyListenersException {

        serialport.addEventListener( listener );
    }

    /**
     * Returns the name of the port.
     * @return
     */
    public String getName() {
        
        return( serialport.getName() );
    }


}
