package com.nervestaple.scalemonitor.mock;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

/**
 * Creates a new mock SerialPort for testing.
 *
 * Created on Jul 16, 2005 at 1:07:13 PM
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class MockSerialPortAndEw300A extends SerialPort {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Prefix for the port name.
     */
    public final static String PORT_PREFIX = "TST";

    /**
     * Counter for the port name.
     */
    private static int portIdNumber = 0;

    /**
     * List of listeners.
     */
    private List listeners;

    /**
     * Port name.
     */
    private String portName;

    /**
     * Outputstream of data.
     */
    private PipedOutputStream outputstream;

    /**
     * Flag to generate data.
     */
    private boolean generateData = false;

    /**
     * Creates a new MockSerialPortAndEw300A.
     */
    public MockSerialPortAndEw300A() {

        // increment the port id number
        portIdNumber++;

        // create a name for the port
        portName = PORT_PREFIX + portIdNumber;

        // create a list to store listeners
        listeners = new ArrayList();

        // create a new outputstream
        outputstream = new PipedOutputStream();
    }

    public void setSerialPortParams( int baud, int databits, int stopbits, int parity )
            throws UnsupportedCommOperationException {

        // make sure params meet that of the AND EW 300A
        if( ( baud != 2400 ) || ( databits != SerialPort.DATABITS_7 ) || ( stopbits != SerialPort.STOPBITS_1 )
            || parity != SerialPort.PARITY_EVEN ) {

            throw new UnsupportedCommOperationException( "Unsupported port parameters for the AND EW 300A" );
        }
    }

    public InputStream getInputStream() throws IOException {

        // start the thread to generate data
        startGeneratingData();
        
        InputStream inputstream = new PipedInputStream( outputstream );

        return( inputstream );
    }

    public void removeEventListener() {

        listeners.clear();
    }

    public void addEventListener( SerialPortEventListener listener ) throws TooManyListenersException {

        if( listeners.size() >= 1 ) {

            throw new TooManyListenersException( "Too many listeners (more than one)" );
        }

        if( !listeners.contains( listener ) ) {

            listeners.add( listener );
        }
    }

    public void close() {

        stopGeneratingData();
    }

    // methods needed to implement SerialPort

    public void notifyOnDataAvailable( boolean notifyOnDataAvailable ) {

    }

    public void notifyOnFramingError( boolean b ) {

    }

    public void notifyOnOutputEmpty( boolean b ) {

    }

    public void notifyOnOverrunError( boolean b ) {

    }

    public void notifyOnParityError( boolean b ) {

    }

    public void notifyOnRingIndicator( boolean b ) {

    }

    public void sendBreak( int i ) {

    }

    public void setDTR( boolean b ) {

    }

    public void setFlowControlMode( int i ) throws UnsupportedCommOperationException {

    }

    public void setRTS( boolean b ) {

    }

    public void disableReceiveFraming() {

    }

    public void disableReceiveThreshold() {

    }

    public void disableReceiveTimeout() {

    }

    public void enableReceiveFraming( int i ) throws UnsupportedCommOperationException {

    }

    public void enableReceiveThreshold( int i ) throws UnsupportedCommOperationException {

    }

    public void enableReceiveTimeout( int i ) throws UnsupportedCommOperationException {

    }

    public int getInputBufferSize() {
        return 0;
    }

    public int getBaudRate() {
        return 0;
    }

    public int getDataBits() {
        return 0;
    }

    public int getFlowControlMode() {
        return 0;
    }

    public int getParity() {
        return 0;
    }

    public int getStopBits() {
        return 0;
    }

    public boolean isCD() {
        return false;
    }

    public boolean isCTS() {
        return false;
    }

    public boolean isDSR() {
        return false;
    }

    public boolean isDTR() {
        return false;
    }

    public boolean isRI() {
        return false;
    }

    public boolean isRTS() {
        return false;
    }

    public void notifyOnBreakInterrupt( boolean b ) {

    }

    public byte getParityErrorChar() throws UnsupportedCommOperationException {
        return 0;
    }

    public boolean setParityErrorChar( byte b ) throws UnsupportedCommOperationException {
        return false;
    }

    public byte getEndOfInputChar() throws UnsupportedCommOperationException {
        return 0;
    }

    public boolean setEndOfInputChar( byte b ) throws UnsupportedCommOperationException {
        return false;
    }

    public boolean setUARTType( String s, boolean b ) throws UnsupportedCommOperationException {
        return false;
    }

    public String getUARTType() throws UnsupportedCommOperationException {
        return null;
    }

    public boolean setBaudBase( int i ) throws UnsupportedCommOperationException {
        return false;
    }

    public int getBaudBase() throws UnsupportedCommOperationException {
        return 0;
    }

    public boolean setDivisor( int i ) throws UnsupportedCommOperationException {
        return false;
    }

    public int getDivisor() throws UnsupportedCommOperationException {
        return 0;
    }

    public boolean setLowLatency() throws UnsupportedCommOperationException {
        return false;
    }

    public boolean getLowLatency() throws UnsupportedCommOperationException {
        return false;
    }

    public boolean setCallOutHangup( boolean b ) throws UnsupportedCommOperationException {
        return false;
    }

    public boolean getCallOutHangup() throws UnsupportedCommOperationException {
        return false;
    }

    public void notifyOnCTS( boolean b ) {

    }

    public void notifyOnCarrierDetect( boolean b ) {

    }

    public void notifyOnDSR( boolean b ) {

    }

    public String getName() {

        return( portName );
    }

    public int getOutputBufferSize() {
        return 0;
    }

    public OutputStream getOutputStream() throws IOException {
        return null;
    }

    public int getReceiveFramingByte() {
        return 0;
    }

    public int getReceiveThreshold() {
        return 0;
    }

    public int getReceiveTimeout() {
        return 0;
    }

    public boolean isReceiveFramingEnabled() {
        return false;
    }

    public boolean isReceiveThresholdEnabled() {
        return false;
    }

    public boolean isReceiveTimeoutEnabled() {
        return false;
    }

    public void setInputBufferSize( int i ) {

    }

    public void setOutputBufferSize( int i ) {

    }

    // private methods

    private void notifyListenersDataAvailable() {

        SerialPortEventListener[] eventlisteners =
                ( SerialPortEventListener[] ) listeners.toArray( new SerialPortEventListener[ listeners.size() ] );

        for( int index = 0; index < eventlisteners.length; index++ ) {

            eventlisteners[ index ].serialEvent( new SerialPortEvent( this, 1, true, true ) );
        }
    }

    private void startGeneratingData() {

        generateData = true;

        Thread thread = new Thread( new Runnable() {
            public void run() {

                NumberFormat numberformat = new DecimalFormat( "0000" );
                int counter = 0;
                int value = 0;
                int valueDecimal = 0;
                int type = 0;
                String typeName = null;

                while( generateData ) {

                    if( counter <= 0 ) {
                        value = RandomUtils.nextInt( 999 );
                        valueDecimal = RandomUtils.nextInt( 9 );
                        counter = RandomUtils.nextInt( 5 );
                        type = RandomUtils.nextInt( 12 );
                    }

                    if( type >= 0 & type <=7  ) {
                        typeName = "ST";
                    } else if(  type >=8 & type <= 10 ) {
                        typeName ="US";
                    } else {
                        typeName = "OL";
                    }

                    writeStringToOutputStream( typeName + ",G,+" + numberformat.format( value ) + "." + valueDecimal
                            + "\r\n" );

                    counter--;

                    // sleep before generating more data
                    try {
                        Thread.sleep( 200 );
                    } catch( InterruptedException e ) {
                        logger.warn( e );
                    }
                }
            }
        });

        thread.start();
    }

    private void stopGeneratingData() {

        generateData = false;
    }

    private void writeStringToOutputStream( String data ) {

        // array to hold data
        char[] charOut = new char[ 1 ];

        for( int index = 0; index < data.length(); index++ ) {

            data.getChars( index, index+1, charOut, 0 );
            try {
                outputstream.write( charOut[ 0 ] );
            } catch( IOException e ) {
                logger.warn( e );
            }
            notifyListenersDataAvailable();
        }
    }
}
