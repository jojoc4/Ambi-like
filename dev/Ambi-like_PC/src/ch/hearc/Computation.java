package ch.hearc;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author téo schaffner
 */
public class Computation implements Runnable {
    
    private int nbLedLeft;
    private int nbLedTop;
    private int nbLedRight;
    private int nbLedBottom;
    private int[][] colors;
    
    public Computation()
    {
        nbLedLeft = 20;
        nbLedTop = 50;
        nbLedRight = 20;
        nbLedBottom = 0;
        colors = new int[4][];
        colors[0] = new int[nbLedLeft];
        colors[1] = new int[nbLedTop];
        colors[2] = new int[nbLedRight];
        colors[3] = new int[nbLedBottom];
    }
    
    @Override
    public void run() {
        BufferedImage img = printScreen();
        
        int rgb = img.getRGB(0, 0);
        int red =   (rgb >> 16) & 0xFF;
        int green = (rgb >>  8) & 0xFF;
        int blue =  (rgb      ) & 0xFF;
        
        System.out.println("rouge: " + red + " vert: " + green + " bleu: " + blue);
    }

    /**
     *
     * @return a BufferedImage containing the screenshot, or null if an
     * exception is encountered
     */
    private BufferedImage printScreen() {
        try {
            System.out.println("ch.hearc.Computation.printScreen()");
            Robot r = new Robot();
            return r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException ex) {
            Logger.getLogger(Computation.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
