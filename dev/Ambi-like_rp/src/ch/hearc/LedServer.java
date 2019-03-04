package ch.hearc;

import java.awt.Color;

/**
 *
 * @author jonatan.baumgart
 */
public interface LedServer extends java.rmi.Remote{
    public void setColor(int number, Color c) throws java.rmi.RemoteException;
}
