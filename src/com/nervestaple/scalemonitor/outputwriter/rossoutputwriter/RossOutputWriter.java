package com.nervestaple.scalemonitor.outputwriter.rossoutputwriter;

import com.nervestaple.scalemonitor.form.portlist.portinfo.PortInfoController;
import com.nervestaple.scalemonitor.outputwriter.OutputWriter;
import com.nervestaple.scalemonitor.outputwriter.formatter.OutputFormatter;
import com.nervestaple.scalemonitor.outputwriter.formatter.RossOutputFormatter;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.ScalePacket;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Provides an output writer that writes data files in the format that Ross' data processing application
 * is expecting.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class RossOutputWriter implements OutputWriter {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Hashtable of output files.
     */
    private Hashtable hashtableWriters;

    /**
     * Hashtable of generated scale id's.
     */
    private Hashtable hashtableIds;

    /**
     * Current last last used scale id.
     */
    private int intLastScaleId = 0;

    /**
     * Output formatter.
     */
    private OutputFormatter outputformatter;

    /**
     * Storage location.
     */
    private File storageFolder;

    /**
     * Start time.
     */
    private Date dateStartTime;

    /**
     * Extension to use on the files.
     */
    private String FILE_EXTENSION = "csv";

    /**
     * Date format for file names.
     */
    private final DateFormat dateformat = new SimpleDateFormat( "yyyy-MM-dd" );

    /**
     * Headers.
     */
    private final static String[] HEADER = new String[] {
        "Header", "Weight (g)", "Run Time (ms)", "Excel Timestamp" };

    /**
     * Creates a new RossOutputWriter.
     */
    public RossOutputWriter( File fileSaveLocation ) throws RossOutputWriterException {

        // create a new hashtable to hold references to the file writers
        hashtableWriters = new Hashtable();

        // create a new hashtable for the generated scale id's.
        hashtableIds = new Hashtable();

        // setup the start time
        dateStartTime = new Date();

        // setup out formatter
        outputformatter = new RossOutputFormatter( dateStartTime );

        setup( fileSaveLocation );
    }

    /**
     * Write the packet to the output destination.
     *
     * @param packet
     * @param controller
     */
    public void writePacket( ScalePacket packet, PortInfoController controller ) {

        // check to see if we have a writer for this port
        BufferedWriter writer = ( BufferedWriter ) hashtableWriters.get( controller.getModel().getName() );

        if( writer == null ) {

            // increment the id
            intLastScaleId++;

            // create a new file for the port
            File file =
                    new File( storageFolder.getAbsolutePath() +
                            "/" +
                            dateformat.format( dateStartTime ) +
                            "." + controller.getModel().getName() +
                            "." +intLastScaleId +
                            "." +
                            "rsw" );

            try {

                // create a writer for the file
                writer = new BufferedWriter( new FileWriter( file ) );

                // add the writer to our hashtable
                hashtableWriters.put( controller.getModel().getName(), writer );

                // store the id
                hashtableIds.put( controller.getModel().getName(), new Integer( intLastScaleId ) );

                // write the UNIX time to the file
                writeData( writer,
                        new String[]{ ( new Long( dateStartTime.getTime() / 1000 ) ).toString(),
                                "UNIX Start Timestamp" } );

                // write the scale name to the file
                writeData( writer, new String[]{ controller.getModel().getName(), "Scale" } );

                // write the header to the file
                writeData( writer, HEADER );
            } catch( IOException e ) {
                logger.warn( e );
            }
        }

        // format the data
        String[] stringArrayOut = outputformatter.format( packet, controller );

        // write the data to the file
        try {
            writeData( writer, stringArrayOut );
        } catch( IOException e ) {
            logger.warn( e );
        }
    }

    /**
     * Close the output destination.
     */
    public void close() {

        // get a collection of values from the hashtable
        Collection collection = hashtableWriters.values();

        // loop through the values
        Iterator iterator = collection.iterator();
        while( iterator.hasNext() ) {

            BufferedWriter writer = ( BufferedWriter ) iterator.next();

            // close the writer
            try {

                writer.flush();
                writer.close();
            } catch( IOException e ) {
                logger.warn( e );
            }
        }
    }

    // private methods

    /**
     * Sets up the output file location.
     * @param fileSaveLocation
     */
    private void setup( File fileSaveLocation ) throws RossOutputWriterException  {

        // figure out the name of the folder
        storageFolder = fileSaveLocation;

        // do some sanity checking
        if( storageFolder.exists() && !storageFolder.isDirectory() ) {
            throw new RossOutputWriterException( "The storage location is not a directory " + storageFolder );
        }

        if( storageFolder.exists() && !storageFolder.canWrite() ) {
            throw new RossOutputWriterException( "Cannot write to the storage location " + storageFolder );
        }

        if( !storageFolder.exists() ) {

            // create the folder
            storageFolder.mkdirs();
        }
    }

    /**
     * Writes an array of data to the output file as CSV data.
     *
     * @param writer
     * @param stringArrayData
     * @throws IOException
     */
    private void writeData( BufferedWriter writer, String[] stringArrayData ) throws IOException {

        StringBuffer buffer = new StringBuffer();

        for( int index = 0; index < stringArrayData.length; index++ ) {

            if( buffer.length() > 0 ) {
                buffer.append( "," );
            }

            buffer.append( stringArrayData[ index ] );
        }

        writer.write( buffer.toString() );

        // write out a windows end of line
        writer.write( "\r\n" );
    }
}
