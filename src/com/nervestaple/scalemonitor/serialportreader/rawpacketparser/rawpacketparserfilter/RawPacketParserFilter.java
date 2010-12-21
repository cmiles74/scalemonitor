package com.nervestaple.scalemonitor.serialportreader.rawpacketparser.rawpacketparserfilter;

import com.nervestaple.scalemonitor.ScaleType;
import com.nervestaple.scalemonitor.serialportreader.SerialPortReaderListener;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * Provides an object for filtering the raw packets.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class RawPacketParserFilter implements SerialPortReaderListener {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Raw packet parser.
     */
    private RawPacketParser rawpacketparser;

    /**
     * Minimum timer difference between packets.
     */
    private long minimumTime = 0;

    /**
     * Minimum value difference.
     */
    private double minimumDifference = 0;

    /**
     * List of listeners.
     */
    private List listeners;

    /**
     * Last packet received.
     */
    private RawPacket rawpacketLast;

    /**
     * Time since last packet that contained changed data.
     */
    private Date dateLastChange;

    /**
     * Creates a new rawpacketparserfilter.
     * @param minimumTime
     * @param minimumDifference
     * @throws RawPacketParserException
     */
    public RawPacketParserFilter( ScaleType scaletype, long minimumTime, double minimumDifference )
            throws RawPacketParserException {

        // get a raw packet parser
        try {
            this.rawpacketparser = RawPacketParserFactory.getRawPacketParser( scaletype );
        } catch( RawPacketParserException e ) {

            throw new RawPacketParserException( "Could not get a parser for the raw packet", e );
        }

        // save values
        this.minimumTime = minimumTime;
        this.minimumDifference = minimumDifference;
        dateLastChange = new Date();

        // create a list for listeners
        listeners = new ArrayList();
    }

    /**
     * Adds a new RawPacketParserFilter listener.
     * @param listener
     */
    public void addRawPacketParserFilterListener( RawPacketParserFilterListener listener ) {

        if( ! listeners.contains( listener ) ) {
            listeners.add( listener );
        }
    }

    /**
     * Removes a new RawPacketParserFilter listener.
     * @param listener
     */
    public void removeRawPacketParserFilterListener( RawPacketParserFilterListener listener ) {

        listeners.remove( listener );
    }

    /**
     * Returns an array of RawPacketParserListeners.
     * @return
     */
    public RawPacketParserFilterListener[] getRawPacketParserListeners() {

        RawPacketParserFilterListener[] arrayListeners =
                ( RawPacketParserFilterListener[] ) listeners.toArray( new RawPacketParserFilterListener[ listeners.size() ] );

        return( arrayListeners );
    }

    /**
     * Called when a packet is read from the port.
     *
     * @param rawpacket
     */
    public void packetRead( RawPacket rawpacket ) {

        Validate.notNull( rawpacket );

        boolean passPacket = false;

        if( rawpacketLast == null  ) {
            passPacket = true;
        }

        if( !passPacket && !rawpacket.getData().equals( rawpacketLast.getData() ) ) {

            // split packets
            String[] packet1 = StringUtils.split( rawpacket.getData(), "," );
            String[] packet2 = StringUtils.split( rawpacketLast.getData(), "," );

            String header1 = packet1[ 0 ];
            String header2 = packet2[ 0 ];

            if ( !header1.equals( header2 ) ) {

                passPacket = true;
            }

            if( !( rawpacket.getDate().getTime() - rawpacketLast.getDate().getTime() < minimumTime ) ) {

                // parse packets
                try {
                    double value1 = Double.parseDouble( packet1[ 2 ] );
                    double value2 = Double.parseDouble( packet2[ 2 ] );

                    if( value2 > value1 ) {

                        if( value2 - value1 > minimumDifference ) {
                            passPacket = true;
                        }
                    } else if( value1 > value2 ) {

                        if( value1 - value2 > minimumDifference ) {
                            passPacket = true;
                        }
                    }
                } catch ( NumberFormatException e ) {
                    logger.warn( e );
                }
            }
        }

        // save the last packet
        rawpacketLast = rawpacket;

        // pass the packet if we need to
        if ( passPacket ) {
            handleNewPacket( rawpacket );
        }
    }

    // private methods

    /**
     * Handles the arrival of a new filtered packet.
     * @param rawpacket
     */
    private void handleNewPacket( RawPacket rawpacket ) {

        // get a AndEw300aPacket for the raw packet
        ScalePacket scalepacket = rawpacketparser.parseData( rawpacket );

        // calculate time since last change
        Date datePrevious = dateLastChange;
        dateLastChange = new Date();
        long msDifference = dateLastChange.getTime() - datePrevious.getTime();
        scalepacket.setMillisecondsSinceLastChange( msDifference );

        // set the time since last change on the packet

        // notify listeners
        notifyRawPacketParserListeners( scalepacket );
    }

    /**
     * Notifies listeners of a new filtered packet.
     * @param andew300apacket
     */
    private void notifyRawPacketParserListeners( ScalePacket andew300apacket ) {

        // get listeners
        RawPacketParserFilterListener[] arrayListeners = getRawPacketParserListeners();

        // loop through listeners
        for( int index = 0; index < arrayListeners.length; index++ ) {

            arrayListeners[ index ].newPacket( andew300apacket );
        }
    }
}
