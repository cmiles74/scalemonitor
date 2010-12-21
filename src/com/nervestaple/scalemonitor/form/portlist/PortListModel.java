package com.nervestaple.scalemonitor.form.portlist;

import java.util.List;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

/**
 * Provides a data model for the PortList.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class PortListModel {

    /**
     * List of PortInfoController objects.
     */
    private List ports;

    /**
     * Listener for the add test port action.
     */
    private ActionListener listenerAddTestPort;

    /**
     * Listener for the okay button.
     */
    private ActionListener listenerOkay;

    /**
     * Listener for the cancel button.
     */
    private ActionListener listenerCancel;

    /**
     * Property change support for the model.
     */
    private PropertyChangeSupport propertychangesupport;

    /**
     * Creates a new PortListModel.
     */
    public PortListModel() {

        // setup the list
        ports = new ArrayList();

        // setup property change support
        propertychangesupport = new PropertyChangeSupport( this );
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

    public List getPorts() {
        return ports;
    }

    public void setPorts( List ports ) {
        List valueOld = this.ports;
        this.ports = ports;
        propertychangesupport.firePropertyChange( "ports", valueOld, ports );
    }

    public ActionListener getListenerAddTestPort() {
        return listenerAddTestPort;
    }

    public void setListenerAddTestPort( ActionListener listenerAddTestPort ) {
        ActionListener valueOld = this.listenerAddTestPort;
        this.listenerAddTestPort = listenerAddTestPort;
        propertychangesupport.firePropertyChange( "listenerAddTestPort", valueOld, listenerAddTestPort );
    }

    public ActionListener getListenerOkay() {
        return listenerOkay;
    }

    public void setListenerOkay( ActionListener listenerOkay ) {
        ActionListener valueOld = this.listenerOkay;
        this.listenerOkay = listenerOkay;
        propertychangesupport.firePropertyChange( "listenerOkay", valueOld, listenerOkay );
    }

    public ActionListener getListenerCancel() {
        return listenerCancel;
    }

    public void setListenerCancel( ActionListener listenerCancel ) {
        ActionListener valueOld = this.listenerCancel;
        this.listenerCancel = listenerCancel;
        propertychangesupport.firePropertyChange( "listenerCancel", valueOld, listenerCancel );
    }
}
