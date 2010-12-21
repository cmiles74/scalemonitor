package com.nervestaple.scalemonitor.serialportreader.rawpacketreader;

import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.RawPacket;
import com.nervestaple.scalemonitor.serialportreader.rawpacketreader.RawPacketReaderException;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.RawPacket;

import java.util.Date;

/**
 * Provides an interface that RawPacketReader objects can implement to convert raw data to a raw packet.
 *
 * Created on Jul 10, 2005 at 2:35:04 PM
 *
 * @author Christopher Miles
 * @version 1.0
 */
public interface RawPacketReader {
    /**
     * Returns a raw packet for an AND EW 300A electronic scale.
     * @param portId
     * @param dateReceived
     * @param data
     * @return
     * @throws com.nervestaple.scalemonitor.serialportreader.rawpacketreader.RawPacketReaderException
     */
    RawPacket getRawPacket( String portId, Date dateReceived, String data )
            throws RawPacketReaderException;

    /**
     * Verifies that the data is valid for an AND EW 300A electronic scale.
     * @param data
     * @return
     */
    boolean validateData( String data );
}
