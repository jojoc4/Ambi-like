/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.ChoiceMode;

import ch.hearc.Config;
import ch.hearc.Main;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelChoice extends JPanel {

    private PanelChoiceMode panelChoiceMode;
    private JButton buttonApplyParameter;
    
    
    private Config config;

    public PanelChoice() {
        geometry();
        control();
        appearance();

    }

    private void geometry() {
        panelChoiceMode = new PanelChoiceMode();
        buttonApplyParameter = new JButton("Appliquer le mode sélectionné");
        
        config = Config.getConfig();
        
        BorderLayout layout = new BorderLayout();
        Box boxV = Box.createVerticalBox();
        boxV.add(panelChoiceMode);
        boxV.add(buttonApplyParameter);
        boxV.add(Box.createVerticalGlue());
        setLayout(layout);
        add(boxV, BorderLayout.CENTER);
    }

    private void control() {
        buttonApplyParameter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (config.getMode() != Main.getTempMode()) {
                    config.setMode(Main.getTempMode());
                    Main.changeMode();
                }

            }
        });
    }

    private void appearance() {

    }

}
