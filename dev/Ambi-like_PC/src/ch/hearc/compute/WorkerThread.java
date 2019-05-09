/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.compute;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import ch.hearc.compute.senders.Sender_I;

/**
 *
 * @author teosc
 */
public class WorkerThread implements Runnable {

    //Inputs
    private Boundaries boundaries;
    private BufferedImage img;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    private int index;
    //private Sender sender;

    //Tools
    private int red;
    private int green;
    private int blue;

    private boolean running;
    private Sender_I sender;

    public WorkerThread(Boundaries boundaries, Sender_I sender) {
        this.boundaries = boundaries;
        this.running = false;
        this.sender = sender;
    }

    public synchronized void setImage(BufferedImage img) {
        this.img = img;
    }
    
    private synchronized int getRGB(int x, int y){
        //System.out.println("x: " + x + " y: " + y);
        return (img != null) ? img.getRGB(x, y) : 0; //0 means the LEDs will be switched off when there's no image available.
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
            index = b[4];

            int totalR = 0;
            int totalG = 0;
            int totalB = 0;
            int totalPx = (Math.max(xMax, xMin) - Math.min(xMax, xMin)) * (Math.max(yMax, yMin) - Math.min(yMax, yMin));

            //System.out.println(xMin + " " + yMin + " " + xMax + " " + yMax + " ");
            for (int x = xMin; x <= xMax; ++x) {
                for (int y = yMin; y <= yMax; ++y) {
                    int rgb = this.getRGB(x, y);
                    totalR += (rgb >> 16) & 0xFF;
                    totalG += (rgb >> 8) & 0xFF;
                    totalB += (rgb) & 0xFF;
                }
            }

            if (totalPx == 0) {
                totalPx = 1;
            }
            this.red = totalR / totalPx;
            this.green = totalG / totalPx;
            this.blue = totalB / totalPx;

            //System.out.println("rouge: " + moyR + " vert: " + moyG + " bleu: " + moyB);
            sendValues();

            try {
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void sendValues() {
        //send the values to the raspberry
        //For testing :
        //System.out.println("entre (" + xMin + "; " + yMin + ") et (" + xMax + "; " + yMax + ") : RGB(" + red + "; " + green + "; "+ blue + ")");

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
