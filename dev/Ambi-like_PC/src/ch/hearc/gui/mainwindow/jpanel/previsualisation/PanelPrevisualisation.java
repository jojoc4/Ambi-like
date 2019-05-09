/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.previsualisation;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelPrevisualisation extends JPanel {
    
    private PanelPrevisualisationEcran panelPrevisualisationEcran;
    private PanelPrevisualisationBouton panelPrevisualisationBouton;
   
    public PanelPrevisualisation(){
        geometry();
        control();
        appearance();
        
    }
    
    private void geometry(){
        panelPrevisualisationEcran = new PanelPrevisualisationEcran();
        panelPrevisualisationBouton = new PanelPrevisualisationBouton();
        
        Box vLayout = Box.createVerticalBox();
        setLayout(new BorderLayout());
        
        vLayout.add(panelPrevisualisationEcran);
        vLayout.add(panelPrevisualisationBouton);
        add(vLayout,BorderLayout.CENTER);
        
    }
    
    private void control(){
        
    }
    private void appearance(){
        setBorder(BorderFactory.createTitledBorder("Prévisualisation"));
    }
    

    
}