package com.nervestaple.scalemonitor.form.portlist;

import com.nervestaple.scalemonitor.ScaleType;
import com.nervestaple.scalemonitor.commportidentifierwrapper.CommPortIdentifierWrapper;
import com.nervestaple.scalemonitor.commportidentifierwrapper.CommPortIdentifierWrapperFactory;
import com.nervestaple.scalemonitor.form.portlist.portinfo.PortInfoController;
import org.apache.log4j.Logger;

import gnu.io.CommPortIdentifier;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * Provides a controller for the PortList.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class PortListController {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Model for the controller.
     */
    private PortListModel model;

    /**
     * View for the controller.
     */
    private PortListView2 view;

    /**
     * Creates a new PortListController.
     * @param commportids
     */
    public PortListController( Collection commportids ) {

        // get a new model
        model = new PortListModel();

        model.addPropertyChangeListener( new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent evt ) {
                logger.debug( evt.getPropertyName() + ": " + evt.getOldValue() + " -> " + evt.getNewValue() );
            }
        });

        // get a new view
        view = new PortListView2( model );

        setPorts( commportids );

        initializeListeners();
    }

    public List getPorts() {

        return( model.getPorts() );
    }

    // accessor methods

    public PortListModel getModel() {
        return model;
    }

    public PortListView2 getView() {
        return view;
    }

    // private methods

    private void initializeListeners() {

        ActionListener listenerAddTestPort = new ActionListener() {
            public void actionPerformed( ActionEvent event ) {

                logger.debug( "Adding test port" );
                // get a wrapper around a test port for the AND EW 300A
                CommPortIdentifierWrapper commporttest1 =
                        CommPortIdentifierWrapperFactory.getCommPortIdentifierWrapperTest( ScaleType.AND_EW_300A );

                // get a port info controller for this port
                PortInfoController portinfocontroller =
                        new PortInfoController( commporttest1 );

                // add the port to the model
                model.getPorts().add( portinfocontroller );

                // add the port to the view
                view.addNewPortController( portinfocontroller );
            }
        };
        model.setListenerAddTestPort( listenerAddTestPort );
    }

    private void setPorts( Collection commportids ) {

        logger.debug( "Received " + commportids.size() + " ports" );

        // create a list to hold the port info controllers
        List listPortControllers = new ArrayList();

        // get an array of comm ports
        CommPortIdentifier[] commports =
                ( CommPortIdentifier[] ) commportids.toArray( new CommPortIdentifier[ commportids.size() ] );

        for( int index = 0; index < commports.length; index++ ) {

            // get a port info controller for this port
            PortInfoController  portinfocontroller =
                    new PortInfoController( CommPortIdentifierWrapperFactory.getCommPortIndentifierWrapper(
                            commports[ index ] ) );

            // add the controller to the list
            logger.debug( portinfocontroller );
            listPortControllers.add( portinfocontroller );
        }

        // update the model
        model.setPorts( listPortControllers );
    }
}
