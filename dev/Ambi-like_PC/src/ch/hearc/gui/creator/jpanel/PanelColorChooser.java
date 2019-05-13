/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import java.awt.BorderLayout;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

/**
 *
 * @author teosc
 */
public class PanelColorChooser extends JPanel{
    
    private final JColorChooser jcc;
    
    public PanelColorChooser(){
        jcc = new JColorChooser();
        
        setLayout(new BorderLayout());
        add(jcc, BorderLayout.CENTER);
    }
}
