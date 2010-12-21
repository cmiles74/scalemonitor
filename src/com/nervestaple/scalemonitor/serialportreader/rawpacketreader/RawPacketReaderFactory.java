package com.nervestaple.scalemonitor.serialportreader.rawpacketreader;

import org.apache.log4j.Logger;
import com.nervestaple.scalemonitor.ScaleType;

/**
 * Provides a factory object for getting AndEw300AImpl objects.
 *
 * Created on Jul 10, 2005 at 2:36:07 PM
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class RawPacketReaderFactory {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Returns the appropriate RawPacketReader for the scale type provided.
     * @param scaleType
     * @return
     * @throws com.nervestaple.scalemonitor.serialportreader.rawpacketreader.RawPacketReaderException
     */
    public static RawPacketReader getRawPacketReader( ScaleType scaleType ) throws RawPacketReaderException {

        RawPacketReader rawpacketreader = null;

        try {
            rawpacketreader = ( RawPacketReader ) Class.forName( scaleType.getRawPacketReaderClass() ).newInstance();
        } catch( InstantiationException e ) {
            throw new RawPacketReaderException( "Could not instantiate reader class", e );
        } catch( IllegalAccessException e ) {
            throw new RawPacketReaderException( "Could not instantiate reader class", e );
        } catch( ClassNotFoundException e ) {
            throw new RawPacketReaderException( "Could not find reader class", e );
        }

        if( rawpacketreader == null ) {

            throw new RawPacketReaderException( "Unknown scale type" );
        }

        return( rawpacketreader );
    }
}
