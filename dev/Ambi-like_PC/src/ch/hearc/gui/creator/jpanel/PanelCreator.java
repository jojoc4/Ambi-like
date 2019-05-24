/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author teosc
 */
public class PanelCreator extends JPanel {

    private static final int MARGE = 40;

    private PanelColorChooser colorChooser;
    private BorderLayout layout;
    private PanelPreviewButtons preview;

    public PanelCreator() {
        colorChooser = new PanelColorChooser();
        preview = new PanelPreviewButtons(colorChooser);

        layout = new BorderLayout();
        setLayout(layout);
        add(colorChooser, BorderLayout.WEST);
        add(preview, BorderLayout.CENTER);
        
        //System.out.println("PanelCreator - width: " + getWidth() + " height: " + getHeight() + " x: " + getX() + " y: " + getY() + " visible: " + isVisible() + " valid: " + isValid());
    }

}
