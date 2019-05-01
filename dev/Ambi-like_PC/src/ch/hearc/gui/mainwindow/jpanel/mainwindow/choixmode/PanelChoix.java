/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.mainwindow.choixmode;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelChoix extends JPanel {
    public PanelChoix(){
        geometry();
        control();
        appearance();
        
    }
    
    private void geometry(){
        panelChoixMode = new PanelChoixMode();
        buttonApplyParameter = new JButton("Appliquer le mode sélectionné");
        
        
        
        add(panelChoixMode);
        add(buttonApplyParameter);
    }
    
    private void control(){
        
    }
    private void appearance(){
        
    }
    
    private PanelChoixMode panelChoixMode;
    private JButton buttonApplyParameter;
}
