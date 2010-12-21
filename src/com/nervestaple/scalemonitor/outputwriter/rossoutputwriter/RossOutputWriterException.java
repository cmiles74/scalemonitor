package com.nervestaple.scalemonitor.outputwriter.rossoutputwriter;

/**
 * Created by IntelliJ IDEA.
 * User: miles
 * Date: Nov 27, 2005
 * Time: 12:26:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class RossOutputWriterException extends Exception {

    /**
     * Creates a new RossOutputWriterException.
     */
    public RossOutputWriterException() {
        super();
    }

    /**
     * Creates a new RossOutputWriterException.
     *
     * @param message
     */
    public RossOutputWriterException( String message ) {
        super( message );
    }

    /**
     * Creates a new RossOutputWriterException.
     *
     * @param message
     * @param throwable
     */
    public RossOutputWriterException( String message, Throwable throwable ) {
        super( message, throwable );
    }

    /**
     * Creates a new SegmentedFileException.
     *
     * @param throwable
     */
    public RossOutputWriterException( Throwable throwable ) {
        super( throwable );
    }
}
