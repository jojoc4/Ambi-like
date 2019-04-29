/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.mainwindow.previsualisation;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelPrevisualisationBouton extends JPanel {
    public PanelPrevisualisationBouton(){
        geometry();
        control();
        appearance();
        
    }
    
    private void geometry(){
        labelVisuLED = new JLabel("Visualisation LED : ");
        checkBoxVisuLED = new JCheckBox();
        
        String message1 = "<html>" + "Créer un mode" + "<br>" + "de visualisation LED"
        + "</html>";
        
        String message2 = "<html>" + "Paramètres de" + "<br>" + "l'application"
        + "</html>";
        
        
        buttonCreerModeVisualisation = new JButton(message1);
        buttonParametres = new JButton(message2);
        
        
        
        Box hBox = Box.createHorizontalBox();
        hBox.add(labelVisuLED);
        hBox.add(checkBoxVisuLED);
        hBox.add(Box.createHorizontalGlue());
        
        hBox.add(buttonCreerModeVisualisation);
        hBox.add(buttonParametres);
        
        add(hBox);
        
        

    }
    
    private void control(){
        
    }
    private void appearance(){
        
    }
    
    private JButton buttonCreerModeVisualisation;
    private JLabel labelVisuLED;
    private JCheckBox checkBoxVisuLED;
    private JButton buttonParametres;
    
}