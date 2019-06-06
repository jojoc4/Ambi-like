/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * Main panel of the mode creator window. Contains the other panels. Used by FrameCreator.
 * 
 * @version 3.0.1
 * @since 06.06.2019
 * @author Téo Schaffner
 */
public class PanelCreator extends JPanel {

    private PanelColorChooser colorChooser;
    private BorderLayout layout;
    private PanelPreviewButtons preview;
    
    /**
     * Constructor
     */
    public PanelCreator() {
        colorChooser = new PanelColorChooser();
        preview = new PanelPreviewButtons(colorChooser);

        layout = new BorderLayout();
        setLayout(layout);
        add(colorChooser, BorderLayout.WEST);
        add(preview, BorderLayout.CENTER);
    }
}
