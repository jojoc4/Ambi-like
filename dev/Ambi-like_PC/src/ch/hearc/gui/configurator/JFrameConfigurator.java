/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.configurator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author julien.chappuis1
 */
public class JFrameConfigurator extends JFrame {

    private JPanelConfigurator jPanelConfigurator;

    public JFrameConfigurator() {
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        this.jPanelConfigurator = new JPanelConfigurator();

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        add(this.jPanelConfigurator, BorderLayout.CENTER);
    }

    private void control() {

    }

    private void appearance() {
        setSize(jPanelConfigurator.getPreferredSize());
        setLocationRelativeTo(null); // frame centrer
        setVisible(true); // last!
    }

}
