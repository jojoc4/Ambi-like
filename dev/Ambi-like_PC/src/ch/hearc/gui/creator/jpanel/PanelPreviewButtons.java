/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import ch.hearc.ModePersonnalise;
import ch.hearc.Pixel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

        //System.out.println("PanelPreviewButtons - width: " + getWidth() + " height: " + getHeight() + " x: " + getX() + " y: " + getY() + " visible: " + isVisible() + " valid: " + isValid());
    }

    public void saveMode(String fileName, String modeName) {
        ModePersonnalise m = new ModePersonnalise(modeName);

        int i = 0;
        for (Pixel p : preview.getVectorPixel()) {
            m.setLed(i++, p);
        }

        m.save(fileName);
    }

    public synchronized void useMode(ModePersonnalise m) {
        preview.setPixels(m.getPixels(), m.getNbled());
    }
}

class PanelButtons extends JPanel {

    private final JButton btnLoad;
    private final JButton btnSave;
    private final JLabel labelName;
    private JTextField textName;
    private final PanelPreviewButtons parent;
    private String fileName;

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

        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.awt.FileDialog fd = new java.awt.FileDialog((java.awt.Frame) null, "Choix d'un fichier", java.awt.FileDialog.LOAD);
                fd.setMultipleMode(false);
                fd.setVisible(true);

                fileName = fd.getFile();

                if (fileName != null) {
                    ModePersonnalise m = ModePersonnalise.getMode(fileName);
                    textName.setText(m.getName().split(".amm")[0]);
                    parent.useMode(m);
                }
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.awt.FileDialog fd = new java.awt.FileDialog((java.awt.Frame) null, "Choix d'un dossier", java.awt.FileDialog.SAVE);
                fd.setMultipleMode(false);
                fd.setFile(textName.getText() + ".amm");
                fd.setVisible(true);

                fileName = fd.getFile();

                if (fileName != null) {
                    parent.saveMode(fileName, textName.getText());
                }
            }
        });

        //System.out.println("PanelButtons - width: " + getWidth() + " height: " + getHeight() + " x: " + getX() + " y: " + getY() + " visible: " + isVisible() + " valid: " + isValid());
    }
}
