package ch.hearc.compute;

import ch.hearc.Config;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import ch.hearc.compute.senders.Sender_I;

/**
 *
 * @author Téo Schaffner
 */
public class Computation extends Computation_I {    
    private int[] nbLed;
    private int[][][] colors;
    private Boundaries boundaries;
    private WorkerThread[] workers;
    private ExecutorService executor;
    private Sender_I sender;

    public Computation(Sender_I sender) {
        this.sender = sender;
        
        nbLed = Config.getConfig().getNbLed();
        
        colors = new int[4][][];
        colors[0] = new int[nbLed[0]][];
        colors[1] = new int[nbLed[1]][];
        colors[2] = new int[nbLed[2]][];
        colors[3] = new int[nbLed[3]][];
        
        //boundaries (nb total LEDs)
        boundaries = new Boundaries(nbLed[0]+nbLed[1]+nbLed[2]+nbLed[3]);
        
        executor = Executors.newWorkStealingPool();
        workers = new WorkerThread[Runtime.getRuntime().availableProcessors()];
        
        stopComputation(); //set running to false
    }

    @Override
    public void run() {
        startComputation();
        buildBoundaries();
        
        boundaries.printAll();
        
        try {
            Thread.sleep(10000000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Computation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Create the threads and start them.
        for(int i=0; i<workers.length; ++i)
            {
                workers[i] = new WorkerThread(boundaries, sender); //Executors.defaultThreadFactory().newThread( ... );
                executor.execute(workers[i]);
            }
            
        while(isRunning())
        {
            BufferedImage img = printScreen();
            
            //Give new image to all the threads
            for(int i=0; i<workers.length; ++i)
            {
                workers[i].setImage(img);
            }
            
            
            try {
                Thread.sleep(40); // ~25 FPS pour le refresh de l'image utilisée
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //not running anymore, so tell the workers to finish what they are doing
        for(int i=0; i<workers.length; ++i)
            {
                //Give them a null image, so LEDs will receive (0,0,0) as RGB color
                workers[i].setImage(null);
                //And tell them to stop their job
                workers[i].stopRun();
            }
        //wait for them to finish and it's over
        executor.shutdown();
    }

    private void buildBoundaries() {
        BufferedImage img = printScreen();
        
        int oldCol = 0;
        int oldLin = 0;
        for (int i = 0; i < 4; ++i) {
            if (nbLed[i] > 0) {
                int dLin = (img.getHeight() ) / nbLed[i];
                int dCol = (img.getWidth() ) / nbLed[i];
                for (int j = 1; j < colors[i].length; ++j) {
                    int col = 0;
                    int lin = 0;

                    switch (i) {
                        //LEFT
                        case 0:
                            col = 0;
                            oldCol = 50;

                            lin = img.getHeight() - j * dLin;//j * (img.getHeight() - 1) / nbLed[i];
                            break;
                        //TOP
                        case 1:
                            col = j * dCol;

                            lin = 0;
                            oldLin = 50;
                            break;
                        //RIGHT
                        case 2:
                            col = img.getWidth() ;
                            oldCol = img.getWidth() - 50;

                            lin = j * dLin;
                            break;
                        //BOTTOM
                        case 3:
                            col = img.getWidth() - j * dCol;

                            lin = img.getHeight() - 1;
                            oldLin = img.getHeight() - 50;
                    }
                    
                    boundaries.setNext(Math.min(col, oldCol), Math.min(lin, oldLin), Math.max(col, oldCol), Math.max(lin, oldLin));

                    //System.out.println(oldCol + " " + oldLin + " " + col + " " + lin);

                    oldCol = col;
                    oldLin = lin;
                }
                //System.out.println("cote : " + i);
            }
        }
    }
    
    /**
     * @return a BufferedImage containing the screenshot, or null if an exception is encountered
     */
    private BufferedImage printScreen() {
        try {
            Robot r = new Robot();
            return r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException ex) {
            Logger.getLogger(Computation.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
}
