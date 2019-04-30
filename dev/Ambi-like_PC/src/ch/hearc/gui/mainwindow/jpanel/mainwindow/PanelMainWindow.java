/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.mainwindow;

import ch.hearc.gui.mainwindow.jpanel.mainwindow.previsualisation.PanelPrevisualisationDecorator;
import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelMainWindow extends JPanel {
    
    public PanelMainWindow()
    {
        
        geometry();
        control();
        appearance();
    }
    
    private void geometry()
    {
        panelChoix = new PanelChoix();
        panelPrevisualisation = new PanelPrevisualisationDecorator();        
        
        GridLayout layout = new GridLayout(1,2);
        setLayout(layout);
        add(panelChoix);
        add(panelPrevisualisation);
    }
    
    
    private void control()
    {
        
    }
    
    
    private void appearance()
    {
        
    }
    
    private PanelChoix panelChoix;
    private PanelPrevisualisationDecorator panelPrevisualisation;
}
