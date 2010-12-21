package com.nervestaple.scalemonitor.form.portlist.portinfo;

import com.nervestaple.scalemonitor.commportidentifierwrapper.CommPortIdentifierWrapper;
import com.nervestaple.scalemonitor.ScaleType;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

/**
 * Provides a simple data object with port information.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class PortInfoModel {

    /**
     * CommPortIdentifierWrapper for this port.
     */
    private CommPortIdentifierWrapper commPortIdWrapper;

    /**
     * Monitor this port.
     */
    private Boolean monitor;

    /**
     * Name of the port.
     */
    private String name;

    /**
     * The type of scale attached to this port.
     */
    private ScaleType scaletype;

    /**
     * Tare value.
     */
    private Double tare;

    /**
     * Property change support.
     */
    private transient PropertyChangeSupport propertychangesupport;

    /**
     * Creates a new PortInfoModel object.
     * @param commPortIdWrapper
     */
    public PortInfoModel( CommPortIdentifierWrapper commPortIdWrapper ) {

        propertychangesupport = new PropertyChangeSupport( this );
        
        this.commPortIdWrapper = commPortIdWrapper;
    }

    // property change support methods

    public void addPropertyChangeListener( PropertyChangeListener listener ) {
        propertychangesupport.addPropertyChangeListener( listener );
    }

    public void removePropertyChangeListener( PropertyChangeListener listener ) {
        propertychangesupport.removePropertyChangeListener( listener );
    }

    public void addPropertyChangeListener( String property,
                                           PropertyChangeListener listener ) {
        propertychangesupport.addPropertyChangeListener( property, listener );
    }

    public void removePropertyChangeListener( String property,
                                              PropertyChangeListener listener ) {
        propertychangesupport.removePropertyChangeListener( property, listener );
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return ( propertychangesupport.getPropertyChangeListeners() );
    }

    public void firePropertyChange( String propertyName, Object valueOld, Object valueNew ) {
        propertychangesupport.firePropertyChange( propertyName, valueOld, valueNew );
    }

    // accessor and mutator methods

    public CommPortIdentifierWrapper getCommPortIdWrapper() {
        return commPortIdWrapper;
    }

    public void setCommPortIdWrapper( CommPortIdentifierWrapper commPortIdWrapper ) {
        CommPortIdentifierWrapper valueOld = this.commPortIdWrapper;
        this.commPortIdWrapper = commPortIdWrapper;
        propertychangesupport.firePropertyChange( "commPortIdWrapper", valueOld, commPortIdWrapper );
    }

    public Boolean isMonitor() {
        return monitor;
    }

    public void setMonitor( Boolean monitor ) {
        Boolean valueOld = this.monitor;
        this.monitor = monitor;
        propertychangesupport.firePropertyChange( "monitor", valueOld, monitor );
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        String valueOld = this.name;
        this.name = name;
        propertychangesupport.firePropertyChange( "name", valueOld, name );
    }

    public ScaleType getScaletype() {
        return scaletype;
    }

    public void setScaletype( ScaleType scaletype ) {
        ScaleType valueOld = this.scaletype;
        this.scaletype = scaletype;
        propertychangesupport.firePropertyChange( "scaletype", valueOld, scaletype );
    }

    public Double getTare() {
        return tare;
    }

    public void setTare( Double tare ) {
        Double valueOld = this.tare;
        this.tare = tare;
        propertychangesupport.firePropertyChange( "tare", valueOld, tare );
    }

    // other methods

    public boolean equals( Object o ) {
        if( this == o ) {
            return true;
        }
        if( !( o instanceof PortInfoModel ) ) {
            return false;
        }

        final PortInfoModel portInfoModel = ( PortInfoModel ) o;

        if( commPortIdWrapper != null ?
                !commPortIdWrapper.equals( portInfoModel.commPortIdWrapper ) :
                portInfoModel.commPortIdWrapper != null ) {
            return false;
        }
        if( monitor != null ? !monitor.equals( portInfoModel.monitor ) : portInfoModel.monitor != null ) {
            return false;
        }
        if( name != null ? !name.equals( portInfoModel.name ) : portInfoModel.name != null ) {
            return false;
        }
        if( scaletype != null ? !scaletype.equals( portInfoModel.scaletype ) : portInfoModel.scaletype != null ) {
            return false;
        }
        if( tare != null ? !tare.equals( portInfoModel.tare ) : portInfoModel.tare != null ) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result;
        result = ( commPortIdWrapper != null ? commPortIdWrapper.hashCode() : 0 );
        result = 29 * result + ( monitor != null ? monitor.hashCode() : 0 );
        result = 29 * result + ( name != null ? name.hashCode() : 0 );
        result = 29 * result + ( scaletype != null ? scaletype.hashCode() : 0 );
        result = 29 * result + ( tare != null ? tare.hashCode() : 0 );
        return result;
    }

    public String toString() {
        return "PortInfoModel{" +
                "commPortIdWrapper=" +
                commPortIdWrapper +
                ", monitor=" +
                monitor +
                ", name='" +
                name +
                "'" +
                ", scaletype=" +
                scaletype +
                ", tare=" +
                tare +
                "}";
    }
}
