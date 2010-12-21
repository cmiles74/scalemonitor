package com.nervestaple.scalemonitor.outputwriter.formatter;

import com.nervestaple.scalemonitor.form.portlist.portinfo.PortInfoController;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.ScalePacket;
import com.nervestaple.utility.NervestapleDate;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Provides an output formatter that outputs data to meet Ross' specifications.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class RossOutputFormatter implements OutputFormatter {

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
    public RossOutputFormatter( Date dateStart ) {

        // set our decimal format
        decimalformat = new DecimalFormat( "##0.0" );

        // setup a nervestaple date
        nervestapledate = new NervestapleDate();
    }

    /**
     * Returns a String array containing the ouput values.
     * <p/>
     * Scale name
     * Overload | Stable | Unstable
     * weight
     * unit of measure (grams or ounces)
     * Date received (Ross timestamp)
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
        String[] stringArrayOutput = new String[ 4 ];

        logger.debug( "Received packet: " + packet );

        String header = null;
        String unit = null;

        if( packet.isOverload() ) {
            header = "O";
        } else if( packet.isStableData() ) {
            header = "S";
        } else if( packet.isUnstableData() ) {
            header = "U";
        }

        if( packet.isGrams() ) {
            unit = "grams";
        } else if( packet.isOunces() ) {
            unit = "ounces";
        }

        stringArrayOutput[ 0 ] = header;

        if( controller.getModel().getTare() == null ) {
            stringArrayOutput[ 1 ] = decimalformat.format( packet.getWeight() );
        } else {
            stringArrayOutput[ 1 ] =
                    decimalformat.format( packet.getWeight().add( new BigDecimal( controller.getModel()
                            .getTare().doubleValue() ) ) );
        }

        // store the date as a Ross timestamp
        stringArrayOutput[ 2 ] = ( new Long( packet.getMillisecondsSinceLastChange() ) ).toString();

        // store the excel timestamp
        stringArrayOutput[ 3 ] = ( new Double( nervestapledate.getExcelTimestamp( packet.getReceived() ) ) ).toString();

        return ( stringArrayOutput );
    }
}
