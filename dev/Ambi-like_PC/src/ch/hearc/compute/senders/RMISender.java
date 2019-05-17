package ch.hearc.compute.senders;

import ch.hearc.Config;
import ch.hearc.rmi.CommandeLed_I;
import java.rmi.Remote;
import com.bilat.tools.reseau.rmi.RmiTools;
import com.bilat.tools.reseau.rmi.RmiURL;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * sends command via rmi
 *
 * @author jonatan.baumgart
 */
public class RMISender implements Sender_I {

    private static RMISender instance = null;

    private CommandeLed_I commande;

    /**
     * connects to raspberry pi
     */
    private RMISender() {
        try {
            //InetAddress serveurInetAdress = InetAddress.getByName(Config.getConfig().getRaspIp());
            InetAddress serveurInetAdress = InetAddress.getByName("192.168.100.100");
            RmiURL rmiURL = new RmiURL("commande_led", serveurInetAdress, RmiTools.PORT_RMI_DEFAUT);
            Remote remote = RmiTools.connectionRemoteObjectBloquant(rmiURL);
            commande = (CommandeLed_I) remote;
        } catch (RemoteException | MalformedURLException | UnknownHostException ex) {
            Logger.getLogger(RMISender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * singletone pattern so the connection is only madae once
     *
     * @return
     */
    public static synchronized RMISender getInstance() {
        if (instance == null) {
            instance = new RMISender();
        }
        return instance;
    }

    /**
     * send new color to raspberry pi
     *
     * @param nbLed led number on ledstrip
     * @param r red color, beetween 1 and 255
     * @param g green color, beetween 1 and 255
     * @param b blue color, beetween 1 and 255
     */
    @Override
    public void send(int nbLed, int r, int g, int b) {

        r = (int) ((r / 255f) * Config.getConfig().getLumMax());
        g = (int) ((g / 255f) * Config.getConfig().getLumMax());
        b = (int) ((b / 255f) * Config.getConfig().getLumMax());

        r = checkColor(r);
        g = checkColor(g);
        b = checkColor(b);

        try {
            commande.setLed(nbLed, r, g, b);
        } catch (RemoteException ex) {
            Logger.getLogger(RMISender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int checkColor(int color) {
        if (color > 255) {
            return 255;
        }
        if (color < 0) {
            return 0;
        }
        return color;
    }
}
