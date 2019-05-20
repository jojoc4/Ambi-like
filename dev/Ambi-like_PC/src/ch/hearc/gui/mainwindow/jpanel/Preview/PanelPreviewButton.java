/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.Preview;

import ch.hearc.gui.configurator.JFrameConfigurator;
import ch.hearc.gui.creator.FrameCreator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelPreviewButton extends JPanel {

    private JButton buttonCreateNewDisplayMode;
    private JLabel labelDisplayLED;
    private JCheckBox checkBoxDisplayLED;
    private JButton buttonParameters;

    private JFrameConfigurator jFrameConfigurator;

    public PanelPreviewButton() {
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        labelDisplayLED = new JLabel("Visualisation LED : ");
        checkBoxDisplayLED = new JCheckBox();

        String message1 = "<html>Créer un mode<br>de visualisation LED</html>";

        String message2 = "<html> Paramètres de<br>l'application</html>";

        buttonCreateNewDisplayMode = new JButton(message1);
        buttonParameters = new JButton(message2);

        Box hBox = Box.createHorizontalBox();
        hBox.add(labelDisplayLED);
        hBox.add(checkBoxDisplayLED);
        hBox.add(Box.createHorizontalGlue());

        hBox.add(buttonCreateNewDisplayMode);
        hBox.add(buttonParameters);

        add(hBox);
    }

    private void control() {
        buttonParameters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrameConfigurator = new JFrameConfigurator();
            }
        });
        
        buttonCreateNewDisplayMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FrameCreator();
            }
        });
    }

    private void appearance() {

    }

}
