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
 * envoie des commandes par rmi
 *
 * @author jba
 */
public class RMISender implements Sender_I {

    private static RMISender instance = null;

    private CommandeLed_I commande;

    private RMISender() {
        try {
            InetAddress serveurInetAdress = InetAddress.getByName(Config.getConfig().getRaspIp());
            RmiURL rmiURL = new RmiURL("commande_led", serveurInetAdress, RmiTools.PORT_RMI_DEFAUT);
            Remote remote = RmiTools.connectionRemoteObjectBloquant(rmiURL);
            commande = (CommandeLed_I) remote;
        } catch (RemoteException | MalformedURLException | UnknownHostException ex) {
            Logger.getLogger(RMISender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static synchronized RMISender getInstance() {
        if (instance == null) {
            instance = new RMISender();
        }
        return instance;
    }

    @Override
    public void send(int nbLed, int r, int g, int b) {
        try {
            commande.setLed(nbLed, r, g, b);
        } catch (RemoteException ex) {
            Logger.getLogger(RMISender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
