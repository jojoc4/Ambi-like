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
    private BorderLayout layout;

    public FrameCreator() {
        geometry();
        control();
        appearance();
        //System.out.println("FrameCreator - width: " + getWidth() + " height: " + getHeight() + " x: " + getX() + " y: " + getY() + " visible: " + isVisible() + " valid: " + isValid());
    }

    private void geometry() {
        panel = new PanelCreator();
        
        add(panel);

    }

    private void control() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void appearance() {
        setTitle("Création de mode personnalisé");
        setSize(1300, 600);
        setResizable(false);
        setLocationRelativeTo(null); // frame centrer
        setVisible(true); // last!
    }
}
