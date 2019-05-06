package ch.hearc;

import ch.hearc.rmi.CommandeLed;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;
import com.github.mbelling.ws281x.*;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author jonatan.baumgart
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Ws281xLedStrip led = new Ws281xLedStrip(10, 18, 800000, 10, 255, 0, false, LedStripType.WS2811_STRIP_RGB, false);
            led.setStrip(0, 0, 0);
            led.render();

            CommandeLed commande = new CommandeLed(led);
            RmiURL rmiURL = new RmiURL("commande_led");
            RmiTools.shareObject(commande, rmiURL);

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

        } catch (RemoteException | MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
