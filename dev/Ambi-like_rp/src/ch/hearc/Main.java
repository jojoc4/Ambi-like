package ch.hearc;

import ch.hearc.rmi.CommandeLed;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;
import com.github.mbelling.ws281x.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jonatan.baumgart
 * @version 1
 * @since 06.05.2019
 */
public class Main {

    /**
     * Main program
     */
    public static void main(String[] args) {

        //initialisation of the ledStrip
        Ws281xLedStrip led = new Ws281xLedStrip(10, 18, 800000, 10, 255, 0, false, LedStripType.WS2811_STRIP_RGB, false);
        led.setStrip(0, 0, 0);
        led.render();

        //init command and share it over rmi 
        CommandeLed commande = new CommandeLed(led);
        try {
            RmiURL rmiURL = new RmiURL("commande_led", InetAddress.getByName("192.168.100.100"));
            RmiTools.shareObject(commande, rmiURL);

        } catch (RemoteException | MalformedURLException | UnknownHostException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(2);
        }

        //thread that turn the led strip of if no command is received during 2 seconds
        Runnable check = new Runnable() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - commande.lastSetLed > 2000) {
                    led.setStrip(0, 0, 0);
                    led.render();
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        new Thread(check).start();
        while (true) {

        }

    }

}
