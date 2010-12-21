package com.nervestaple.scalemonitor.outputwriter.segmentedfile;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.RandomAccessFile;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides a class for a segmented file. The idea is that the application writes to a file inside of a folder.
 * When the file reaches the segment size, it is closed an another file opened. The files are numbered
 * sequentially.
 *
 * This object is only useful for writing files.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class SegmentedFile {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Default segment size.
     */
    public final long DEFAULT_SEGMENT_SIZE = 1047576;

    /**
     * Default segment size in lines.
     */
    public final int DEFAULT_SEGMENT_SIZE_LINES = 65536;

    /**
     * Path to the storage location.
     */
    public final String PATH;

    /**
     * Name of the storage location (folder).
     */
    public final String NAME;

    /**
     * Extension to use on the storage location.
     */
    public final String EXTENSION;

    /**
     * Extension to use on the files.
     */
    public final String FILE_EXTENSION;

    /**
     * Segment size, in bytes.
     */
    public final long SEGMENT_SIZE;

    /**
     * Segment size, in lines.
     */
    public final int SEGMENT_SIZE_LINES;

    /**
     * Storage location.
     */
    private File storageFolder;

    /**
     * Current file to write to.
     */
    private File currentFile;

    /**
     * File number counter.
     */
    private int fileCounter = 0;

    /**
     * Buffered writer to write data to the file.
     */
    private BufferedWriter bufferedwriterFileOutput;

    /**
     * Counter for line writing.
     */
    private int currentLine = 0;

    /**
     * Listeners.
     */
    private List listeners = new ArrayList();

    /**
     * Creates a new Segmented file.
     *
     * @param path
     * @param name
     */
    public SegmentedFile( String path, String name ) throws SegmentedFileException {

        PATH = path;
        NAME = name;
        EXTENSION = null;
        FILE_EXTENSION = null;
        SEGMENT_SIZE = DEFAULT_SEGMENT_SIZE;
        SEGMENT_SIZE_LINES = 0;

        setupFolder();
    }

    /**
     * Creates a new Segmented file.
     * @param path
     * @param name
     * @param segmentSize
     */
    public SegmentedFile( String path, String name,long segmentSize ) throws SegmentedFileException {

        PATH = path;
        NAME = name;
        EXTENSION = null;
        FILE_EXTENSION = null;
        SEGMENT_SIZE = segmentSize;
        SEGMENT_SIZE_LINES = 0;

        setupFolder();
    }

    /**
     * Creates a new Segmented file.
     *
     * @param path
     * @param name
     * @param segmentSizeLines
     */
    public SegmentedFile( String path, String name, int segmentSizeLines ) throws SegmentedFileException {

        PATH = path;
        NAME = name;
        EXTENSION = null;
        FILE_EXTENSION = null;
        SEGMENT_SIZE = segmentSizeLines;
        SEGMENT_SIZE_LINES = 0;

        setupFolder();
    }

    /**
     * Creates a new Segmented file.
     * @param path
     * @param name
     * @param extension
     * @param fileExtension
     * @param segmentSize
     */
    public SegmentedFile( String path, String name, String extension, String fileExtension, long segmentSize )
            throws SegmentedFileException {

        // save our values
        PATH = path;
        NAME = name;
        EXTENSION = extension;
        FILE_EXTENSION = fileExtension;
        SEGMENT_SIZE = segmentSize;
        SEGMENT_SIZE_LINES = 0;

        setupFolder();
    }

    /**
     * Creates a new Segmented file.
     *
     * @param path
     * @param name
     * @param extension
     * @param fileExtension
     * @param segmentSizeLines
     */
    public SegmentedFile( String path, String name, String extension, String fileExtension, int segmentSizeLines )
            throws SegmentedFileException {

        // save our values
        PATH = path;
        NAME = name;
        EXTENSION = extension;
        FILE_EXTENSION = fileExtension;
        SEGMENT_SIZE = 0;
        SEGMENT_SIZE_LINES = segmentSizeLines;

        setupFolder();
    }

    /**
     * Opens the folder and returns the file to write to.
     */
    public void openForWriting() throws SegmentedFileException {

        if( bufferedwriterFileOutput == null ) {

            checkAndIncrement();

            if( !currentFile.exists() ) {
                try {
                    currentFile.createNewFile();
                } catch( IOException e ) {
                    throw new SegmentedFileException( "Could not open file for writing " +
                            currentFile.getAbsolutePath() );
                }
            }
        }
    }

    /**
     * Closes the currently open file.
     */
    public void close() {

        if( bufferedwriterFileOutput != null ) {

            // flush close the file
            try {
                bufferedwriterFileOutput.flush();
                bufferedwriterFileOutput.close();
                bufferedwriterFileOutput = null;
            } catch( IOException e ) {
                logger.warn( "Problem closing file " + currentFile.getAbsoluteFile() );
            }
        }
    }

    /**
     * Adds a new listener.
     * @param listener
     */
    public void addSegmentedFileListener( SegmentedFileListener listener ) {

        // add the listener
        if( !listeners.contains( listener ) ) {
            listeners.add( listener );
        }
    }

    /**
     * Removes a listener.
     * @param listener
     */
    public void removeSegmentedFileListener( SegmentedFileListener listener ) {

        // remove the listener
        listeners.remove( listener );
    }

    /**
     * Writes a line of String data to the file.
     * @param line
     * @throws SegmentedFileException
     */
    public void writeLine( String line ) throws SegmentedFileException {

        checkAndIncrement();

        try {
            logger.debug( "Writing " + line );
            bufferedwriterFileOutput.write( line );
            bufferedwriterFileOutput.newLine();
        } catch( IOException e ) {
            throw new SegmentedFileException( "Could not write to file " + currentFile.getAbsolutePath() );
        }
    }

    /**
     * Writes a String of data to the file.
     * @param data
     * @throws SegmentedFileException
     */
    public void write( String data ) throws SegmentedFileException {

        checkAndIncrement();

        try {
            bufferedwriterFileOutput.write( data );
        } catch( IOException e ) {
            throw new SegmentedFileException( "Could not write to file " + currentFile.getAbsolutePath() );
        }
    }

    // private methods

    /**
     * Notifies listeners that the file has changed.
     */
    private void notifyListenersFileChanged() {

        // get an array of listeners
        SegmentedFileListener[] listenerArray = ( SegmentedFileListener[] ) listeners
                .toArray( new SegmentedFileListener[ listeners.size() ] );

        for( int index = 0; index < listenerArray.length; index++ ) {

            // get the next listener
            SegmentedFileListener listenerThis = listenerArray[ index ];

            // notify the listener
            listenerThis.fileChanged();
        }
    }

    private void checkAndIncrement() throws SegmentedFileException {

        if( currentFile != null ) {

            if( SEGMENT_SIZE != 0 ) {

                if( currentFile.length() >= SEGMENT_SIZE ) {

                    close();

                    changeFile();

                    openForWriting();
                }
            } else if( SEGMENT_SIZE_LINES != 0 ) {

                if( currentLine  >= ( SEGMENT_SIZE_LINES + 1 ) ) {

                    close();

                    changeFile();

                    currentLine = 0;

                    openForWriting();
                }

                currentLine++;
                logger.info( "Writing line " + currentLine );
            }
        }

       if( currentFile != null && bufferedwriterFileOutput == null ) {
           try {
               bufferedwriterFileOutput = new BufferedWriter( new FileWriter( currentFile ) );
               notifyListenersFileChanged();
           } catch( Exception e ) {
               throw new SegmentedFileException( "Could not open file for writing " + currentFile.getAbsolutePath() );
           }
       }
    }

    private void setupFolder() throws SegmentedFileException {

        // figure out the name of the folder
        storageFolder = new File( getFolderPath() );

        // do some sanity checking
        if( storageFolder.exists() && !storageFolder.isDirectory() ) {
            throw new SegmentedFileException( "The storage location is not a directory " + storageFolder );
        }

        if( storageFolder.exists() && !storageFolder.canWrite() ) {
            throw new SegmentedFileException( "Cannot write to the storage location " + storageFolder );
        }

        if( !storageFolder.exists() ) {

            // create the folder
            storageFolder.mkdirs();
        }

        // get the contents of the folder
        File[] files = storageFolder.listFiles();

        // set the file counter
        fileCounter = files.length;

        if( fileCounter != 0 ) {

            // loop through the files
            for( int index = 0; index < files.length; index++ ) {

                // get the next file
                File fileThis = files[ index ];

                // clear our index value
                int indexThis = 0;

                // clear our string version
                String indexString = null;

                if( FILE_EXTENSION != null ) {

                    indexString = fileThis.getName().substring( NAME.length(),
                            fileThis.getName().length() - FILE_EXTENSION.length() );
                } else {
                    indexString = fileThis.getName().substring( NAME.length(),
                            fileThis.getName().length() - FILE_EXTENSION.length() );
                }

                // parse the string to an index value
                indexThis = Integer.parseInt( indexString );

                if( indexThis == fileCounter ) {

                    // this is the current file
                    currentFile = fileThis;
                }
            }

            // make sure the file isn't at the max
            if( currentFile != null ) {

                if( SEGMENT_SIZE != 0 ) {

                    if( currentFile.length() >= SEGMENT_SIZE ) {

                        changeFile();
                    }
                } else if( SEGMENT_SIZE_LINES != 0 ) {

                    if( getLinesInFile( currentFile ) >= SEGMENT_SIZE_LINES ) {

                        changeFile();
                    }
                }
            }
        } else {

            changeFile();
        }
    }

    /**
     * Returns the number of lines in the file.
     * @param file
     * @return
     * @throws SegmentedFileException
     */
    private int getLinesInFile( File file ) throws SegmentedFileException{

        int lines = 0;

        try {
            RandomAccessFile randFile = new RandomAccessFile( file, "r" );
            long lastRec = randFile.length();
            randFile.close();

            FileReader fileRead = new FileReader( file );
            LineNumberReader lineRead = new LineNumberReader( fileRead );
            lineRead.skip( lastRec );

            lines  = lineRead.getLineNumber() - 1;
            fileRead.close();
            lineRead.close();
        } catch( IOException e ) {
            throw new SegmentedFileException( "Could not read from file " + file.getAbsolutePath() );
        }

        return( lines );
    }

    /**
     * Closes the current file, increments the counter, and opens a new file.
     */
    private void changeFile() {

        fileCounter++;

        currentFile = new File( storageFolder.getAbsolutePath() + "/" + getFileForIndex( fileCounter ) );
    }

    /**
     * Returns a String for the file with the index specified.
     * @param index
     * @return String
     */
    private String getFileForIndex( int index ) {

        StringBuffer buffer = new StringBuffer();

        // add the name
        buffer.append( NAME );

        if( buffer.charAt( buffer.length() - 1 ) != '-' ) {

            // add a dash before the number
            buffer.append( '-' );
        }

        // add the number
        buffer.append( index );

        if( FILE_EXTENSION != null && FILE_EXTENSION.length() > 0 ) {

            if( buffer.charAt( buffer.length() - 1 ) != '.' ) {

                // add a period for the extension
                buffer.append( '.' );
            }

            buffer.append( FILE_EXTENSION );
        }

        return( buffer.toString() );
    }

    /**
     * Returns the complete path name of the folder.
     * @return String
     */
    private String getFolderPath() {

        // create a buffer to hold the name
        StringBuffer buffer = new StringBuffer();

        // add the path
        buffer.append( PATH );

        if( EXTENSION != null && EXTENSION.length() > 0 ) {

            if( buffer.charAt( buffer.length() - 1 ) != '.' ) {

                // add a period for the extension
                buffer.append( '.' );
            }

            buffer.append( EXTENSION );
        }

        if( buffer.charAt( buffer.length() - 1 ) != '/' ) {

            // add a path seperator
            buffer.append( '/' );
        }

        return( buffer.toString() );
    }
}
