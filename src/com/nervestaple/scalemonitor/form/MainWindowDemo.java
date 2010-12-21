package com.nervestaple.scalemonitor.form;

import org.apache.log4j.Logger;

/**
 * Demonstrates the MainWindow.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class MainWindowDemo {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * MainWindow.
     */
    private MainWindow mainwindow;

    /**
     * Creates a new MainWindoDemo.
     */
    public MainWindowDemo() {

        // get a mainwindow
        mainwindow = new MainWindow( "Main Window" );

        mainwindow.setVisible( true );
    }

    /**
     * Bootstraps the application.
     *
     * @param args
     */
    public static void main( final String[] args ) {

        new MainWindowDemo();
    }
}
