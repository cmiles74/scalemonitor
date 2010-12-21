package com.nervestaple.scalemonitor;

import org.apache.commons.lang.enums.Enum;

import gnu.io.SerialPort;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Provides an enumeration of supported scale types.
 *
 * Created on Jul 10, 2005 at 5:06:57 PM
 *
 * @author miles
 * @version 1.0
 */
public final class ScaleType extends Enum {

    /**
     * AND EW 300A Electronic Scale.
     */
    public static ScaleType AND_EW_300A = new ScaleType( "AND EW 300A", "\r\n", 12, 2400, SerialPort.DATABITS_7,
            SerialPort.STOPBITS_1, SerialPort.PARITY_EVEN,
            "com.nervestaple.scalemonitor.serialportreader.rawpacketreader.implementation.AndEw300AImpl",
            "com.nervestaple.scalemonitor.serialportreader.rawpacketparser.implementation.AndEw300AImpl" );

    /**
     * End of line string.
     */
    private String endOfLine;

    /**
     * Size of data packet.
     */
    private int packetSize;
    
    /**
     * Baud rate.
     */ 
    private int baudRate;
    
    /**
     * Data bits.
     */ 
    private int dataBits;
    
    /**
     * Stop bits.
     */ 
    private int stopBits;
    
    /**
     * Parity.
     */ 
    private int parity;
    
    /**
     * Raw packet reader class.
     */ 
    private String rawPacketReaderClass;
    
    /**
     * Raw packet parser classs.
     */ 
    private String rawPacketParserClass;

    /**
     * Creates a new ScaleType.
     * @param name
     */
    private ScaleType( String name, String endOfLine, int packetSize , int baudRate, int dataBits, int stopBits, 
                       int parity, String rawPacketReaderClass, String rawPacketParserClass ) {

        super( name );

        // save values
        this.endOfLine = endOfLine;
        this.packetSize = packetSize;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
        this.rawPacketReaderClass = rawPacketReaderClass;
        this.rawPacketParserClass = rawPacketParserClass;
    }

    /**
     * Returns a map of scale types.
     * @return
     */
    public static Map getEnumMap() {

        return( getEnumMap( ScaleType.class ) );
    }

    /**
     * Returns a list of scale types.
     * @return
     */
    public static List getEnumList() {

        return( getEnumList( ScaleType.class ) );
    }

    /**
     * Returns an iterator of scale types.
     * @return
     */
    public static Iterator iterator() {

        return( iterator( ScaleType.class ) );
    }

    // accessor methods

    public String getEndOfLine() {
        return endOfLine;
    }

    public int getPacketSize() {
        return packetSize;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }

    public int getParity() {
        return parity;
    }

    public String getRawPacketReaderClass() {
        return rawPacketReaderClass;
    }

    public String getRawPacketParserClass() {
        return rawPacketParserClass;
    }

    // other methods

    public String toString() {

        return( super.getName() );
    }
}
