/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.compute.senders;

import ch.hearc.gui.mainwindow.jpanel.previsualisation.PanelPrevisualisationEcran;

/**
 *
 * @author teosc
 */
public class PrevisualisationSender implements Sender{
    
    private PanelPrevisualisationEcran ppe;
    
    public PrevisualisationSender(PanelPrevisualisationEcran ppe){
        this.ppe = ppe;
    }

    @Override
    public void send(int nbLed, int r, int g, int b) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}