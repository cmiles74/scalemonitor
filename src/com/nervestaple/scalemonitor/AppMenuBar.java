package com.nervestaple.scalemonitor;

import net.roydesign.ui.JScreenMenuBar;
import net.roydesign.app.Application;
import net.roydesign.app.AboutJMenuItem;
import net.roydesign.app.QuitMenuItem;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

/**
 * Provides a menu bar for the application
 *
 * @author Christopher Miles
 * @version 1.0
 */
public class AppMenuBar extends JScreenMenuBar {

    /**
     * MRJAdapter application instance.
     */
    private Application application;

    /**
     * File Menu.
     */
    private JMenu fileMenu;

    /**
     * Quit item.
     */
    private JMenuItem quitMenuItem;

    /**
     * About menu item.
     */
    private JMenuItem aboutMenuItem;

    /**
     * Choose Ports menu item.
     */
    private JMenuItem choosePortsMenuItem;

    /**
     * Creates a new AppMenuBar.
     */
    public AppMenuBar() {

        super();

        // get an application instance
        application = Application.getInstance();

        // create the file menu
        fileMenu = new JMenu( "File" );

        // add to the menu.
        add( fileMenu );

        // create the about menu item
        aboutMenuItem = application.getAboutJMenuItem();

        if( !AboutJMenuItem.isAutomaticallyPresent() ) {

            aboutMenuItem.setText( "About ScaleMonitor" );
            fileMenu.add( aboutMenuItem );
        }

        // create the choose ports menu item
        choosePortsMenuItem = new JMenuItem( "Choose ports to monitor..." );
        fileMenu.add( choosePortsMenuItem );

        // create the quit menu item
        quitMenuItem = application.getQuitJMenuItem();

        if( !QuitMenuItem.isAutomaticallyPresent() ) {

            quitMenuItem.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_X,
                    Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() ) );
            quitMenuItem.setText( "Exit" );
            fileMenu.add( quitMenuItem );
        }
    }

    // accessor methods

    public JMenu getFileMenu() {
        return fileMenu;
    }

    public JMenuItem getQuitMenuItem() {
        return quitMenuItem;
    }

    public JMenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public JMenuItem getChoosePortsMenuItem() {
        return choosePortsMenuItem;
    }
}
