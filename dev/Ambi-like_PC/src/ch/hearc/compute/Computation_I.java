package ch.hearc.compute;

import java.awt.image.BufferedImage;

/**
 *
 * @author Téo
 */
public abstract class Computation_I implements Runnable {

    public final static String MODE_AMBILIGHT = "ambilight";
    public final static String MODE_FIXE = "fixe";
    public final static String MODE_PERSO = "perso";

    private boolean running;

    @Override
    public abstract void run();

    public void startComputation() {
        this.running = true;
    }

    public void stopComputation() {
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }
    
    public abstract BufferedImage getImage();
}
