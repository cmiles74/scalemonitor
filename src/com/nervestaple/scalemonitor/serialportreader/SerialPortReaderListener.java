package com.nervestaple.scalemonitor.serialportreader;

/**
 *  Provides an interface objects can implement if they wish to respond to
 * SerialPortReader events.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public interface SerialPortReaderListener {

    /**
     * Called when a packet is read from the port.
     * @param rawpacket
     */
    public void packetRead( com.nervestaple.scalemonitor.serialportreader.rawpacketparser.RawPacket rawpacket );
}
