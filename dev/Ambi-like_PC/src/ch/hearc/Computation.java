package ch.hearc;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.Arrays;
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
        
        
//        int red =   (rgb >> 16) & 0xFF;
//        int green = (rgb >>  8) & 0xFF;
//        int blue =  (rgb      ) & 0xFF;
        //System.out.println("rouge: " + red + " vert: " + green + " bleu: " + blue);
        
        for(int i=0; i<nbLedLeft-1; ++i)
        {
                int rgb = img.getRGB(0, i*img.getHeight()/nbLedLeft);
                colors[0][i] = rgb;
        }
        for(int i=0; i<nbLedTop-1; ++i)
        {
            int rgb = img.getRGB(i*img.getWidth()/nbLedTop, 0);
            colors[1][i] = rgb;
        }
        for(int i=0; i<nbLedRight-1; ++i)
        {
            int rgb = img.getRGB(0, i*img.getHeight()/nbLedRight);
            colors[2][i] = rgb;
        }
        for(int i=0; i<nbLedBottom-1; ++i)
        {
            int rgb = img.getRGB(i*img.getWidth()/nbLedBottom, 0);
            colors[3][i] = rgb;
        }
        
        for(int[] a : colors)
        {
            for(int b : a)
            {
                int red =   (b >> 16) & 0xFF;
                int green = (b >>  8) & 0xFF;
                int blue =  (b      ) & 0xFF;
                System.out.println("rouge: " + red + " vert: " + green + " bleu: " + blue);
            }
            System.out.println("coté");
        }
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
