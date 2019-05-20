package ch.hearc;

import ch.hearc.compute.Computation_Ambilight;
import ch.hearc.compute.Computation_I;
import ch.hearc.compute.Computation_fixedColor;
import ch.hearc.compute.Computation_perso;
import ch.hearc.compute.senders.*;
import ch.hearc.gui.configurator.JPanelConfigurator;
import ch.hearc.gui.mainwindow.FrameMainWindow;
import ch.hearc.gui.creator.FrameCreator;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author jonatan.baumgart
 */
public class Main {

    private static Computation_I c;
    private static final String MODE = "TEST";
    //private static final String MODE = "RMI";
    
    private static int requestedMode = Computation_I.MODE_AMBILIGHT;

    public static void main(String[] args) {

        //verify that system tray is available, maybe not working on certain os
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        //start main computation Thread
        //createComputation();
        
        //test
        //new JPanelConfigurator();
        new FrameCreator();

        //add elements to systemTray
        SystemTray tray = SystemTray.getSystemTray();
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        //set tray icon
        URL path = Main.class.getResource("/ch/hearc/images/logo.png");
        Image image = new ImageIcon(path).getImage();

        PopupMenu menu = new PopupMenu();

        //add configuration menu, create and opens FrameMainWindow if pressed
        MenuItem messageItem = new MenuItem("Configuration");
        messageItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new FrameMainWindow();
    
            }
        });

        //add close option, quit the program
        menu.add(messageItem);

        MenuItem closeItem = new MenuItem("Fermer");
        closeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(closeItem);
        TrayIcon icon = new TrayIcon(image, "Ambi-like", menu);
        icon.setImageAutoSize(true);

        //add icon to tray
        try {
            tray.add(icon);
        } catch (AWTException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, "cannot add system tray icon, quit", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);

        }

    }

    /**
     * used to apply new configuration to the computation stop and destroy the
     * current computation and create the new one with the parameters saved in
     * config
     */
    public static void changeMode() {
        c.stopComputation();
        createComputation();
    }

    /**
     * creates the right new computation depending on Config content
     */
    private static void createComputation() {
        Sender_I sender = null;

        if ("TEST".equals(MODE)) {
            sender = new TestSender();
        } else if ("RMI".equals(MODE)) {
            sender = RMISender.getInstance();
        }
        
        System.out.println(Config.getConfig().getMode() + " contenu");
        switch (Config.getConfig().getMode()) {
            case Computation_I.MODE_AMBILIGHT:
                c = new Computation_Ambilight(sender);
                break;
            case Computation_I.MODE_FIXE:
                c = new Computation_fixedColor(sender, new Pixel(Config.getConfig().getColor()[0], Config.getConfig().getColor()[1], Config.getConfig().getColor()[2]));
                break;
            case Computation_I.MODE_PERSO:
                c = new Computation_perso(sender, ModePersonnalise.getMode(Config.getConfig().getPersoModeFile()));
        }
        Thread t = new Thread(c);
        t.setName("Computation");
        t.start();
    }
    
    public static int getTempMode(){
        return requestedMode;
    }
    
    public static void setTempMode(int mode){
        requestedMode = mode;
    }

}
