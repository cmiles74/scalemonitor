package com.nervestaple.scalemonitor.form.datatoolbar;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

/**
 *  Provides a data model for the DataToolBar.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class DataToolBarModel {

    /**
     * Flag to indicate recording.
     */
    private boolean record;

    /**
     * Flag to indicate paused.
     */
    private boolean pause;

    /**
     * Property change support.
     */
    private PropertyChangeSupport propertychangesupport;

    /**
     * Creates a new DataToolBarModel.
     */
    public DataToolBarModel() {

        record = true;
        pause = false;
        propertychangesupport = new PropertyChangeSupport( this );
    }

    // accessor and mutator methods

    public boolean isRecord() {
        return record;
    }

    public void setRecord( boolean record ) {
        boolean valueOld = this.record;
        this.record = record;
        propertychangesupport.firePropertyChange( "record", valueOld, record );
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause( boolean pause ) {
        boolean valueOld = this.pause;
        this.pause = pause;
        propertychangesupport.firePropertyChange( "pause", valueOld, pause );
    }

    // property change support methods

    public void addPropertyChangeListener( PropertyChangeListener listener ) {
        propertychangesupport.addPropertyChangeListener( listener );
    }

    public void removePropertyChangeListener( PropertyChangeListener listener ) {
        propertychangesupport.removePropertyChangeListener( listener );
    }

    public void addPropertyChangeListener( String property, PropertyChangeListener listener ) {
        propertychangesupport.addPropertyChangeListener( property, listener );
    }

    public void removePropertyChangeListener( String property, PropertyChangeListener listener ) {
        propertychangesupport.removePropertyChangeListener( property, listener );
    }
}
