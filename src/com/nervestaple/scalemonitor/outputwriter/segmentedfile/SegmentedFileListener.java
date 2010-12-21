package com.nervestaple.scalemonitor.outputwriter.segmentedfile;

/**
 * Provides an interface objects can implement if they want to listen for SegmentFile events.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public interface SegmentedFileListener {

    /**
     * Notifies listener the the file has been changed and the new file is ready for writing.
     */
    public void fileChanged();
}
