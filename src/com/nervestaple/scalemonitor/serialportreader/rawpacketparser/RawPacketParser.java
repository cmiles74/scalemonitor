package com.nervestaple.scalemonitor.serialportreader.rawpacketparser;



/**
 * CLASS NAME
 * <p/>
 * Created on Jul 10, 2005 at 6:02:10 PM
 *
 * @author miles
 * @version 1.0
 */
public interface RawPacketParser {
    /**
     * Parses out the values in the packet of raw data.
     * @param rawpacket
     * @return
     */
    ScalePacket parseData( RawPacket rawpacket );
}
