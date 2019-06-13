package ch.hearc.compute;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstract class that defines the Computation. Implements Runnable interface.
 *
 * @version 2.0
 * @since 22.04.2019
 * @author Téo Schaffner
 */
public abstract class Computation_I implements Runnable {

    public final static int MODE_AMBILIGHT = 1;
    public final static int MODE_FIXE = 2;
    public final static int MODE_PERSO = 3;

    private boolean running;

    @Override
    public abstract void run();

    /**
     * Enables the computation. Must start the thread after calling this if you
     * want the computation to actually do something.
     */
    public void startComputation() {
        this.running = true;
    }

    /**
     * Stops the computation in a clean way. The thread will finish it's job and
     * exit.
     */
    public void stopComputation() {
        this.running = false;
    }

    /**
     * Tells if the computation is currently enabled.
     *
     * @return true if the computation is enabled, false otherwise.
     */
    public boolean isRunning() {
        return this.running;
    }

    /**
     * Takes a screenshot and returns it.
     *
     * @return BufferedImage containing the screenshot, or null if an exception
     * is encountered.
     */
    public static final BufferedImage printScreen() {
        try {
            Robot r = new Robot();
            return r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException ex) {
            Logger.getLogger(Computation_Ambilight.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public abstract BufferedImage getImage();
}
