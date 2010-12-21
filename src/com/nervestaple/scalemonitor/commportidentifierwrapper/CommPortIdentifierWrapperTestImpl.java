package com.nervestaple.scalemonitor.commportidentifierwrapper;

import com.nervestaple.scalemonitor.ScaleType;
import com.nervestaple.scalemonitor.mock.MockSerialPortWrapperFactory;
import com.nervestaple.scalemonitor.serialportwrapper.SerialPortWrapper;
import org.apache.log4j.Logger;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

/**
 * Provides a test CommPortIdentifierWrapper for wrapping test ports.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class CommPortIdentifierWrapperTestImpl extends CommPortIdentifierWrapper {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Port type.
     */
    private final static int PORT_TYPE = CommPortIdentifier.PORT_SERIAL;

    /**
     * Name that has the port open.
     */
    private String name;

    /**
     * Time to wait for the port to open.
     */
    private int wait;

    /**
     * Scale type to emulate.
     */
    private ScaleType scaletype;

    /**
     * SerialPortWrapper.
     */
    private SerialPortWrapper serialportwrapper;

    /**
     * Returns a CommPortIdentifierWrapperTestImpl that wrapps a test port of the scale type specified.
     * @param scaletype
     */
    public CommPortIdentifierWrapperTestImpl( ScaleType scaletype ) {

        this.scaletype = scaletype;

        serialportwrapper = MockSerialPortWrapperFactory.getSerialPort( scaletype );
    }

    /**
     * Returns the port type.
     * @return
     */
    public int getPortType() {

        return ( PORT_TYPE );
    }

    /**
     * Returns the name of the port.
     *
     * @return
     */
    public String getName() {

        return ( serialportwrapper.getName() );
    }

    /**
     * Opens the port. This implementaion <i>always</i> returns a SerialPortWrapper. In the future, this
     * will be changes to return a ParallelPortWrapper is the port is parallel.
     * @param name
     * @param wait
     * @return
     * @throws PortInUseException
     */
    public SerialPortWrapper open( String name, int wait ) throws PortInUseException {

        return( serialportwrapper );
    }
}
