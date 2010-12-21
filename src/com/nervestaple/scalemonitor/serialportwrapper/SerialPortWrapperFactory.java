package com.nervestaple.scalemonitor.serialportwrapper;

import gnu.io.SerialPort;

/**
 * Provides a factory object for wrapping SerialPort objects.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class SerialPortWrapperFactory {

    /**
     * Returns a wrapper around the port.
     * @param serialport
     * @return
     */
    public static SerialPortWrapper getSerialPortWrapper( SerialPort serialport ) {

        return( new SerialPortWrapperDefaultImpl( serialport ) );
    }
}
