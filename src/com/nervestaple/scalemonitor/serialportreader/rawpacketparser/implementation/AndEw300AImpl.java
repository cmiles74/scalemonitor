package com.nervestaple.scalemonitor.serialportreader.rawpacketparser.implementation;

import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.RawPacketParser;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.ScalePacket;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  Provides a class to parse a RawPacket into a ScalePacket.
 *
 * @author Christopher Mil3s
 * @version 1.0
 */
public class AndEw300AImpl implements RawPacketParser {

    /**
     * Logger instance.
     */
    private static Logger logger = Logger.getLogger( "com.nervestaple.scalemonitor.rawpacketparser.AndEw300AImpl" );

    /**
     * Creates a new AndEw300AImpl.
     */
    public AndEw300AImpl() {

        logger.debug( "Created new AndEw300AImpl RawPacketParser" );
    }

    /**
     * Parses out the values in the packet of raw data.
     * @param rawpacket
     * @return
     */
    public ScalePacket parseData( com.nervestaple.scalemonitor.serialportreader.rawpacketparser.RawPacket rawpacket ) {

        String rawData = null;
        String port = null;
        Date received = null;
        boolean overload = false;
        boolean stableData = false ;
        boolean unstableData = false;
        boolean grams = false;
        boolean ounces = false;
        BigDecimal weight = null;

        // get teh easy values
        rawData = rawpacket.getData();
        port = rawpacket.getPort();
        received = rawpacket.getDate();

        // parse out the other values
        String[] values = StringUtils.split( rawData, "," );

        // make sure we have a valid packet
        Validate.isTrue( values.length == 3 );

        // parse out the header
        if ( values[ 0 ].equals( "OL" ) ) {
            overload = true;
        } else if ( values[ 0 ].equals( "ST" ) ) {
            stableData = true;
        } else if ( values[ 0 ].equals( "US" ) ) {
            unstableData = true;
        } else {
            throw new IllegalArgumentException( "Invalid header on packet: " + values[ 0 ] );
        }

        // parse out the unit
        if ( values[ 1 ].equals( "G" ) ) {
            grams = true;
        } else if ( values[ 1 ].equals( "O" ) ) {
            ounces = true;
        } else {
            throw new IllegalArgumentException( "Invalid unit type in packet: " + values[ 1 ] );
        }

        // parse out the weight
        weight = new BigDecimal( Double.parseDouble( values[ 2 ] ) );

        // create the finished packet
        ScalePacket scalepacket = new ScalePacket( rawData, port, received, overload, stableData,
                unstableData, grams, ounces, weight );

        return( scalepacket );
    }
}
