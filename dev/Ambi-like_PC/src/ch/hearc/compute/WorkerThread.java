package ch.hearc.compute;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import ch.hearc.compute.senders.Sender_I;

/**
 * Calculates the average color of areas around the screen and sends them to the desired output. Only used by Computation_Ambilight. <br>
 * Gets the areas by using Boundaries, so it runs around the screen, in harmony with all the other workers. <br>
 * This class implements Runnable interface.
 * 
 * @version 2.5.3
 * @since 25.05.2019
 * @author Téo Schaffner
 */
public class WorkerThread implements Runnable {

    //Inputs
    private final Boundaries boundaries;
    private BufferedImage img;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private int index;
    private final Sender_I sender;

    //Tools
    private int red;
    private int green;
    private int blue;
    private boolean running;

    /**
     * Constructor
     * 
     * @param boundaries The boundaries object containing all the areas that should be calculated.
     * @param sender The sender that should be used to send the claculated color to the desired output.
     */
    public WorkerThread(Boundaries boundaries, Sender_I sender) {
        this.boundaries = boundaries;
        this.running = false;
        this.sender = sender;
    }
    
    /**
     * Set a new image to use for colors. Thread-safe.
     * 
     * @param img the new image
     */
    public synchronized void setImage(BufferedImage img) {
        this.img = img;
    }
    
    /**
     * Gives the ARGB color of the pixel at index (x,y) in the current image.
     * 
     * @param x x coordinate of the pixel
     * @param y y coordinate of the pixel
     * @return integer containing the ARGB value of the pixel's color
     */
    private int getRGB(int x, int y) {
        //System.out.println("x: " + x + " y: " + y);
        try {
            synchronized (this) {
                return (img != null) ? img.getRGB(x, y) : 0; //0 means the LEDs will be switched off when there's no image available.
            } 
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e.getClass() + " -- problematic coordinates (x: " + x + "; y: " + y
                    + "). Image size : " + img.getWidth() + " x " + img.getHeight()
                    + " Please contact the devs and tell them this error message.");
        }
        return 0;
    }
    
    /**
     * Calculates the average color of the next area as long as the thread is marked as running (see startRun, stopRun and isRunning)
     */
    @Override
    public void run() {
        startRun();

        while (isRunning()) {
            int[] b = boundaries.getNext();

            xMin = b[0];
            yMin = b[1];
            xMax = b[2];
            yMax = b[3];
            index = b[4]; //this is the LED's id

            int totalR = 0;
            int totalG = 0;
            int totalB = 0;
            int totalPx = 0;

            //System.out.println(xMin + " " + yMin + " " + xMax + " " + yMax + " ");
            for (int x = xMin; x <= xMax; ++x) {
                for (int y = yMin; y <= yMax; ++y) {
                    int rgb = this.getRGB(x, y);
                    //int rgb contains red green blue and alpha values, 8 bits for each. shifts to get correct values for each color
                    totalR += (rgb >> 16) & 0xFF;
                    totalG += (rgb >> 8) & 0xFF;
                    totalB += (rgb) & 0xFF;

                    ++totalPx;
                }
            }

            totalPx = (totalPx == 0) ? 1 : totalPx; //make sure there is no zero-division if the area to compute is ill-formed.

            this.red = (totalR / totalPx);
            this.green = (totalG / totalPx);
            this.blue = (totalB / totalPx);

            //send the values to the specified output (chosen in constructor)
            sendValues();
            
            //wait a moment before calculating next area
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * sends the values to the desired output, specified by the sender given in constructor
     */
    private void sendValues() {
        sender.send(index, red, green, blue);
    }
    
    /**
     * enables the thread to run
     */
    public synchronized void startRun() {
        this.running = true;
    }
    
    /**
     * disables the thread to run
     */
    public synchronized void stopRun() {
        this.running = false;
    }
    
    /**
     * Tells if the thread is enabled or not.
     * 
     * @return true if enabled, false otherwise
     */
    public synchronized boolean isRunning() {
        return this.running;
    }

}
