package com.nervestaple.scalemonitor.mock;

import com.nervestaple.scalemonitor.serialportwrapper.SerialPortWrapper;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;

/**
 * Provides a mock SerialPortWrapper for testing.
 *
 * Created on Jul 16, 2005 at 1:04:17 PM
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class MockSerialPortWrapper extends SerialPortWrapper {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * SerialPort instance.
     */
    private SerialPort serialport;

    /**
     * Creates a new MockSerialPortWrapper.
     */
    public MockSerialPortWrapper( SerialPort serialport ) {

        this.serialport = serialport;
    }

    /**
     * Setst the port to notify when data is available.
     * @param notifyOnDataAvailable
     */
    public void notifyOnDataAvailable( boolean notifyOnDataAvailable ) {

    }

    public void setSerialPortParams( int baud, int databits, int stopbits, int parity )
            throws UnsupportedCommOperationException {

        serialport.setSerialPortParams( baud, databits, stopbits, parity );
    }

    public InputStream getInputStream() throws IOException {

        return( serialport.getInputStream() );
    }

    public void removeEventListener() {

        serialport.removeEventListener();
    }

    public void close() {

        serialport.close();
    }

    public void addEventListener( SerialPortEventListener listener ) throws TooManyListenersException {

        serialport.addEventListener( listener );
    }

    public String getName() {

        return( serialport.getName() );
    }
}
