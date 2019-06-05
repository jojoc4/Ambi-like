/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.Preview;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelPreview extends JPanel {

    private PanelPreviewScreen panelPreviewScreen;
    private PanelPreviewButton panelPreviewButton;
    
    

    public PanelPreview(PanelPreviewScreen panelPreviewScreen) {
        this.panelPreviewScreen = panelPreviewScreen;
        geometry();
        control();
        appearance();

    }

    private void geometry() {
        panelPreviewButton = new PanelPreviewButton();

        Box vLayout = Box.createVerticalBox();
        setLayout(new BorderLayout());

        vLayout.add(panelPreviewScreen);
        vLayout.add(panelPreviewButton);
        add(vLayout, BorderLayout.CENTER);

    }

    private void control() {

    }

    private void appearance() {
        setBorder(BorderFactory.createTitledBorder("Prévisualisation"));
    }

    public void stopComputation() {
        this.panelPreviewScreen.stopComputation();
    }

}
