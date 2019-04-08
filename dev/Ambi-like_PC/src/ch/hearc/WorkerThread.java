/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc;

import java.awt.image.BufferedImage;

/**
 *
 * @author teosc
 */
public class WorkerThread implements Runnable{
    
    //Inputs
    private Boundaries boundaries;
    private BufferedImage img;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    
    //Tools
    private int red;
    private int green;
    private int blue;
    
    public WorkerThread(Boundaries boundaries, BufferedImage img)
    {
        this.boundaries = boundaries;
        this.img = img;
    }
    
    @Override
    public void run() {
        while(true)
        {
            int[] b = boundaries.getNext();
            
            xMin = b[0];
            yMin = b[1];
            xMax = b[2];
            yMax = b[3];
            
            int totalR = 0;
            int totalG = 0;
            int totalB = 0;
            int totalPx = (xMax - xMin) * (yMax - yMin);

            //System.out.println(xMin + " " + yMin + " " + xMax + " " + yMax + " ");
            for (int x = xMin; x <= xMax; ++x) {
                for (int y = yMin; y <= yMax; ++y) {
                    int rgb = img.getRGB(x, y);
                    totalR += (rgb >> 16) & 0xFF;
                    totalG += (rgb >> 8) & 0xFF;
                    totalB += (rgb) & 0xFF;
                }
            }

            this.red = totalR / totalPx;
            this.green = totalG / totalPx;
            this.blue = totalB / totalPx;

            //System.out.println("rouge: " + moyR + " vert: " + moyG + " bleu: " + moyB);
            sendValues();
        }
    }
    
    private void sendValues(){
        //send the values to the raspberry
    }
    
}
