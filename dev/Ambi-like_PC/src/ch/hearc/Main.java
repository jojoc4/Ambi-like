package ch.hearc;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Jonatan Baumgartner
 */
public class Main {

    public static void main(String[] args) {
        
        
        //verify that system tray is available, maybe not working on certain os
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        //start main computation Thread
        Computation c = new Computation();
        Thread t = new Thread(c);
        t.start();

        //add elements to systemTray
        SystemTray tray = SystemTray.getSystemTray();
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        //TODO choose trayIcon
        Image image = toolkit.getImage("/images/trayIcon.png");

        PopupMenu menu = new PopupMenu();

        //add configuration menu, create and opens JFramConfigurator if pressed
        MenuItem messageItem = new MenuItem("Configuration");
        messageItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new JFrameMainGUI();
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

}
