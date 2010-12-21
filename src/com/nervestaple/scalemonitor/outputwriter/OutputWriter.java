package com.nervestaple.scalemonitor.outputwriter;

import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.ScalePacket;
import com.nervestaple.scalemonitor.form.portlist.portinfo.PortInfoController;

/**
 * Provides an interface for output writers to implement.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public interface OutputWriter {

    /**
     * Write the packet to the output destination.
     * @param packet
     * @param controller
     */
    void writePacket( ScalePacket packet, PortInfoController controller );

    /**
     * Close the output destination.
     */
    void close();
}
