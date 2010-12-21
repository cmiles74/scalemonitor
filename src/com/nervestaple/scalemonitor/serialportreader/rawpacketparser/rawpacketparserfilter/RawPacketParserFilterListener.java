package com.nervestaple.scalemonitor.serialportreader.rawpacketparser.rawpacketparserfilter;

import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.ScalePacket;

/**
 * Provides in interface an object can implement to received even
 * from the AndEw300AImpl.
 * @author Christopher Miles
 * @version 1.0
 */
public interface RawPacketParserFilterListener {

    /**
     * Called when a new packet is filtered.
     * @param andew300apacket
     */
    public void newPacket( ScalePacket andew300apacket );
}
