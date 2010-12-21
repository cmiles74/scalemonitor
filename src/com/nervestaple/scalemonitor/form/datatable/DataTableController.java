package com.nervestaple.scalemonitor.form.datatable;

import org.apache.log4j.Logger;

/**
 * Provides a controller for the DataTable.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class DataTableController {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Data model.
     */
    private DataTableModel model;

    /**
     * View.
     */
    private DataTableView view;

    /**
     * Creates a new DataTableController.
     */
    public DataTableController() {

        // get a data model
        model = new DataTableModel();

        // get a view
        view = new DataTableView( model );
    }

    public DataTableModel getModel() {
        return model;
    }

    public DataTableView getView() {
        return view;
    }
}
