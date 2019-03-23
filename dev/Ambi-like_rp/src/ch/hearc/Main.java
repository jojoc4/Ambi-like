/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.github.mbelling.ws281x.*;

/**
 *
 * @author jonatan.baumgart
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Ws281xLedStrip led = new Ws281xLedStrip(10, 18, 800000, 10, 255, 0, false, LedStripType.WS2811_STRIP_RGB, false);

        led.setStrip(100, 0, 100);

        led.render();

        try {
            LedServerImp obj = new LedServerImp();
            LedServer stub = (LedServer) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("LedServer", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
