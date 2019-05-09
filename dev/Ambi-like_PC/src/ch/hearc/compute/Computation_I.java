/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.compute;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Téo
 */
public abstract class Computation_I implements Runnable {

    public final static String MODE_AMBILIGHT = "ambilight";
    public final static String MODE_FIXE = "fixe";
    public final static String MODE_PERSO = "perso";

    private boolean running;

    @Override
    public abstract void run();

    public void startComputation() {
        this.running = true;
    }

    public void stopComputation() {
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }
}
