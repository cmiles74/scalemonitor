package com.nervestaple.scalemonitor.outputwriter.segmentedfile;

/**
 * Provides an exception object for the SegmentFile.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class SegmentedFileException extends Exception {

    /**
     * Creates a new SegmentedFileException.
     */
    public SegmentedFileException() {
        super();
    }

    /**
     * Creates a new SegmentedFileException.
     *
     * @param message
     */
    public SegmentedFileException( String message ) {
        super( message );
    }

    /**
     * Creates a new SegmentedFileException.
     *
     * @param message
     * @param throwable
     */
    public SegmentedFileException( String message, Throwable throwable ) {
        super( message, throwable );
    }

    /**
     * Creates a new SegmentedFileException.
     *
     * @param throwable
     */
    public SegmentedFileException( Throwable throwable ) {
        super( throwable );
    }
}
