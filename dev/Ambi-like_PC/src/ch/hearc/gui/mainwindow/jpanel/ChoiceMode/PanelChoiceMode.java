package ch.hearc.gui.mainwindow.jpanel.ChoiceMode;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;

/**
 * Describe PanelChoiceMode content
 *
 * @version 1.0
 * @since 18.04.2019
 * @author Julien Chappuis
 */
public class PanelChoiceMode extends JPanel {

    private PanelChoiceModeSettings panelChoiceModeSettings;

    public PanelChoiceMode() {
        geometry();
        appearance();

    }

    private void geometry() {
        panelChoiceModeSettings = new PanelChoiceModeSettings();

        Box boxv = Box.createVerticalBox();
        boxv.add(panelChoiceModeSettings);
        boxv.add(Box.createHorizontalGlue());

        add(boxv);
    }

    private void appearance() {
        setBorder(BorderFactory.createTitledBorder("Choix du mode"));
    }
}
