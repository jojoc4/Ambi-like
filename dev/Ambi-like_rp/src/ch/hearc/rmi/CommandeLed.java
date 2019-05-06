/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.rmi;

import com.github.mbelling.ws281x.*;
import java.rmi.RemoteException;

/**
 *
 * @author jonatan.baumgart
 */
public class CommandeLed implements CommandeLed_I {

    private Ws281xLedStrip led;
    public long lastSetLed;

    public CommandeLed(Ws281xLedStrip led) {
        this.led = led;
        lastSetLed = System.currentTimeMillis();
    }

    @Override
    public void setLed(int nbLed, int r, int g, int b) throws RemoteException {
        led.setPixel(nbLed, r, g, b);
        led.render();
        lastSetLed = System.currentTimeMillis();
    }

    public void check() {
        if (System.currentTimeMillis() - lastSetLed > 2000) {
            led.setStrip(0, 0, 0);
            led.render();
        }
    }

}
