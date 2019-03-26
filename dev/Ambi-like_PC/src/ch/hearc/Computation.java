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
 * @author Téo Schaffner
 */
public class Computation implements Runnable {

    private int[] nbLed;
    private int[][] colors;

    public Computation() {
        nbLed = new int[]{20, 50, 20, 0};
        colors = new int[4][];
        colors[0] = new int[nbLed[0]];
        colors[1] = new int[nbLed[1]];
        colors[2] = new int[nbLed[2]];
        colors[3] = new int[nbLed[3]];
    }

    @Override
    public void run() {
        BufferedImage img = printScreen();
        int rgb;

        for(int i=0; i<4; ++i)
        {
            if(nbLed[i] > 0)
            {
                for(int j=0; j<colors[i].length; ++j)
                {
                    int col = 0;
                    int lin = 0;
                    switch(i)
                    {
                        case 0:
                            lin = j*(img.getHeight()-1)/nbLed[i];
                            break;
                        case 1:
                            col = j*(img.getWidth()-1)/nbLed[i];
                            break;
                        case 2:
                            col = img.getWidth()-1;
                            lin = j*(img.getHeight()-1)/nbLed[i];
                            break;
                        case 3:
                            col = j*(img.getWidth()-1)/nbLed[i];
                            lin = img.getHeight()-1;
                    }

                    rgb = img.getRGB(col, lin);
                    colors[i][j] = rgb;
                }
            }
        }
        
        for (int[] a : colors) {
            for (int b : a) {
                int red = (b >> 16) & 0xFF;
                int green = (b >> 8) & 0xFF;
                int blue = (b) & 0xFF;
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
            Robot r = new Robot();
            return r.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException ex) {
            Logger.getLogger(Computation.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
