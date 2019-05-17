/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author teosc
 */
public class PanelCreator extends JPanel {

    private static final int MARGE = 40;

    private PanelColorChooser colorChooser;
    PanelPreview preview;
    private BorderLayout layout;

    public PanelCreator() {
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        layout = new BorderLayout();
        setLayout(layout);

        colorChooser = new PanelColorChooser();
        preview = new PanelPreview();

        add(colorChooser, BorderLayout.WEST);
        add(preview, BorderLayout.CENTER);
    }

    private void control() {

    }

    private void appearance() {
        int w = colorChooser.getWidth() + preview.getWidth() + MARGE;
        int h = Math.max(colorChooser.getHeight(), preview.getHeight()) + MARGE;

        System.out.println(w + " " + h);

        Dimension d = new Dimension(w, h);

        setMinimumSize(d);
        setPreferredSize(d);
    }

}
