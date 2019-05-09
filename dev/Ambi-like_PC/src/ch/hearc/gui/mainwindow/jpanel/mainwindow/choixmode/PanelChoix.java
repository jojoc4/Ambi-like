/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.mainwindow.choixmode;

import ch.hearc.Config;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelChoix extends JPanel {
    
    private PanelChoixMode panelChoixMode;
    private JButton buttonApplyParameter;
    
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
        buttonApplyParameter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Config config = Config.getConfig();
                JOptionPane.showMessageDialog(null, "" + config.getColor().toString() + config.getNbLed() + config.getLumMax() + config.getRaspIp(), "InfoBox: ", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    private void appearance(){
        
    }
    

}
