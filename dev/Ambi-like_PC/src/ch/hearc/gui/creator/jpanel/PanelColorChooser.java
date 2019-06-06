/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 * Panel that contains a JColorChooser. Extends JPanel. <br>
 * Used by PanelCreator.
 * 
 * @version 1.4
 * @since 28.05.2019
 * @author Téo Schaffner
 */
public class PanelColorChooser extends JPanel {

    private JColorChooser jcc;
    
    /**
     * Constructor
     */
    public PanelColorChooser() {
        jcc = new JColorChooser();
        jcc.setPreviewPanel(new JPanel());

        AbstractColorChooserPanel[] panels = jcc.getChooserPanels();
        for (AbstractColorChooserPanel accp : panels) {
            if (!accp.getDisplayName().equals("RGB")) {
                jcc.removeChooserPanel(accp);
            }
        }

        setLayout(new BorderLayout());
        add(jcc, BorderLayout.CENTER);
    }
    
    /**
     * Gives the selected color
     * @return selected color
     */
    public Color getColor() {
        return jcc.getColor();
    }
}
