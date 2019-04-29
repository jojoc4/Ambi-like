package ch.hearc;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Téo Schaffner
 */
public class Computation implements Runnable {

    private int[] nbLed;
    private int[][][] colors;
    private Boundaries boundaries;
    private WorkerThread[] workers;
    private ExecutorService executor;
    private boolean running;

    public Computation() {
        nbLed = new int[]{20, 50, 20, 0};
        
        colors = new int[4][][];
        colors[0] = new int[nbLed[0]][];
        colors[1] = new int[nbLed[1]][];
        colors[2] = new int[nbLed[2]][];
        colors[3] = new int[nbLed[3]][];
        
        //boundaries (nb total LEDs)
        boundaries = new Boundaries(nbLed[0]+nbLed[1]+nbLed[2]+nbLed[3]);
        
        //runners = new Thread[Runtime.getRuntime().availableProcessors()];
        executor = Executors.newWorkStealingPool();
        workers = new WorkerThread[Runtime.getRuntime().availableProcessors()];
        
        running = false;
        
    }

    @Override
    public void run() {
        startComputation();
        BufferedImage img = printScreen();

        int oldCol = 0;
        int oldLin = 0;
        for (int i = 0; i < 4; ++i) {
            if (nbLed[i] > 0) {
                int dLin = (img.getHeight() - 1) / nbLed[i];
                int dCol = (img.getWidth() - 1) / nbLed[i];
                for (int j = 1; j < colors[i].length; ++j) {
                    int col = 0;
                    int lin = 0;
                    int[] rgb2;

                    switch (i) {
                        case 0:
                            col = 0;
                            oldCol = 50;

                            lin = img.getHeight() - j * dLin;//j * (img.getHeight() - 1) / nbLed[i];
                            break;
                        case 1:
                            col = j * dCol;

                            lin = 0;
                            oldLin = 50;
                            break;
                        case 2:
                            col = img.getWidth() - 1;
                            oldCol = img.getWidth() - 50;

                            lin = j * dLin;
                            break;
                        case 3:
                            col = img.getWidth() - j * dCol;

                            lin = img.getHeight() - 1;
                            oldLin = img.getHeight() - 50;
                    }

                    boundaries.setNext(Math.min(col, oldCol), Math.min(lin, oldLin), Math.max(col, oldCol), Math.max(lin, oldLin));

                    //System.out.println(oldCol + " " + oldLin + " " + col + " " + lin);

                    //int[] b = boundaries.getNext();

                    //rgb2 = getMoyenneBetween(img, b[0], b[1], b[2], b[3]); //img.getRGB(col, lin);
                    //colors[i][j] = rgb2;

                    oldCol = col;
                    oldLin = lin;
                }
                //System.out.println("cote : " + i);
            }
        }
        
        for(int i=0; i<workers.length; ++i)
            {
                workers[i] = new WorkerThread(boundaries);
                executor.execute(workers[i]);
            }
            
        while(isRunning())
        {
            img = printScreen();

            for(int i=0; i<workers.length; ++i)
            {
                workers[i].setImage(img);
            }
            
            
            try {
                Thread.sleep(30); // ~30 FPS
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            }

//            for (int[][] a : colors) {
//                for (int[] b : a) {
//                    System.out.println(Arrays.toString(b));
//                }
//                System.out.println("coté");
//            }
        }
        
        for(int i=0; i<workers.length; ++i)
            {
                workers[i].stopRun();
            }
        executor.shutdownNow();
    }

    private int[] getMoyenneBetween(BufferedImage img, int xMin, int yMin, int xMax, int yMax) {
        int totalR = 0;
        int totalG = 0;
        int totalB = 0;
        long totalPx = 0;
        
        //System.out.println(xMin + " " + yMin + " " + xMax + " " + yMax + " ");

        for (int x = xMin; x <= xMax; ++x) {
            for (int y = yMin; y <= yMax; ++y) {
                int rgb = img.getRGB(x, y);
                totalR += (rgb >> 16) & 0xFF;
                totalG += (rgb >> 8) & 0xFF;
                totalB += (rgb) & 0xFF;
                totalPx++;
            }
        }
        
        int moyR = (int) (totalR / totalPx);
        int moyG = (int) (totalG / totalPx);
        int moyB = (int) (totalB / totalPx);
        
        //System.out.println("rouge: " + moyR + " vert: " + moyG + " bleu: " + moyB);
        return new int[]{moyR, moyG, moyB};
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
            Logger.getLogger(Computation.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public synchronized void startComputation(){
        this.running = true;
    }
    
    public synchronized void stopComputation(){
        this.running = false;
    }
    
    public synchronized boolean isRunning(){
        return this.running;
    }
}
