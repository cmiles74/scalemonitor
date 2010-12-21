package com.nervestaple.scalemonitor.form;

import com.nervestaple.scalemonitor.form.portlist.PortListController;
import gnu.io.CommPortIdentifier;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Provides a window with a list of ports.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class PortListWindow extends JFrame {

    /**
     * Title of the window.
     */
    private final static String TITLE = "Select Ports";
    /**
     * PortListController.
     */
    private PortListController portlistcontroller;

    /**
     * Creates a new PortListWindow.
     */
    public PortListWindow() {

        super( TITLE );

        setupWindow();

        initializeListeners();

        setSize( 385, 400 );
    }

    // accessor methods

    public PortListController getController() {
        return portlistcontroller;
    }

    // private methods

    private void initializeListeners() {

        addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent event ) {

                setVisible( false );
            }
        });
    }

    private void setupWindow() {

        // get a list to hold the port ids
        List listPorts = new ArrayList();

        // get a list of ports
        Enumeration enumerationPorts = CommPortIdentifier.getPortIdentifiers();

        // loop through the list
        while( enumerationPorts.hasMoreElements() ) {

            // get the next port
            CommPortIdentifier commportid = ( CommPortIdentifier ) enumerationPorts.nextElement();

            // add the port to the list
            listPorts.add( commportid );
        }

        // get a port list controller
        portlistcontroller = new PortListController( listPorts );

        getContentPane().setLayout( new GridLayout( 1, 1 ) );
        getContentPane().add( portlistcontroller.getView() );
    }
}
