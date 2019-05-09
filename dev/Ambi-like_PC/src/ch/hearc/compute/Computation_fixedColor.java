package ch.hearc.compute;

import ch.hearc.Config;
import ch.hearc.Pixel;
import ch.hearc.compute.senders.Sender_I;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonatan.baumgart
 */
public class Computation_fixedColor extends Computation_I {

    private Sender_I sender;
    private Pixel p;

    public Computation_fixedColor(Sender_I sender, Pixel p) {
        this.sender = sender;
        this.p = p;
    }

    @Override
    public void run() {
        while (isRunning()) {
            try {
                for (int i = 0; i < Config.getConfig().getNbLedTotal(); i++) {
                    sender.send(i, p.getRed(), p.getGreen(), p.getBlue());
                }
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Computation_fixedColor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
