package com.nervestaple.scalemonitor.outputwriter.formatter;

import com.nervestaple.scalemonitor.form.portlist.portinfo.PortInfoController;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.ScalePacket;
import com.nervestaple.utility.NervestapleDate;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Provides a formatter that returns the default set out output values.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class DefaultOutputFormatter implements OutputFormatter {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Decimal format for weights.
     */
    private DecimalFormat decimalformat;

    /**
     * Nervestaple date.
     */
    private NervestapleDate nervestapledate;

    /**
     * Creates a new DefaultOutputFormatter.
     */
    public DefaultOutputFormatter() {

        // set our decimal format
        decimalformat = new DecimalFormat( "##0.0" );

        // setup a nervestaple date
        nervestapledate = new NervestapleDate();
    }

    /**
     * Returns a String array containing the ouput values.
     *
     * Scale name
     * Overload | Stable | Unstable
     * weight
     * unit of measure (grams or ounces)
     * Date received (Unix timestamp)
     * Date received (ISO compatible format)
     * Date received (Excel timestamp)
     *
     * @param packet
     * @param controller
     * @return String[]
     */
    public String[] format( ScalePacket packet, PortInfoController controller ) {

        // setup our return array
        String[] stringArrayOutput = new String[ 7 ];

        logger.debug( "Received packet: " + packet );

        String header = null;
        String unit = null;

        if( packet.isOverload() ) {
            header = "Overload";
        } else if( packet.isStableData() ) {
            header = "Stable";
        } else if( packet.isUnstableData() ) {
            header = "Unstable";
        }

        if( packet.isGrams() ) {
            unit = "grams";
        } else if( packet.isOunces() ) {
            unit = "ounces";
        }
        
        stringArrayOutput[ 0 ] = controller.getModel().getName();
        stringArrayOutput[ 1 ] = header;

        if( controller.getModel().getTare() == null ) {
            stringArrayOutput[ 2 ] = decimalformat.format( packet.getWeight() );
        } else {
            stringArrayOutput[ 2 ] =
                    decimalformat.format( packet.getWeight().add( new BigDecimal( controller.getModel()
                            .getTare() .doubleValue() ) ) );
        }

        stringArrayOutput[ 3 ] = unit;

        // store the date as a Unix timestamp
        stringArrayOutput[ 4 ] = ( new Long( packet.getReceived().getTime() / 1000 ) ).toString();

        stringArrayOutput[ 5 ] = DateFormatUtils.ISO_DATETIME_FORMAT.format( packet.getReceived().getTime() );

        stringArrayOutput[ 6 ] = ( new Double( nervestapledate.getExcelTimestamp( packet.getReceived() ) ) ).toString();

        return( stringArrayOutput );
    }
}