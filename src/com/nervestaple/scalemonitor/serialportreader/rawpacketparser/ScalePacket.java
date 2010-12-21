package com.nervestaple.scalemonitor.serialportreader.rawpacketparser;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Provides a class for encapsulating a packet of data received from an
 * AND EW-300A electronic scale.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class ScalePacket {

    /**
     * Raw packet of data.
     */
    private String rawData;

    /**
     * Port data was read from.
     */
    private String port;

    /**
     * Date received.
     */
    private Date received;

    /**
     * Measurement was overload.
     */
    private boolean overload;

    /**
     * Measurement is of stable data.
     */
    private boolean stableData;

    /**
     * Measurement is of unstable data.
     */
    private boolean unstableData;

    /**
     * Measurement is in grams.
     */
    private boolean grams;

    /**
     * Measurement is in ounces.
     */
    private boolean ounces;

    /**
     * Weight.
     */
    private BigDecimal weight;

    /**
     * Milliseconds since last change.
     */
    private long millisecondsSinceLastChange;

    /**
     * Creates a new ScalePacket.
     * @param rawData
     * @param port
     * @param received
     * @param overload
     * @param stableData
     * @param unstableData
     * @param grams
     * @param ounces
     * @param weight
     */
    public ScalePacket( String rawData, String port, Date received, boolean overload, boolean stableData,
                    boolean unstableData, boolean grams, boolean ounces, BigDecimal weight ) {
        this.rawData = rawData;
        this.port = port;
        this.received = received;
        this.overload = overload;
        this.stableData = stableData;
        this.unstableData = unstableData;
        this.grams = grams;
        this.ounces = ounces;
        this.weight = weight;
        this.millisecondsSinceLastChange = 0;
    }

    /**
     * Creates a new ScalePacket.
     *
     * @param rawData
     * @param port
     * @param received
     * @param overload
     * @param stableData
     * @param unstableData
     * @param grams
     * @param ounces
     * @param weight
     */
    public ScalePacket( String rawData, String port, Date received, boolean overload, boolean stableData,
                        boolean unstableData, boolean grams, boolean ounces, BigDecimal weight,
                        long millisecondsSinceLastChange ) {
        this.rawData = rawData;
        this.port = port;
        this.received = received;
        this.overload = overload;
        this.stableData = stableData;
        this.unstableData = unstableData;
        this.grams = grams;
        this.ounces = ounces;
        this.weight = weight;
        this.millisecondsSinceLastChange = millisecondsSinceLastChange;
    }

    // accessor and mutator methods

    public String getRawData() {
        return rawData;
    }

    public void setRawData( String rawData ) {
        this.rawData = rawData;
    }

    public String getPort() {
        return port;
    }

    public void setPort( String port ) {
        this.port = port;
    }

    public Date getReceived() {
        return received;
    }

    public void setReceived( Date received ) {
        this.received = received;
    }

    public boolean isOverload() {
        return overload;
    }

    public void setOverload( boolean overload ) {
        this.overload = overload;
    }

    public boolean isStableData() {
        return stableData;
    }

    public void setStableData( boolean stableData ) {
        this.stableData = stableData;
    }

    public boolean isUnstableData() {
        return unstableData;
    }

    public void setUnstableData( boolean unstableData ) {
        this.unstableData = unstableData;
    }

    public boolean isGrams() {
        return grams;
    }

    public void setGrams( boolean grams ) {
        this.grams = grams;
    }

    public boolean isOunces() {
        return ounces;
    }

    public void setOunces( boolean ounces ) {
        this.ounces = ounces;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight( BigDecimal weight ) {
        this.weight = weight;
    }

    public long getMillisecondsSinceLastChange() {
        return millisecondsSinceLastChange;
    }

    public void setMillisecondsSinceLastChange( long millisecondsSinceLastChange ) {
        this.millisecondsSinceLastChange = millisecondsSinceLastChange;
    }

    // other methods

    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof ScalePacket ) ) {
            return false;
        }

        final ScalePacket scalePacket = ( ScalePacket ) o;

        if ( grams != scalePacket.grams ) {
            return false;
        }
        if ( ounces != scalePacket.ounces ) {
            return false;
        }
        if ( overload != scalePacket.overload ) {
            return false;
        }
        if ( stableData != scalePacket.stableData ) {
            return false;
        }
        if ( unstableData != scalePacket.unstableData ) {
            return false;
        }
        if ( port != null ? !port.equals( scalePacket.port ) : scalePacket.port != null ) {
            return false;
        }
        if ( rawData != null ? !rawData.equals( scalePacket.rawData ) : scalePacket.rawData != null ) {
            return false;
        }
        if ( received != null ? !received.equals( scalePacket.received ) : scalePacket.received != null ) {
            return false;
        }
        if ( weight != null ? !weight.equals( scalePacket.weight ) : scalePacket.weight != null ) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = ( rawData != null ? rawData.hashCode() : 0 );
        result = 29 * result + ( port != null ? port.hashCode() : 0 );
        result = 29 * result + ( received != null ? received.hashCode() : 0 );
        result = 29 * result + ( overload ? 1 : 0 );
        result = 29 * result + ( stableData ? 1 : 0 );
        result = 29 * result + ( unstableData ? 1 : 0 );
        result = 29 * result + ( grams ? 1 : 0 );
        result = 29 * result + ( ounces ? 1 : 0 );
        result = 29 * result + ( weight != null ? weight.hashCode() : 0 );
        return result;
    }

    public String toString() {
        return "ScalePacket{" +
                "rawData='" + rawData + "'" +
                ", port='" + port + "'" +
                ", received=" + received +
                ", overload=" + overload +
                ", stableData=" + stableData +
                ", unstableData=" + unstableData +
                ", grams=" + grams +
                ", ounces=" + ounces +
                ", weight=" + weight +
                "}";
    }
}
