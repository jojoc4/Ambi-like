package ch.hearc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * CommandeLed interface
 *
 * @author jonatan.baumgart
 */
public interface CommandeLed_I extends Remote {

    /**
     * set a led color
     *
     * @param nbLed number of the led on the strip
     * @param r red value beetween 0 and 255
     * @param g green value beetween 0 and 255
     * @param b blue value beetween 0 and 255
     * @throws RemoteException
     */
    public void setLed(int nbLed, int r, int g, int b) throws RemoteException;
}
