/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.panel.configurator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author julien.chappuis1
 */
public class JFrameConfigurator extends JFrame {

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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void appearance() {
        setSize(jPanelConfigurator.getSize());
        setLocationRelativeTo(null); // frame centrer
        setVisible(false); // last!
    }
    
    private JPanelConfigurator jPanelConfigurator;
}
