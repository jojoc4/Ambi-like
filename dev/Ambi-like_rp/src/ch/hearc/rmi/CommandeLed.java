package ch.hearc.rmi;

import com.github.mbelling.ws281x.*;
import java.rmi.RemoteException;

/**
 * implementation of CommandeLed_I
 *
 * @author jonatan.baumgart
 * @version 1.5
 * @since 20.05.2019
 */
public class CommandeLed implements CommandeLed_I {

    private Ws281xLedStrip led;
    public long lastSetLed;

    /**
     *
     * @param led led strip we want to command
     */
    public CommandeLed(Ws281xLedStrip led) {
        this.led = led;
        lastSetLed = System.currentTimeMillis();
    }

    /**
     * set a led color
     *
     * @param nbLed number of the led on the strip
     * @param r red value beetween 0 and 255
     * @param g green value beetween 0 and 255
     * @param b blue value beetween 0 and 255
     * @throws RemoteException
     */
    @Override
    public void setLed(int nbLed, int r, int g, int b) throws RemoteException {
        led.setPixel(nbLed, r, g, b);
        lastSetLed = System.currentTimeMillis();
    }

}
