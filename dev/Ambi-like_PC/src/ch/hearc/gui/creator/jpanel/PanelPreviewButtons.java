/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import ch.hearc.ModePersonnalise;
import ch.hearc.Pixel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FilenameFilter;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author teosc
 */
public class PanelPreviewButtons extends JPanel {

    private PanelPreview preview;
    private PanelButtons buttons;

    public PanelPreviewButtons(PanelColorChooser colorChooser) {

        this.preview = new PanelPreview(colorChooser);
        this.buttons = new PanelButtons(this);

        add(preview, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        System.out.println("PanelPreviewButtons - width: " + getWidth() + " height: " + getHeight() + " x: " + getX() + " y: " + getY() + " visible: " + isVisible() + " valid: " + isValid());

        setSize(preview.getWidth() + buttons.getWidth(), preview.getHeight() + buttons.getHeight());
    }

    public void saveMode(String fileName) {
        ModePersonnalise m = new ModePersonnalise();

        int i = 0;
        for (Pixel p : preview.getVectorPixel()) {
            m.setLed(i++, p);
        }

        m.save(fileName);
    }
    
    public synchronized void useMode(ModePersonnalise m){
        preview.setPixels(m.getPixels(), m.getNbled());
    }
}

class PanelButtons extends JPanel {
    private JButton btnEdit;
    private JButton btnSave;
    private PanelPreviewButtons parent;
    private String fileName;

    public PanelButtons(PanelPreviewButtons parent) {
        btnEdit = new JButton("Editer un mode");
        btnSave = new JButton("Sauvegarder");
        this.parent = parent;
        fileName = null;

        setLayout(new BorderLayout());
//        add(Box.createHorizontalStrut(20), BorderLayout.WEST);
        add(Box.createHorizontalStrut(20), BorderLayout.CENTER);
        add(btnEdit, BorderLayout.WEST);
        add(btnSave, BorderLayout.EAST);
        add(Box.createVerticalStrut(20), BorderLayout.SOUTH);
        add(Box.createVerticalStrut(20), BorderLayout.NORTH);
        
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.awt.FileDialog fd = new java.awt.FileDialog((java.awt.Frame)null, "Choix d'un fichier", java.awt.FileDialog.LOAD);
                fd.setVisible(true);
                //fd.setFilenameFilter​(new FilenameFilter("*.amm"));
                
                fileName = fd.getFile();
                
                if(fileName != null){
                    ModePersonnalise m = ModePersonnalise.getMode(fileName);
                    parent.useMode(m);
                }
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.saveMode(fileName);
            }
        });

        //setSize(500, 80);
        //System.out.println("PanelButtons - width: " + getWidth() + " height: " + getHeight() + " x: " + getX() + " y: " + getY() + " visible: " + isVisible() + " valid: " + isValid());
    }
}
