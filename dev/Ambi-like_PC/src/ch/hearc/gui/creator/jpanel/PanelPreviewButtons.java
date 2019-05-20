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
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author teosc
 */
public class PanelPreviewButtons extends JPanel{
    
    private PanelPreview preview;
    private PanelButtons buttons;
    
    public PanelPreviewButtons() {
        
        this.preview = new PanelPreview();
        this.buttons = new PanelButtons(this);
        
        add(preview, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
    
    public void saveMode() {
        ModePersonnalise m = new ModePersonnalise();
        
        int i=0;
        for(Pixel p : preview.getVectorPixel()){
            m.setLed(i++, p);
        }
        
        m.save("test.amm");
    }
}


class PanelButtons extends JPanel {
    private JButton btnSave;
    private PanelPreviewButtons parent;

    public PanelButtons(PanelPreviewButtons parent) {
        btnSave = new JButton("Sauvegarder");
        this.parent = parent;

        setLayout(new BorderLayout());
        add(Box.createHorizontalStrut(20), BorderLayout.WEST);
        add(Box.createHorizontalStrut(20), BorderLayout.EAST);
        add(btnSave, BorderLayout.CENTER);
        add(Box.createVerticalStrut(20), BorderLayout.SOUTH);
        add(Box.createVerticalStrut(20), BorderLayout.NORTH);
        
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.saveMode();
            }
        });
    }
}