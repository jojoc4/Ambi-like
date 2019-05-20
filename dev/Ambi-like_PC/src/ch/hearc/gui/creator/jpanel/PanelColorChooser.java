/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import java.awt.BorderLayout;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;

/**
 *
 * @author teosc
 */
public class PanelColorChooser extends JPanel {

    private JColorChooser jcc;
    //private PanelButtons pb;

    public PanelColorChooser() {
        jcc = new JColorChooser();
        jcc.setPreviewPanel(new JPanel());
        
        AbstractColorChooserPanel[] panels = jcc.getChooserPanels();
        for (AbstractColorChooserPanel accp : panels) {
           if(!accp.getDisplayName().equals("RGB")) {
              jcc.removeChooserPanel(accp);
           } 
        }
        
        //pb = new PanelButtons();

        setLayout(new BorderLayout());
        add(jcc, BorderLayout.CENTER);
        //add(pb, BorderLayout.SOUTH);
    }
}

