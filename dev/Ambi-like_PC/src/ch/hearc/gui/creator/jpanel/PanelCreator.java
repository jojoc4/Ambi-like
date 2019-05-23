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
    //private PanelPreview preview;
    private BorderLayout layout;
    private PanelPreviewButtons preview;

    public PanelCreator() {
        geometry();
        control();
        appearance();
        //System.out.println("PanelCreator - width: " + getWidth() + " height: " + getHeight() + " x: " + getX() + " y: " + getY() + " visible: " + isVisible() + " valid: " + isValid());
    }

    private void geometry() {
        colorChooser = new PanelColorChooser();
        preview = new PanelPreviewButtons(colorChooser);
        //buttons = new PanelButtons(this);

        layout = new BorderLayout();
        setLayout(layout);
        add(colorChooser, BorderLayout.WEST);
        add(preview, BorderLayout.CENTER);
        //add(buttons, BorderLayout.SOUTH);

        //System.out.println("bite " + layout.getLayoutComponent(BorderLayout.CENTER));
    }

    private void control() {

    }

    private void appearance() {
//        int w = colorChooser.getWidth() + preview.getWidth() + MARGE;
//        int h = Math.max(colorChooser.getHeight(), preview.getHeight()) + MARGE;
//
//        Dimension d = new Dimension(w, h);
//
//        setMinimumSize(d);
//        setPreferredSize(d);
    }

}
