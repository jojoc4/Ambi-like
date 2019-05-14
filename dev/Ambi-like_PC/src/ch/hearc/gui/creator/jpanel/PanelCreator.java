/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author teosc
 */
public class PanelCreator extends JPanel {
    private static final int MARGE = 40;
    
    private PanelColorChooser colorChooser;
    PanelVisualisation visualisation;
    private BorderLayout layout;

    public PanelCreator() {
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        layout = new BorderLayout();
        setLayout(layout);
        
        colorChooser = new PanelColorChooser();
        visualisation = new PanelVisualisation();
        
        add(colorChooser, BorderLayout.WEST);
        add(visualisation, BorderLayout.CENTER);
    }
    
    private void control() {
        
    }

    private void appearance() {
        int w = colorChooser.getWidth() + visualisation.getWidth() + MARGE;
        int h = Math.max(colorChooser.getHeight(), visualisation.getHeight()) + MARGE;
        
        System.out.println(w + " " + h);
        
        Dimension d = new Dimension(w, h);
        
        setMinimumSize(d);
        setPreferredSize(d);
    }

}
