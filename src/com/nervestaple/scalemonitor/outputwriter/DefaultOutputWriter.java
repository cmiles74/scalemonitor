package com.nervestaple.scalemonitor.outputwriter;

import com.nervestaple.scalemonitor.form.portlist.portinfo.PortInfoController;
import com.nervestaple.scalemonitor.outputwriter.formatter.DefaultOutputFormatter;
import com.nervestaple.scalemonitor.outputwriter.formatter.OutputFormatter;
import com.nervestaple.scalemonitor.outputwriter.segmentedfile.SegmentedFile;
import com.nervestaple.scalemonitor.outputwriter.segmentedfile.SegmentedFileException;
import com.nervestaple.scalemonitor.outputwriter.segmentedfile.SegmentedFileListener;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.ScalePacket;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Provides the default implementation of the output writer. It uses the defalt output formatter object
 * to format the output and a segmented file to write the output to the save location.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class DefaultOutputWriter implements OutputWriter {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Output formatter.
     */
    private OutputFormatter outputformatter;

    /**
     * Segmented file.
     */
    private SegmentedFile segmentedfile;

    /**
     * Creates a new DefaultOutputWriter and sets the save location.
     * @param fileSaveLocation
     */
    public DefaultOutputWriter( File fileSaveLocation ) {

        // setup output formatter
        outputformatter = new DefaultOutputFormatter();

        try {
            // create a new SegmentedFile
            segmentedfile = new SegmentedFile( fileSaveLocation.getAbsolutePath(),
                    fileSaveLocation.getName(),
                    null,
                    "csv",
                    65536 );
        } catch( SegmentedFileException e ) {
            logger.warn( e );
        }

        // add a listener to write the header when the output file changes
        segmentedfile.addSegmentedFileListener( new SegmentedFileListener() {
            public void fileChanged() {

                writeData( new String[]{"Scale", "Header", "Weight", "Unit", "UNIX Timestamp", "ISO Timestamp",
                        "Excel Timestamp"} );
            }
        } );

        try {
            // open the file
            segmentedfile.openForWriting();
        } catch( SegmentedFileException e ) {
            logger.warn( e );
        }
    }

    /**
     * Write the packet to the output destination.
     * @param packet
     * @param controller
     */
    public void writePacket( ScalePacket packet, PortInfoController controller ) {

        // format the data
        String[] stringArrayOut = outputformatter.format( packet, controller );

        writeData( stringArrayOut );
    }

    /**
     * Close the output destination.
     */
    public void close() {

        segmentedfile.close();
    }

    // private methods

    /**
     * Writes an array of data to the output file as CSV data.
     * @param stringArrayData
     */
    private void writeData( String[] stringArrayData ) {

        StringBuffer buffer = new StringBuffer();

        for( int index = 0; index < stringArrayData.length; index++ ) {

            if( buffer.length() > 0 ) {
                buffer.append( "," );
            }

            buffer.append( stringArrayData[ index ] );
        }

        try {
            segmentedfile.writeLine( buffer.toString() );
        } catch( SegmentedFileException e ) {
            logger.warn( e );
        }
    }
}
