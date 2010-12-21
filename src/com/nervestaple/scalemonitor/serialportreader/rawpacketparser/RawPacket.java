package com.nervestaple.scalemonitor.serialportreader.rawpacketparser;

import java.util.Date;

/**
 *  Provides a class for encapsulating information about a data packet.
 *
 * @author Christopher MMiles
 * @version 1.0
 */
public class RawPacket {

    /**
     * Port name.
     */
    private String port;

    /**
     * Data packet.
     */
    private String data;

    /**
     * Date read.
     */
    private Date date;

    /**
     * Creates a new RawPacket.
     * @param port
     * @param data
     * @param date
     */
    public RawPacket( String port, String data, Date date ) {
        this.port = port;
        this.data = data;
        this.date = date;
    }

    // accessor methods

    public String getData() {
        return data;
    }

    public Date getDate() {
        return date;
    }

    public String getPort() {
        return port;
    }

    // other methods

    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof RawPacket ) ) {
            return false;
        }

        final RawPacket rawPacket = ( RawPacket ) o;

        if ( data != null ? !data.equals( rawPacket.data ) : rawPacket.data != null ) {
            return false;
        }
        if ( date != null ? !date.equals( rawPacket.date ) : rawPacket.date != null ) {
            return false;
        }
        if ( port != null ? !port.equals( rawPacket.port ) : rawPacket.port != null ) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = ( port != null ? port.hashCode() : 0 );
        result = 29 * result + ( data != null ? data.hashCode() : 0 );
        result = 29 * result + ( date != null ? date.hashCode() : 0 );
        return result;
    }

    public String toString() {
        return "RawPacket{" +
                "port='" + port + "'" +
                ", data='" + data + "'" +
                ", date=" + date +
                "}";
    }
}
