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
    
    private BufferedImage img;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
    
    public WorkerThread(BufferedImage img, int xMin, int yMin, int xMax, int yMax)
    {
        this.img = img;
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
    }
    
    @Override
    public void run() {
        
    }
    
}
