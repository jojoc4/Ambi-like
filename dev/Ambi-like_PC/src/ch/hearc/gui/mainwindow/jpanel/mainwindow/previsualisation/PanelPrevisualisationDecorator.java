/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.mainwindow.previsualisation;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelPrevisualisationDecorator extends JPanel {
    public PanelPrevisualisationDecorator(){
        geometry();
        control();
        appearance();
        
    }
    
    private void geometry(){
        panelPrevisualisation = new PanelPrevisualisation();
        
        add(panelPrevisualisation);
    }
    
    private void control(){
        
    }
    private void appearance(){
        //setBorder(BorderFactory.createTitledBorder("Prévisualisation"));
    }
    
   private PanelPrevisualisation panelPrevisualisation;
    
}