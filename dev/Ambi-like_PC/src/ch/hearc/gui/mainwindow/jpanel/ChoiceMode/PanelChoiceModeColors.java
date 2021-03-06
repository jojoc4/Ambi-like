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
 * Describe PanelChoiceModeColors content
 *
 * @version 1.0
 * @since 18.04.2019
 * @author Julien Chappuis
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

        Box boxHorizontalRed = Box.createHorizontalBox();
        boxHorizontalRed.add(labelRed);
        boxHorizontalRed.add(jTextFieldRed);
        boxHorizontalRed.add(jSliderRed);

        Box boxHorizontalBlue = Box.createHorizontalBox();
        boxHorizontalBlue.add(labelBlue);
        boxHorizontalBlue.add(jTextFieldBlue);
        boxHorizontalBlue.add(jSliderBlue);

        Box boxHorizontalGreen = Box.createHorizontalBox();
        boxHorizontalGreen.add(labelGreen);
        boxHorizontalGreen.add(jTextFieldGreen);
        boxHorizontalGreen.add(jSliderGreen);

        Box boxVertical = Box.createVerticalBox();
        boxVertical.add(boxHorizontalRed);
        boxVertical.add(boxHorizontalBlue);
        boxVertical.add(boxHorizontalGreen);

        setLayout(new BorderLayout());
        add(boxVertical, BorderLayout.CENTER);

    }

    private void control() {
        jSliderRed.addChangeListener(addListener(jTextFieldRed, jSliderRed));
        jSliderGreen.addChangeListener(addListener(jTextFieldGreen, jSliderGreen));
        jSliderBlue.addChangeListener(addListener(jTextFieldBlue, jSliderBlue));
    }

    /**
     * Add an action Listener on the the JTextField and modify the JSlider
     *
     * @param textField to focus with the listener
     * @param slider which one the slider changes the value
     * @return an instance of ChangeListener set to the JSlider
     */
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
