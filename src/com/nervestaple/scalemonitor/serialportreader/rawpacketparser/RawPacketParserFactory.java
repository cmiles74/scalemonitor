package com.nervestaple.scalemonitor.serialportreader.rawpacketparser;

import com.nervestaple.scalemonitor.ScaleType;
import org.apache.log4j.Logger;

/**
 * Provides a factory for getting raw packet parser objects.
 *
 * Created on Jul 10, 2005 at 5:59:07 PM
 *
 * @author miles
 * @version 1.0
 */
public class RawPacketParserFactory {

    /**
     * Logger instance.
     */
    private static Logger logger = Logger.getLogger(
            "com.nervestaple.scalemonitor.serialportreader.rawpacketparser.RawPacketParserFactory" );

    /**
     * Returns a raw packet parser for the provided scale type.
     * @param scaletype
     * @return
     * @throws com.nervestaple.scalemonitor.serialportreader.rawpacketparser.RawPacketParserException
     */
    public static RawPacketParser getRawPacketParser( ScaleType scaletype ) throws RawPacketParserException {

        RawPacketParser rawpacketparser = null;

        if( scaletype == ScaleType.AND_EW_300A ) {

            try {
                Class rawpacketparserclass = Class.forName( scaletype.getRawPacketParserClass() );
                rawpacketparser = ( RawPacketParser ) rawpacketparserclass.newInstance();
            } catch( InstantiationException e ) {
                throw new RawPacketParserException( "Could not instantiate parser class", e );
            } catch( IllegalAccessException e ) {
                throw new RawPacketParserException( "Could not instantiate parser class", e );
            } catch( ClassNotFoundException e ) {
                throw new RawPacketParserException( "Could not find parser class", e );
            }
        }

        if( scaletype == null ) {

            throw new IllegalArgumentException( "Unknown scale type" );
        }

        logger.debug( "Created RawPacketParser " + rawpacketparser );
        return( rawpacketparser );
    }
}
