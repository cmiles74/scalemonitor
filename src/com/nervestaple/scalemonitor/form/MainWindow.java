package com.nervestaple.scalemonitor.form;

import com.nervestaple.scalemonitor.form.datatable.DataTableController;
import com.nervestaple.scalemonitor.form.datatoolbar.DataToolBarController;
import org.apache.log4j.Logger;

import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
 * Provides the main window for the application.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class MainWindow extends JFrame {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * DataToolBar controller.
     */
    private DataToolBarController dataToolBarController;

    /**
     * DataTable controller.
     */
    private DataTableController dataTableController;

    /**
     * Creates a new MainWindow.
     */
    public MainWindow() {

        super();

        setupWindow( null );
    }

    /**
     * Creates a new MainWindow.
     * @param title
     */
    public MainWindow( String title ) {

        super();

        setupWindow( title );
    }

    public DataToolBarController getDataToolBarController() {
        return dataToolBarController;
    }

    public DataTableController getDataTableController() {
        return dataTableController;
    }

    // private methods

    /**
     * Sets up the window.
     * @param title
     */
    private void setupWindow( String title ) {

        setTitle( title );

        // get the toolbar
        dataToolBarController = new DataToolBarController();

        /// get the data table
        dataTableController = new DataTableController();

        // add the views to this frame
        getContentPane().add( dataToolBarController.getView(), BorderLayout.NORTH );
        getContentPane().add( dataTableController.getView(), BorderLayout.CENTER );

        setSize( 500, 480 );
        setResizable( true );
    }
}
