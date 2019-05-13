/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

/**
 *
 * @author teosc
 */
public class PanelColorChooser extends JPanel{
    
    private JColorChooser jcc;
    private PanelButtons pb;
    
    public PanelColorChooser(){
        jcc = new JColorChooser();
        pb = new PanelButtons();
        
        setLayout(new BorderLayout());
        add(jcc, BorderLayout.CENTER);
        add(pb, BorderLayout.SOUTH);
    }
}

class PanelButtons extends JPanel{
    private JButton btnSave;
    private JButton btnQuit;
    
    public PanelButtons(){
        btnSave = new JButton("Sauvegarder");
        btnQuit = new JButton("Quitter");
        
        setLayout(new BorderLayout());
        add(btnSave, BorderLayout.WEST);
        add(btnQuit, BorderLayout.EAST);
    }
}
