package ch.hearc.compute;

import ch.hearc.Config;
import ch.hearc.Pixel;
import ch.hearc.compute.senders.Sender_I;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * computation used to set a fixed color to the ledstrip. needs to send color at
 * least once every 2 seconds so the ledstrip don't turn off
 *
 * @author jonatan.baumgart
 */
public class Computation_fixedColor extends Computation_I {

    private final Sender_I sender;
    private final Pixel p;

    /**
     *
     * @param sender used sender
     * @param p pixel witch represent the color applied to the led
     */
    public Computation_fixedColor(Sender_I sender, Pixel p) {
        this.sender = sender;
        this.p = p;
        startComputation();
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

    @Override
    public synchronized BufferedImage getImage() {
        //TODO
        return null;
    }

}
