package ch.hearc.compute;

import java.awt.image.BufferedImage;

/**
 *
 * @author Téo
 */
public abstract class Computation_I implements Runnable {

    public final static int MODE_AMBILIGHT = 1;
    public final static int MODE_FIXE = 2;
    public final static int MODE_PERSO = 3;
    
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
