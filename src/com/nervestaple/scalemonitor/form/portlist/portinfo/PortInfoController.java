package com.nervestaple.scalemonitor.form.portlist.portinfo;

import com.nervestaple.scalemonitor.commportidentifierwrapper.CommPortIdentifierWrapper;
import org.apache.log4j.Logger;

/**
 * Provides a controller for the PortInfo.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class PortInfoController {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Model for the view.
     */
    private PortInfoModel model;

    /**
     * View for the model.
     */
    private PortInfoView view;

    public PortInfoController( CommPortIdentifierWrapper commportIdWrapper) {

        // create a new model
        model = new PortInfoModel( commportIdWrapper );

        // create a new view
        view = new PortInfoView( model );

        // set model values
        model.setMonitor( new Boolean( false ) );

        String name = commportIdWrapper.getName();
        String[] chunks = name.split( "/" );
        if( chunks.length > 0 ) {

            model.setName( chunks[ ( chunks.length - 1 ) ] );
        } else {
            model.setName( commportIdWrapper.getName() );
        }
    }

    // accessor methods

    public PortInfoModel getModel() {
        return model;
    }

    public PortInfoView getView() {
        return view;
    }

    // other methods

    
    public String toString() {
        return "PortInfoController{" +
                "model=" + model +
                ", view=" + view +
                "}";
    }
}
