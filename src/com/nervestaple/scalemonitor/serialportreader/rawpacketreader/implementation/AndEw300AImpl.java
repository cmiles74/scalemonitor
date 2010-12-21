package com.nervestaple.scalemonitor.serialportreader.rawpacketreader.implementation;

import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.RawPacket;
import com.nervestaple.scalemonitor.serialportreader.rawpacketreader.RawPacketReader;
import com.nervestaple.scalemonitor.serialportreader.rawpacketreader.RawPacketReaderException;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Provides a class for interpreting a raw packet of data from the AND EW 300A electronic scale.
 *
 * Created on Jul 10, 2005 at 2:17:21 PM
 *
 * @author miles
 * @version 1.0
 */
public class AndEw300AImpl implements RawPacketReader {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Length of a raw data packet.
     */
    public final static int PACKET_LENGTH = 12;

    /**
     * Creates a new AndEw300AImpl
     */
    public AndEw300AImpl() {

        logger.debug( "Created new AndEw300AImpl RawPacketReader" );
    }

    /**
     * Returns a raw packet for an AND EW 300A electronic scale.
     * @param portId
     * @param dateReceived
     * @param data
     * @return
     * @throws com.nervestaple.scalemonitor.serialportreader.rawpacketreader.RawPacketReaderException
     */
    public RawPacket getRawPacket( String portId, Date dateReceived, String data )
            throws RawPacketReaderException {

        RawPacket rawpacket = null;

        if( validateData( data ) ) {

            rawpacket = new RawPacket( portId, data, dateReceived );
        } else {

            throw new RawPacketReaderException( "Invalid data received from port " + portId + " at " + dateReceived );
        }

        return( rawpacket );
    }

    /**
     * Verifies that the data is valid for an AND EW 300A electronic scale.
     * @param data
     * @return
     */
    public boolean validateData( String data ) {

        boolean isValid = false;

        if( data.length() == PACKET_LENGTH ) {

            isValid = true;
        }

        return( isValid );
    }
}
