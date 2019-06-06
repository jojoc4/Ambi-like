/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import ch.hearc.Config;
import ch.hearc.ModePersonnalise;
import ch.hearc.Pixel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Panel containing the preview of currently edited mode and the buttons to save it or load a new one. <br>
 * Used by PanelCreator.
 * 
 * @version 3.0
 * @since 30.05.2019
 * @author Téo Schaffner
 */
public class PanelPreviewButtons extends JPanel {

    private PanelPreview preview;
    private PanelButtons buttons;
    
    /**
     * Constructor
     * 
     * @param colorChooser PanelColorChooser from which the color should be taken.
     */
    public PanelPreviewButtons(PanelColorChooser colorChooser) {

        this.preview = new PanelPreview(colorChooser);
        this.buttons = new PanelButtons(this);

        add(preview, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
    
    /**
     * Save the mode to a file. Thread-safe.
     * 
     * @param fileName complete path and name of the file
     * @param modeName name of the mode
     */
    public synchronized void saveMode(String fileName, String modeName) {
        ModePersonnalise m = new ModePersonnalise(modeName);

        int i = 0;
        for (Pixel p : preview.getVectorPixel()) {
            m.setLed(i++, p);
        }

        m.save(fileName);
    }
    
    /**
     * Applies a mode to the preview. Thread-safe.
     * 
     * @param m The mode to apply.
     */
    public synchronized void useMode(ModePersonnalise m) {
        preview.setPixels(m.getPixels(), Config.getConfig().getNbLed());//m.getNbled());
    }
}

/**
 * Panel containing the buttons. Used by PanelPreviewButtons.
 * 
 * @author Téo Schaffner
 */
class PanelButtons extends JPanel {

    private final JButton btnLoad;
    private final JButton btnSave;
    private final JLabel labelName;
    private JTextField textName;
    private final PanelPreviewButtons parent;
    private String fileName;
    
    /**
     * Constructor
     * 
     * @param parent The PanelPreviewButtons which contains this this PanelButtons.
     */
    public PanelButtons(PanelPreviewButtons parent) {
        btnLoad = new JButton("Charger un mode");
        btnSave = new JButton("Sauvegarder");
        labelName = new JLabel("Nom :");
        textName = new JTextField("Nouveau mode personnalisé");
        textName.setColumns(30);
        this.parent = parent;
        fileName = null;

        FlowLayout layout = new FlowLayout();
        setLayout(layout);
        add(btnLoad);
        add(labelName);
        add(textName);
        add(btnSave);

        layout.setHgap(20);
        layout.setVgap(40);
        
        //ActionListener for the button to load a mode from a file
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Select the file
                java.awt.FileDialog fd = new java.awt.FileDialog((java.awt.Frame) null, "Choix d'un fichier", java.awt.FileDialog.LOAD);
                fd.setMultipleMode(false);
                fd.setDirectory(Config.getConfig().getPersoModeDefaultDirectory());
                fd.setVisible(true);
                
                //Apply the mode from the file
                if (fd.getFile() != null) {
                    fileName = new File(fd.getDirectory() + fd.getFile()).getAbsolutePath();
                    //load the mode
                    ModePersonnalise m = ModePersonnalise.getMode(fileName);
                    //display the name of the mode
                    textName.setText(m.getName().split(".amm")[0]);
                    //apply the mode to the preview
                    parent.useMode(m);
                }
            }
        });

        //ActionListener for the button for saving the currently edited mode
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Choose file
                java.awt.FileDialog fd = new java.awt.FileDialog((java.awt.Frame) null, "Choix d'un dossier", java.awt.FileDialog.SAVE);
                fd.setMultipleMode(false);
                fd.setDirectory(Config.getConfig().getPersoModeDefaultDirectory());
                fd.setFile(textName.getText() + ".amm");
                fd.setVisible(true);

                //Save to the file (created if doesn't exist)
                if (fd.getFile() != null){
                    fileName = new File(fd.getDirectory() + fd.getFile()).getAbsolutePath();
                    parent.saveMode(fileName, textName.getText());
                }
            }
        });
    }
}
