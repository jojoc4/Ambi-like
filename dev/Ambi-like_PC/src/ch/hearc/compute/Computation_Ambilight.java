package ch.hearc.compute;

import ch.hearc.Config;
import ch.hearc.compute.senders.PrevisualisationSender;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import ch.hearc.compute.senders.Sender_I;

/**
 *
 * @author Téo Schaffner
 */
public class Computation_Ambilight extends Computation_I {

    private final int[] nbLed;
    private final int[][][] colors;
    private final Boundaries boundaries;
    private BufferedImage img;
    private final WorkerThread[] workers;
    private final ExecutorService executor;
    private final Sender_I sender;

    public Computation_Ambilight(Sender_I sender) {
        this.sender = sender;

        nbLed = Config.getConfig().getNbLed();

        colors = new int[4][][];
        colors[0] = new int[nbLed[0]][];
        colors[1] = new int[nbLed[1]][];
        colors[2] = new int[nbLed[2]][];
        colors[3] = new int[nbLed[3]][];
        
        img = null;
        
        //boundaries (nb total LEDs)
        boundaries = new Boundaries(nbLed[0] + nbLed[1] + nbLed[2] + nbLed[3]);
        
        //Use 1 single thread if the work will be done for previsualisation on the GUI, hammer the CPU otherwise.
        executor = (sender instanceof PrevisualisationSender) ? Executors.newSingleThreadExecutor() : Executors.newWorkStealingPool();
        int nbThreads = (sender instanceof PrevisualisationSender) ? 1 : Runtime.getRuntime().availableProcessors();
        workers = new WorkerThread[nbThreads];

        super.stopComputation(); //set running to false. Must only be started when run() has been called.
    }

    @Override
    public void run() {
        buildBoundaries();
        
        //This part is only useful when testing the boundaries
//        boundaries.printAll();
//        try {
//            Thread.sleep(10000000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Computation_Ambilight.class.getName()).log(Level.SEVERE, null, ex);
//        }

        //Create the threads and start them.
        img = printScreen();
        for (int i = 0; i < workers.length; ++i) {
            workers[i] = new WorkerThread(boundaries, sender);
            workers[i].setImage(img);
            executor.execute(workers[i]);
        }
        
        //Set the flag "running" to true. Needed for the loop to do anything.
        startComputation();
        
        while (isRunning()) {
            img.setData(printScreen().getData());
            //boundaries.allowNextLoop();

            //Give new image to all the threads
//            for (WorkerThread t : workers) {
//                t.setImage(img);
//            }

            try {
                Thread.sleep(50); // ~25 FPS pour le refresh de l'image utilisée
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //not running anymore, so tell the workers to finish what they are doing (finish properly, cleaner than just killing them)
        for (WorkerThread t : workers) {
            //And tell them to stop their job
            t.stopRun();
        }
        //wait for them to finish and it's over
        executor.shutdown();
        
        //delete the threads, because they are not needed anymore. Always free the memory as soon as possible!
        for(WorkerThread t : workers){
            t = null;
        }
    }

    private void buildBoundaries() {
        BufferedImage img = printScreen();
        
        //col and oldCol = X axis (columns of pixels)
        //lin and oldLin = Y axis (lines of pixels)
        int oldCol = 0;
        int oldLin = img.getHeight()-1;
        
        //loop all sides of the screen (0 = left, counter-clockwise from bottom-left corner)
        for (int i = 0; i < 4; ++i) { 
            //not necessary to do any job if there is no LED on this side of the screen...
            if (nbLed[i] > 0) { 
                //number of pixels for each LED on lines and columns (used as delta)
                int dLin = (img.getHeight()) / nbLed[i];
                int dCol = (img.getWidth()) / nbLed[i];
                
                //loop all LEDs on the current side of the screen
                for (int j = 0; j < nbLed[i]; ++j) {
                    int col = 0;
                    int lin = 0;

                    switch (i) {
                        //LEFT
                        case 0:
                            col = 0;
                            oldCol = 50; //fixed width : pixels on columns 0 to 50

                            //calculate the height of the area based on the iteration number
                            //the other limit is the previous one, stored in oldLin, no need to set it here.
                            lin = img.getHeight() - (j + 1) * dLin;
                            break;
                        //TOP
                        case 1:
                            //calculate the width of the area based on the iteration number
                            //the other limit is the previous one, stored in oldCol, no need to set it here.
                            col = (j + 1) * dCol;

                            lin = 0;
                            oldLin = 50;
                            break;
                        //RIGHT
                        case 2:
                            col = img.getWidth()-1 ;
                            oldCol = img.getWidth() - 50;

                            lin = (j + 1) * dLin;
                            break;
                        //BOTTOM
                        case 3:
                            col = img.getWidth() - (j + 1) * dCol;

                            lin = img.getHeight()-1;
                            oldLin = img.getHeight() - 50;
                    }
                    
                    //make sure there is no indexOutOfBounds. Like, never.
                    col = (col == img.getWidth()) ? col-1 : col;
                    oldCol = (oldCol == img.getWidth()) ? oldCol-1 : oldCol;
                    lin = (lin == img.getHeight()) ? lin-1 : lin;
                    oldLin = (oldLin == img.getHeight()) ? oldLin-1 : oldLin;
                    
                    //store the calculated coordinates as (xMin, yMin, xMax, yMax)
                    boundaries.setNext(Math.min(col, oldCol), Math.min(lin, oldLin), Math.max(col, oldCol), Math.max(lin, oldLin));

                    //keep value for next iteration
                    oldCol = col;
                    oldLin = lin;
                }
            }
        }
    }

    /**
     * @return a BufferedImage containing the screenshot, or null if an
     * exception is encountered
     */
    private BufferedImage printScreen() {
        try {
            Robot r = new Robot();
            return r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException ex) {
            Logger.getLogger(Computation_Ambilight.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public synchronized BufferedImage getImage() {
        return this.img;
    }
}
