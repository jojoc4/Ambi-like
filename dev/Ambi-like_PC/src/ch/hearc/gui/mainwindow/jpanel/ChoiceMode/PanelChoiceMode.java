/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.ChoiceMode;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelChoiceMode extends JPanel {

    private PanelChoiceModeSettings panelChoiceModeSettings;

    public PanelChoiceMode() {
        geometry();
        control();
        appearance();

    }

    private void geometry() {
        panelChoiceModeSettings = new PanelChoiceModeSettings();

        Box boxv = Box.createVerticalBox();
        boxv.add(panelChoiceModeSettings);
        boxv.add(Box.createHorizontalGlue());

        add(boxv);
    }

    private void control() {

    }

    private void appearance() {
        setBorder(BorderFactory.createTitledBorder("Choix du mode"));
    }

}