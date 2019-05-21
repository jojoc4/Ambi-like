package ch.hearc.compute;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    /**
     * @return a BufferedImage containing the screenshot, or null if an
     * exception is encountered
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
