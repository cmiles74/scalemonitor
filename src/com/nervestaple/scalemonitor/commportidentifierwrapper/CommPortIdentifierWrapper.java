package com.nervestaple.scalemonitor.commportidentifierwrapper;

import com.nervestaple.scalemonitor.serialportwrapper.SerialPortWrapper;

import gnu.io.PortInUseException;

/**
 * Provides an abstract class that can be extended to wrap a CommPortIndentifier.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public abstract class CommPortIdentifierWrapper {

    /**
     * Returns the port type.
     * @return
     */
    public abstract int getPortType();

    /**
     * Opens the serial port wrapper.
     * @param name
     * @param wait
     * @return
     * @throws PortInUseException
     */
    public abstract SerialPortWrapper open( String name, int wait ) throws PortInUseException ;

    /**
     * Returns the name of the port.
     *
     * @return
     */
    public abstract String getName();
}
