package com.nervestaple.scalemonitor.form.datatoolbar;

import org.apache.log4j.Logger;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * Provides a controller for the DataToolBar.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class DataToolBarController {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Data model.
     */
    private DataToolBarModel model;

    /**
     * View.
     */
    private DataToolBarView view;

    /**
     * Creates a new DataToolBarController.
     */
    public DataToolBarController() {

        // get the model
        model = new DataToolBarModel();

        // get the view
        view = new DataToolBarView( model );

        model.addPropertyChangeListener( "record", new PropertyChangeListener() {

            /**
             * This method gets called when a bound property is changed.
             *
             * @param evt A PropertyChangeEvent object describing the event source and the property that has changed.
             */
            public void propertyChange( PropertyChangeEvent evt ) {

                view.setRecord( !( ( Boolean ) evt.getNewValue() ).booleanValue() );
                model.setPause( !( ( Boolean ) evt.getNewValue() ).booleanValue() );
            }
        } );

        model.addPropertyChangeListener( "pause", new PropertyChangeListener() {

            /**
             * This method gets called when a bound property is changed.
             *
             * @param evt A PropertyChangeEvent object describing the event source and the property that has changed.
             */
            public void propertyChange( PropertyChangeEvent evt ) {

                view.setPause( !( ( Boolean ) evt.getNewValue() ).booleanValue() );
                model.setRecord( !( ( Boolean ) evt.getNewValue() ).booleanValue() );
            }
        } );

        // set initial button status
        view.setRecord( !model.isRecord() );
        view.setPause( !model.isPause() );
    }

    public DataToolBarModel getModel() {
        return model;
    }

    public DataToolBarView getView() {
        return view;
    }
}
