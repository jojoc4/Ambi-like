/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.mainwindow.choixmode;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author julien.chappuis1
 */
public class PanelChoixModeSettings extends JPanel {
    public PanelChoixModeSettings(){
        
        geometry();
        control();
        appearance();
    }
    
    private void geometry(){
        reactifRB = new JRadioButton("Réactif (Ambi-light)");
        couleurFixeRB = new JRadioButton("Couleur fixe : ");
        modePersonnalise1 = new JRadioButton("Mode personnalisé 1");
        modePersonnalise2 = new JRadioButton("Mode personnalisé 2");
        modePersonnalise3 = new JRadioButton("Mode personnalisé 3");
        
        panelChoixModeCouleurs = new PanelChoixModeCouleurs();

        group = new ButtonGroup();
        
        
        group.add(reactifRB);
        group.add(couleurFixeRB);
        group.add(modePersonnalise1);
        group.add(modePersonnalise2);
        group.add(modePersonnalise3);
        
        
        Box boxVertical = Box.createVerticalBox();
        boxVertical.add(reactifRB);
        boxVertical.add(panelChoixModeCouleurs);
        boxVertical.add(couleurFixeRB);
        boxVertical.add(modePersonnalise1);
        boxVertical.add(modePersonnalise2);
        boxVertical.add(modePersonnalise3);
        
        setLayout(new BorderLayout());

	add(boxVertical, BorderLayout.CENTER);

    }
    
    private void control(){
        
    }
    
    private void appearance(){
        reactifRB.setSelected(true);
    }
    
    private PanelChoixModeCouleurs panelChoixModeCouleurs;
    
    private ButtonGroup group;
    private JRadioButton reactifRB;
    private JRadioButton couleurFixeRB;
    private JRadioButton modePersonnalise1;
    private JRadioButton modePersonnalise2;
    private JRadioButton modePersonnalise3;
}
