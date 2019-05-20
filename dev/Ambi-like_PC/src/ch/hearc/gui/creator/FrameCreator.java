/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator;

import ch.hearc.gui.creator.jpanel.PanelCreator;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author teosc
 */
public class FrameCreator extends JFrame {

    private PanelCreator panel;

    public FrameCreator() {
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        setLayout(new BorderLayout());
        panel = new PanelCreator();
        add(panel, BorderLayout.CENTER);
    }

    private void control() {

    }

    private void appearance() {
        setTitle("Création de mode personnalisé");
        setSize(1300, 500);
        setResizable(false);
        setLocationRelativeTo(null); // frame centrer
        setVisible(true); // last!
    }
}
