package com.nervestaple.scalemonitor.mock;

import com.nervestaple.scalemonitor.ScaleType;

/**
 * Provides a factory object for creating mock serial port wrappers.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class MockSerialPortWrapperFactory {

    public static MockSerialPortWrapper getSerialPort( ScaleType scaletype ) {

        MockSerialPortWrapper wrapper = null;

        if( scaletype == ScaleType.AND_EW_300A ) {

            wrapper = new MockSerialPortWrapper( new MockSerialPortAndEw300A() );
        }

        return( wrapper );
    }
}
