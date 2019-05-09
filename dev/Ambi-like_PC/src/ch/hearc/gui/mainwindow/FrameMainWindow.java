/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow;

import ch.hearc.gui.mainwindow.jpanel.PanelMainWindow;
import javax.swing.JFrame;

/**
 *
 * @author julien.chappuis1
 */
public class FrameMainWindow extends JFrame {

    private PanelMainWindow panelMainWindow;

    public FrameMainWindow() {

        geometry();
        control();
        appearance();

    }

    private void geometry() {
        panelMainWindow = new PanelMainWindow();
        add(panelMainWindow);
    }

    private void control() {
            
    }

    private void appearance() {
        setTitle("Demo");
        setSize(1000, 600);
        setLocationRelativeTo(null); // frame centrer
        setVisible(true); // last!
    }
}
