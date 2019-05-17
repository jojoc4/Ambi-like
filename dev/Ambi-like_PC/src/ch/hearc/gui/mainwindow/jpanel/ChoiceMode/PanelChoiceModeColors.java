/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.ChoiceMode;

import ch.hearc.Config;
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author julien.chappuis1
 */
public class PanelChoiceModeColors extends JPanel {

    private JLabel labelRed;
    private JTextField jTextFieldRed;
    private JSlider jSliderRed;

    private JLabel labelBlue;
    private JTextField jTextFieldBlue;
    private JSlider jSliderBlue;

    private JLabel labelGreen;
    private JTextField jTextFieldGreen;
    private JSlider jSliderGreen;

    private PanelChoiceModeSettings panelChoiceModeSettings;

    public PanelChoiceModeColors(PanelChoiceModeSettings panel) {
        this.panelChoiceModeSettings = panel;
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        labelRed = new JLabel("Rouge : ");
        jTextFieldRed = new JTextField();
        jSliderRed = new JSlider();

        labelBlue = new JLabel("Bleu : ");
        jTextFieldBlue = new JTextField();
        jSliderBlue = new JSlider();

        labelGreen = new JLabel("Vert : ");
        jTextFieldGreen = new JTextField();
        jSliderGreen = new JSlider();

        Box boxHorizontalRouge = Box.createHorizontalBox();
        boxHorizontalRouge.add(labelRed);
        boxHorizontalRouge.add(jTextFieldRed);
        boxHorizontalRouge.add(jSliderRed);

        Box boxHorizontalBleu = Box.createHorizontalBox();
        boxHorizontalBleu.add(labelBlue);
        boxHorizontalBleu.add(jTextFieldBlue);
        boxHorizontalBleu.add(jSliderBlue);

        Box boxHorizontalVert = Box.createHorizontalBox();
        boxHorizontalVert.add(labelGreen);
        boxHorizontalVert.add(jTextFieldGreen);
        boxHorizontalVert.add(jSliderGreen);

        Box boxVertical = Box.createVerticalBox();
        boxVertical.add(boxHorizontalRouge);
        boxVertical.add(boxHorizontalBleu);
        boxVertical.add(boxHorizontalVert);

        setLayout(new BorderLayout());
        add(boxVertical, BorderLayout.CENTER);

    }

    private void control() {
        jSliderRed.addChangeListener(addListener(jTextFieldRed, jSliderRed));
        jSliderGreen.addChangeListener(addListener(jTextFieldGreen, jSliderGreen));
        jSliderBlue.addChangeListener(addListener(jTextFieldBlue, jSliderBlue));
    }

    private ChangeListener addListener(JTextField textField, JSlider slider) {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textField.setText(Integer.toString(slider.getValue()));

                int[] tabColor = new int[3];

                Config config = Config.getConfig();

                tabColor[0] = jSliderRed.getValue();
                tabColor[1] = jSliderGreen.getValue();
                tabColor[2] = jSliderBlue.getValue();
                config.setColor(tabColor);
            }
        };
    }

    private void appearance() {
        jSliderRed.setMaximum(255);
        jSliderRed.setMinimum(0);
        jSliderRed.setValue(127);

        jSliderBlue.setMaximum(255);
        jSliderBlue.setMinimum(0);
        jSliderBlue.setValue(127);

        jSliderGreen.setMaximum(255);
        jSliderGreen.setMinimum(0);
        jSliderGreen.setValue(127);

        this.setVisible(false);
    }

}
