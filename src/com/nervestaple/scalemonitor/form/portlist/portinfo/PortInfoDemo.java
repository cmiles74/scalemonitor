package com.nervestaple.scalemonitor.form.portlist.portinfo;

import com.nervestaple.scalemonitor.ScaleType;
import com.nervestaple.scalemonitor.commportidentifierwrapper.CommPortIdentifierWrapperFactory;
import org.apache.log4j.Logger;

import javax.swing.JFrame;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Demonstrates the PortInfo view.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class PortInfoDemo extends JFrame {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Controller.
     */ 
    private PortInfoController controller;
    
    public PortInfoDemo() {
        
        // create a controller for the test port.
        controller = new PortInfoController( 
                CommPortIdentifierWrapperFactory.getCommPortIdentifierWrapperTest( ScaleType.AND_EW_300A ) );

        controller.getModel().addPropertyChangeListener( new PropertyChangeListener() {
            public void propertyChange( PropertyChangeEvent event ) {
                logger.debug( event.getPropertyName() + ": " + event.getOldValue() + " -> " + event.getNewValue() );
            }
        });

        // setup the window
        getContentPane().setLayout( new GridLayout( 1, 1 ) );
        getContentPane().add( controller.getView() );
        setSize( 300, 75 );

        setVisible( true );
    }
    
    /**
     * Bootstraps the application.
     *
     * @param args
     */
    public static void main( final String[] args ) {

        new PortInfoDemo();
    }
}
