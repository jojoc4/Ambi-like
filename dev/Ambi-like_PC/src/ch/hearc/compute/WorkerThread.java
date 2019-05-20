/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.compute;

import ch.hearc.Config;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import ch.hearc.compute.senders.Sender_I;
import java.awt.Color;

/**
 *
 * @author teosc
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

    public WorkerThread(Boundaries boundaries, Sender_I sender) {
        this.boundaries = boundaries;
        this.running = false;
        this.sender = sender;
    }

    public synchronized void setImage(BufferedImage img) {
        this.img = img;
    }

    private int getRGB(int x, int y) {
        //System.out.println("x: " + x + " y: " + y);
        try {
            synchronized (this) {
                return (img != null) ? img.getRGB(x, y) : 0;
            } //0 means the LEDs will be switched off when there's no image available.
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e.getClass() + " -- problematic coordinates (x: " + x + "; y: " + y
                    + "). Image size : " + img.getWidth() + " x " + img.getHeight()
                    + " Please contact the devs and tell them this error message.");
        }
        return 0;
    }

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
//            int totalR2 = 0;
//            int totalG2 = 0;
//            int totalB2 = 0;
            int totalPx = 0;

            //System.out.println(xMin + " " + yMin + " " + xMax + " " + yMax + " ");
            for (int x = xMin; x <= xMax; ++x) {
                for (int y = yMin; y <= yMax; ++y) {
                    int rgb = this.getRGB(x, y);
                    //int rgb contains red green blue and alpha values, 8 bits for each. shifts to get correct values for each color
                    totalR += (rgb >> 16) & 0xFF;
                    totalG += (rgb >> 8) & 0xFF;
                    totalB += (rgb) & 0xFF;
//                    Color c = new Color(rgb);
//                    totalR2 += c.getRed();
//                    totalG2 += c.getGreen();
//                    totalB2 += c.getBlue();

                    ++totalPx;
                }
            }

//            System.out.println(totalR + " " + totalG + " " + totalB);
//            System.out.println((int)((((float)totalR / (float)totalPx) / 255f ) * Config.getConfig().getLumMax()));
            totalPx = (totalPx == 0) ? 1 : totalPx; //make sure there is no zero-division if the area to compute is ill-formed.

            this.red = (totalR / totalPx);
            this.green = (totalG / totalPx);
            this.blue = (totalB / totalPx);

//            System.out.println(index + ") rouge: " + red + " vert: " + green + " bleu: " + blue);
//            
//            this.red = (totalR2 / totalPx);
//            this.green = (totalG2 / totalPx);
//            this.blue = (totalB2 / totalPx);
//            
//            System.out.println(index + ") rouge2: " + red + " vert2: " + green + " bleu2: " + blue);
            //send the values to the specified output (chosen in constructor)
            //sendValues();

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void sendValues() {
        //For testing : (can also simply use a TestSender object)
        //System.out.println("entre (" + xMin + "; " + yMin + ") et (" + xMax + "; " + yMax + ") : RGB(" + red + "; " + green + "; "+ blue + ")");

        //send the values to the desired output
        sender.send(index, red, green, blue);
    }

    public synchronized void startRun() {
        this.running = true;
    }

    public synchronized void stopRun() {
        this.running = false;
    }

    public synchronized boolean isRunning() {
        return this.running;
    }

}
