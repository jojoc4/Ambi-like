/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow;

import ch.hearc.gui.mainwindow.jpanel.PanelMainWindow;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

        addWindowListener(new WindowAdapter() {
            //I skipped unused callbacks for readability

            @Override
            public void windowClosing(WindowEvent e) {
                panelMainWindow.stopComputation();
            }
        });
    }

    private void appearance() {
        setTitle("Demo");
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null); // frame centrer
        setVisible(true); // last!
    }
}
