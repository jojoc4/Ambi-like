package ch.hearc.compute;

import ch.hearc.ModePersonnalise;
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
 * @version 1.1
 * @since 10.05.2019
 * @author Jonatan Baumgartner
 */
public class Computation_perso extends Computation_I {

    private final Sender_I sender;
    private final ModePersonnalise mode;
    private final BufferedImage img;

    /**
     *
     * @param s used sender sender to use
     * @param m Personalized mode to apply
     */
    public Computation_perso(Sender_I s, ModePersonnalise m) {
        this.sender = s;
        this.mode = m;

        this.img = printScreen();

        startComputation();
    }

    /**
     * sends evry pixel of the mode ten times a second
     */
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

    /**
     * used by the previsualisation
     *
     * @return screenshot
     */
    @Override
    public synchronized BufferedImage getImage() {
        return img;
    }
}
