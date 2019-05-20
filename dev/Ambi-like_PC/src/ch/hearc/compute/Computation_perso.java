package ch.hearc.compute;

import ch.hearc.PrivateMode;
import ch.hearc.Pixel;
import ch.hearc.compute.senders.Sender_I;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * computation used to set a personalized mode to the ledstrip. needs to send
 * something at least once every 2 seconds so the ledstrip don't turn off
 *
 * can latter be used to send animated personalized mode to ledstrip
 *
 * @author jonatan.baumgart
 */
public class Computation_perso extends Computation_I {

    private final Sender_I sender;
    private final PrivateMode mode;
    private final BufferedImage img;

    /**
     *
     * @param s used sender
     * @param m Personalized mode to apply
     */
    public Computation_perso(Sender_I s, PrivateMode m) {
        this.sender = s;
        this.mode = m;
        
        this.img = printScreen();
        
        startComputation();
    }

    @Override
    public void run() {
        while (isRunning()) {
            try {
                int i = 0;
                for (Pixel p : mode) {
                    sender.send(i++, p.getRed(), p.getGreen(), p.getBlue());
                }
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Computation_perso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public synchronized BufferedImage getImage() {
        return img;
    }
}
