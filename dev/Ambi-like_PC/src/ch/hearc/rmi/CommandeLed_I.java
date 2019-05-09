package ch.hearc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author jonatan.baumgart
 */
public interface CommandeLed_I extends Remote {

    public void setLed(int nbLed, int r, int g, int b) throws RemoteException;
}
