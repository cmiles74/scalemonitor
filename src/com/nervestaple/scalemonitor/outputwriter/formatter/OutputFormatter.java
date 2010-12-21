package com.nervestaple.scalemonitor.outputwriter.formatter;

import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.ScalePacket;
import com.nervestaple.scalemonitor.form.portlist.portinfo.PortInfoController;

/**
 * Defines the interface for output formatting object.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public interface OutputFormatter {

    /**
     * Returns a String array containing the ouput values.
     * @param packet
     * @param controller
     * @return String[]
     */
    public String[] format( ScalePacket packet, PortInfoController controller );
}
