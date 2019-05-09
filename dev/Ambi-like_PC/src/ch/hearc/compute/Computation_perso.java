package ch.hearc.compute;

import ch.hearc.ModePerso;
import ch.hearc.Pixel;
import ch.hearc.compute.senders.Sender_I;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonatan.baumgart
 */
public class Computation_perso extends Computation_I {

    private Sender_I s;
    private ModePerso m;

    public Computation_perso(Sender_I s, ModePerso m) {
        this.s = s;
        this.m = m;
    }

    @Override
    public void run() {
        while (isRunning()) {
            try {
                int i = 0;
                for (Pixel p : m) {
                    s.send(i, p.getRed(), p.getGreen(), p.getBlue());
                }
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Computation_perso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
