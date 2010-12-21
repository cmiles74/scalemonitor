package com.nervestaple.scalemonitor.commportidentifierwrapper;

import com.nervestaple.scalemonitor.serialportwrapper.SerialPortWrapper;
import com.nervestaple.scalemonitor.serialportwrapper.SerialPortWrapperFactory;
import org.apache.log4j.Logger;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

/**
 * Provides a simple wrapper for the CommPortIdentifier object.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class CommPortIdentifierWrapperDefaultImpl extends CommPortIdentifierWrapper {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * CommPortIdentifier.
     */
    protected CommPortIdentifier commPortIdentifier;

    /**
     * Creates a new CommPortIdentifierWrapperDefaultImpl arund the CommPortIdentifier.
     * @param commportidentifier
     */
    public CommPortIdentifierWrapperDefaultImpl( CommPortIdentifier commportidentifier ) {

        // save a reference to the port id
        this.commPortIdentifier = commportidentifier;
    }

    /**
     * Returns the port type.
     * @return
     */
    public int getPortType() {

        return ( commPortIdentifier.getPortType() );
    }

    /**
     * Returns the name of the port.
     * @return
     */
    public String getName() {

        return( commPortIdentifier.getName() );
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

        SerialPortWrapper seriaportwrapper = null;

        if( commPortIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL ) {

            seriaportwrapper = SerialPortWrapperFactory.getSerialPortWrapper( ( SerialPort ) commPortIdentifier.open( this.getClass()
                    .getName(), wait ) );
        }

        return( seriaportwrapper );
    }

}
