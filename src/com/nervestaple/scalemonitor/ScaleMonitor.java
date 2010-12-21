package com.nervestaple.scalemonitor;

import ch.randelshofer.quaqua.QuaquaManager;
import com.nervestaple.scalemonitor.commportidentifierwrapper.CommPortIdentifierWrapper;
import com.nervestaple.scalemonitor.form.MainWindow;
import com.nervestaple.scalemonitor.form.PortListWindow;
import com.nervestaple.scalemonitor.form.portlist.portinfo.PortInfoController;
import com.nervestaple.scalemonitor.outputwriter.OutputWriter;
import com.nervestaple.scalemonitor.outputwriter.rossoutputwriter.RossOutputWriter;
import com.nervestaple.scalemonitor.outputwriter.rossoutputwriter.RossOutputWriterException;
import com.nervestaple.scalemonitor.serialportreader.SerialPortReader;
import com.nervestaple.scalemonitor.serialportreader.SerialPortReaderException;
import com.nervestaple.scalemonitor.serialportreader.SerialPortReaderFactory;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.RawPacketParserException;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.ScalePacket;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.rawpacketparserfilter.RawPacketParserFilter;
import com.nervestaple.scalemonitor.serialportreader.rawpacketparser.rawpacketparserfilter.RawPacketParserFilterListener;
import com.nervestaple.utility.Platform;
import com.nervestaple.utility.swing.GuiSwing;
import gnu.io.CommPortIdentifier;
import net.roydesign.ui.StandardMacAboutFrame;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 *  Provides an application for logging the data from multiple scales connected over serial ports.
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class ScaleMonitor {

    /**
     * Logger instance.
     */
    private Logger logger = Logger.getLogger( this.getClass() );

    /**
     * Application version number.
     */

    private final static String VERSION = "1.6";

    /**
     * MainWindow.
     */
    private MainWindow mainwindow;

    /**
     * List of open ports.
     */
    private List serialportreaders;

    /**
     * List of PortInfoController objects.
     */
    private List listPortInfoController;

    /**
     * Hashtable mapping ports to their controllers.
     */
    private Hashtable hashtablePortAndControllers;

    /**
     * About window.
     */
    private StandardMacAboutFrame aboutWindow;

    /**
     * Flag to indicate if we should be logging the data.
     */
    private static boolean logData = true;

    /**
     * Output writer.
     */
    private OutputWriter outputwriter;

    /**
     * Creates a new MainWindoDemo.
     */
    public ScaleMonitor() {

        // setup port list
        serialportreaders = new ArrayList();

        // get a mainwindow
        mainwindow = new MainWindow( "ScaleMonitor" );

        // listen for the window closing
        mainwindow.addWindowListener( new WindowAdapter() {

            /**
             * Invoked when a window is in the process of being closed. The close operation can be overridden at this
             * point.
             */
            public void windowClosing( WindowEvent e ) {

                quit();
            }
        } );

        // setup the about window
        setupAboutWindow();

        // get a menu bar
        AppMenuBar appmenubar = new AppMenuBar();

        // setup listeners for the quit menu item
        appmenubar.getQuitMenuItem().addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                quit();
            }
        });

        // setup listener for the about menu item
        appmenubar.getAboutMenuItem().addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                aboutWindow.setVisible( true );
            }
        });

        // setup listener for the choose port menu item
        appmenubar.getChoosePortsMenuItem().addActionListener( new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                choosePorts();
            }
        });

        // add the menu bar
        mainwindow.setJMenuBar( appmenubar );

        // center and display the window
        GuiSwing.centerWindow( mainwindow );
        mainwindow.setVisible( true );

        setupTable();

        listenToToolbarModel();

        choosePorts();
    }

    /**
     * Bootstraps the application.
     *
     * @param args
     */
    public static void main( final String[] args ) {

        configureUI();

        new ScaleMonitor();
    }

    public void quit() {

        logger.debug( "Quitting application" );

        closePorts();

        if( outputwriter != null ) {
            outputwriter.close();
        }

        System.exit( 0 );
    }

    public void choosePorts() {

        closePorts();

        if( outputwriter != null ) {
            outputwriter.close();
        }

        setupTable();

        setupPorts();
    }

    // private methods

    private void closePorts() {

        if( serialportreaders != null ) {

            Iterator iterator = serialportreaders.iterator();
            while( iterator.hasNext() ) {

                SerialPortReader serialportreader = ( SerialPortReader ) iterator.next();

                serialportreader.close();
            }
        }
    }

    private void setupPorts() {

        // create a new hashtable for mapping port names to their controllers
        hashtablePortAndControllers = new Hashtable();

        // get a port list window
        final PortListWindow portlistwindow = new PortListWindow();

        // setup an okay action
        ActionListener actionListenerOkay = new ActionListener() {
            public void actionPerformed( ActionEvent e ) {

                portlistwindow.setVisible( false );

                listPortInfoController = portlistwindow.getController().getModel().getPorts();

                chooseOutputFile();
            }
        };

        // pass the actions to the window
        portlistwindow.getController().getModel().setListenerOkay( actionListenerOkay );

        // add a menu bar on the mac
        if( Platform.checkMacintosh() ) {

            // create a menu bar
            AppMenuBar appmenubarThis = new AppMenuBar();

            // add a listener to the quit menu item
            appmenubarThis.getQuitMenuItem().addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    quit();
                }
            } );

            appmenubarThis.getAboutMenuItem().setEnabled( false );
            appmenubarThis.getChoosePortsMenuItem().setEnabled( false );

            portlistwindow.setJMenuBar( appmenubarThis );
        }

        // center and display the window
        GuiSwing.centerWindow( portlistwindow );
        portlistwindow.setVisible( true );
    }

    private void chooseOutputFile() {

        // clear the output file and buffer
        outputwriter = null;

        // display a file chooser
        JFileChooser filechooser = new JFileChooser();

        // set the chooser to select directories
        filechooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );

        // display the chooser and get the status
        int status = filechooser.showSaveDialog( mainwindow );

        if( status == JFileChooser.APPROVE_OPTION ) {

            // get the chosen file
            File fileOutChosen = filechooser.getSelectedFile();

            try {
                outputwriter = new RossOutputWriter( fileOutChosen );
            } catch( RossOutputWriterException e ) {
                logger.warn( e );
            }
        }

        logger.debug( "Start listening to ports" );

        doMonitorPorts( listPortInfoController );
    }

    private void doMonitorPorts( List ports ) {

        PortInfoController[] portinfocontrollerArray =
                ( PortInfoController[] ) ports.toArray( new PortInfoController[ ports.size() ] );
        for( int index = 0; index < portinfocontrollerArray.length; index ++ ) {

            final PortInfoController portinfocontroller = portinfocontrollerArray[ index ];

            logger.debug( portinfocontroller.getModel().getName() + ", " + portinfocontroller.getModel().isMonitor() +
                    ", " + portinfocontroller.getModel().getCommPortIdWrapper().getName() );
            if( portinfocontroller.getModel().isMonitor() != null
                    && portinfocontroller.getModel().isMonitor().booleanValue() ) {

                CommPortIdentifierWrapper commportidwrapper = portinfocontroller.getModel().getCommPortIdWrapper();

                // add an entry in our hashtable of ports and controllers
                hashtablePortAndControllers.put(
                        StringUtils.strip( commportidwrapper.getName(), "/."), portinfocontroller );

                // check to see if this is a serial port
                if( commportidwrapper.getPortType() == CommPortIdentifier.PORT_SERIAL ) {

                    logger.debug( "Listening to serial port " + commportidwrapper.getName() );

                    // start listening to the port
                    try {
                        listenToPort( commportidwrapper );
                    } catch( Exception e ) {
                        logger.warn( commportidwrapper.getName() + ": " + e.getMessage() );
                    }
                }
            }
        }
    }

    /**
     * Sets up thge table.
     */
    private void setupTable() {

        List headers = new ArrayList();
        headers.add( "Scale" );
        headers.add( "Header" );
        headers.add( "Weight" );
        headers.add( "Unit" );
        headers.add( "Received" );

        mainwindow.getDataTableController().getModel().setColumnHeaders( headers );
    }

    /**
     * Sets up listeners on the toolbar.
     */
    private void listenToToolbarModel() {

        mainwindow.getDataToolBarController().getModel().addPropertyChangeListener( "record",
                new PropertyChangeListener() {

                    /**
                     * This method gets called when a bound property is changed.
                     *
                     * @param evt A PropertyChangeEvent object describing the event source and the property that has changed.
                     */
                    public void propertyChange( PropertyChangeEvent evt ) {

                        logData = ( ( Boolean ) evt.getNewValue() ).booleanValue();
                        logger.debug( "logData: " + logData );
                    }
                });
    }

    /**
     * Handles the arrive of a new packet from a scale.
     * @param packet
     */
    private void handlePacketReceived( ScalePacket packet ) {

        logger.debug( "Received packet: " + packet );

        String header = null;
        String unit = null;

        // get the controller for this port
        PortInfoController portinfocontroller =
                ( PortInfoController ) hashtablePortAndControllers.get( StringUtils.strip( packet.getPort(), "/." ) );

        // write out the packet's data
        outputwriter.writePacket( packet, portinfocontroller );

        // format the packet to display on screen
        if( packet.isOverload() ) {
            header = "Overload";
        } else if( packet.isStableData() ) {
            header = "Stable";
        } else if( packet.isUnstableData() ) {
            header = "Unstable";
        }

        if( packet.isGrams() ) {
            unit = "grams";
        } else if( packet.isOunces() ) {
            unit = "ounces";
        }

        Object[] row = new Object[ 5 ];
        logger.debug( portinfocontroller );
        logger.debug( portinfocontroller.getModel() );
        logger.debug( portinfocontroller.getModel().getName() );
        row[ 0 ] = portinfocontroller.getModel().getName();
        row[ 1 ] = header;

        if( portinfocontroller.getModel().getTare() == null ) {
            row[ 2 ] = packet.getWeight();
        } else {
            row[ 2 ] = packet.getWeight().add(
                    new BigDecimal( portinfocontroller.getModel().getTare() .doubleValue() ) );
        }

        row[ 3 ] = unit;
        row[ 4 ] = packet.getReceived();

        mainwindow.getDataTableController().getModel().addDataRow( row );
    }

    /**
     * Opens the port and starts a new thread for listening to that port.
     *
     * @param commportidwrapper
     * @throws Exception
     */
    private void listenToPort( final CommPortIdentifierWrapper commportidwrapper ) throws Exception {

        // get a serial port for the id, use the AND EW 300A type
        final SerialPortReader serialportreader = SerialPortReaderFactory.getSerialPortReader( commportidwrapper,
                                                                                               ScaleType.AND_EW_300A );

        // start a thread to read from the port
        Thread thread = new Thread( new Runnable() {
            public void run() {

                // create a new listener
                RawPacketParserFilterListener listener = new RawPacketParserFilterListener() {

                    /**
                     * Called when a new packet is filtered.
                     *
                     * @param scalepacket
                     */
                    public void newPacket( ScalePacket scalepacket ) {

                        if( logData ) {
                            handlePacketReceived( scalepacket );
                        }
                    }
                };


                try {

                    // create a filter
                    RawPacketParserFilter filter = new RawPacketParserFilter( ScaleType.AND_EW_300A, 0, 0 );

                    // add our listener to the filter
                    filter.addRawPacketParserFilterListener( listener );

                    // add our filter listener to the port
                    serialportreader.addSerialPortReaderListener( filter );
                } catch( RawPacketParserException e ) {
                    logger.warn( e );
                }

                // open the port
                try {
                    serialportreader.open();
                } catch ( SerialPortReaderException e ) {
                    logger.warn( e );
                }

                serialportreaders.add( serialportreader );
            }
        } );
        thread.start();
    }

    private void setupAboutWindow() {

        aboutWindow = new StandardMacAboutFrame( "Scale Monitor", VERSION );
        aboutWindow.setApplicationIcon( new ImageIcon( getClass().getResource( "images/scale-128.png" ) ) );
        aboutWindow.setCopyright( "2005 Nervestaple Development" );
        aboutWindow.setCredits( "<center><p>by Christopher Miles</p></center>" +
                "<p>This is an application for monitoring and logging " +
                "the data from an electronic scale attached to the " +
                "serial port.</p>" +
                "<p>If you notice any problems or unexpected behavior, " +
                "please send an e-mail with the description of your issue to " +
                "<a href=\"mailto:twitch@nervestaple.com\">"
                + "twitch@nervestaple.com</a>", "text/html" );
        aboutWindow.setTitle( "About Scale Monitor..." );
        aboutWindow.setBuildVersion( VERSION );

        // add a menu bar on the mac
        if( Platform.checkMacintosh() ) {

            // create a menu bar
            AppMenuBar appmenubarThis = new AppMenuBar();

            // add a listener to the quit menu item
            appmenubarThis.getQuitMenuItem().addActionListener( new ActionListener() {
                public void actionPerformed( ActionEvent e ) {
                    quit();
                }
            } );

            appmenubarThis.getAboutMenuItem().setEnabled( false );
            appmenubarThis.getChoosePortsMenuItem().setEnabled( false );

            aboutWindow.setJMenuBar( appmenubarThis );
        }

        GuiSwing.centerWindow( aboutWindow );
    }

    /**
     * Sets the appropriate look and feel.
     */
    private static void configureUI() {

        Logger logger = Logger.getLogger( "com.millersamuel.rcf.client.rcfswing.RCFSwing" );

        if( Platform.checkMacintosh() ) {

            // set system properties
            System.setProperty( "apple.laf.useScreenMenuBar", "true" );
            System.setProperty( "com.apple.mrj.application.live-resize",
                    "true" );
            System.setProperty( "apple.awt.showGrowBox", "true" );
            System.setProperty( "apple.awt.brushMetalLook", "false" );
            System.setProperty( "Quaqua.visualMargin", "1,1,1,1" );
            System.setProperty( "Quaqua .selectionStyle", "dark" );
            System.setProperty( "Quaqua.opaque", "true" );

            // use the quaqua look and feel
            try {
                UIManager.setLookAndFeel( QuaquaManager.getLookAndFeelClassName() );
                logger.debug( "Using Quaqua look and feel: "
                        + QuaquaManager.getLookAndFeelClassName() );
            } catch( Exception e ) {
                logger.warn( e );
            }
        } else {

            try {
                UIManager.setLookAndFeel( "net.java.plaf.windows.WindowsLookAndFeel" );
                logger.debug( "Usign WinLAF look and feel" );
            } catch( ClassNotFoundException e ) {
                logger.warn( e );
            } catch( InstantiationException e ) {
                logger.warn( e );
            } catch( IllegalAccessException e ) {
                logger.warn( e );
            } catch( UnsupportedLookAndFeelException e ) {
                logger.warn( e );
            } finally {

                try {
                    UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
                    logger.debug( "Using "
                            +
                            UIManager.getSystemLookAndFeelClassName()
                            + " look and feel" );
                } catch( Exception e ) {
                    logger.debug( e );
                }
            }
        }
    }
}
