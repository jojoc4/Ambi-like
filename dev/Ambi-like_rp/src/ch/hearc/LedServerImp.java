
package ch.hearc;

import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author jonatan.baumgart
 */
public class LedServerImp extends UnicastRemoteObject implements LedServer{

    @Override
    public void setColor(int number, Color c) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public LedServerImp() throws RemoteException {
        
    }
    
    private static final long serialVersionUID = 2674880711467464646L;
    
    
}
