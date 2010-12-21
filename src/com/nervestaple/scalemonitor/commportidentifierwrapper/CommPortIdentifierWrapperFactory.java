package com.nervestaple.scalemonitor.commportidentifierwrapper;

import com.nervestaple.scalemonitor.ScaleType;

import gnu.io.CommPortIdentifier;

/**
 * Provides a factory for getting wrappers around CommPortIdentifier objects.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class CommPortIdentifierWrapperFactory {

    /**
     * Returns a CommPortIndentifierWrapper for the port.
     * @param commportidentifier
     * @return
     */
    public static CommPortIdentifierWrapper getCommPortIndentifierWrapper( CommPortIdentifier commportidentifier ) {

        return( new CommPortIdentifierWrapperDefaultImpl( commportidentifier ) );
    }

    /**
     * Provides a CommPortIdentifierWrapper around a mock comm port identifier for testing.
     * @param scaletype
     * @return
     */
    public static CommPortIdentifierWrapper getCommPortIdentifierWrapperTest( ScaleType scaletype ) {

        CommPortIdentifierWrapper wrapper = null;

        wrapper = new CommPortIdentifierWrapperTestImpl( scaletype );

        return( wrapper );
    }
}
