/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.compute.senders;

import ch.hearc.Pixel;
import ch.hearc.gui.mainwindow.jpanel.previsualisation.PanelPrevisualisationEcran;
import java.util.Vector;

/**
 *
 * @author teosc
 */
public class PrevisualisationSender implements Sender_I {

    private PanelPrevisualisationEcran ppe;

    public PrevisualisationSender(PanelPrevisualisationEcran ppe) {
        this.ppe = ppe;
    }

    @Override
    public void send(int nbLed, int r, int g, int b) {
        Pixel test = (Pixel) ppe.getVectorPixel().elementAt(nbLed);
        test.setRed(r);
        test.setGreen(g);
        test.setBlue(b);
    }
}
