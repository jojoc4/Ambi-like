package ch.hearc.gui.mainwindow.jpanel.ChoiceMode;

import ch.hearc.Config;
import ch.hearc.Main;
import ch.hearc.compute.Computation_I;
import ch.hearc.gui.mainwindow.jpanel.Preview.PanelPreviewScreen;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Describe PanelChoice content
 *
 * @version 1.0
 * @since 18.04.2019
 * @author Julien Chappuis
 */
public class PanelChoice extends JPanel {

    private PanelChoiceMode panelChoiceMode;
    private JButton buttonApplyParameter;

    private PanelPreviewScreen panelPreviewScreen;

    private Config config;

    public PanelChoice(PanelPreviewScreen panelPreviewScreen) {
        this.panelPreviewScreen = panelPreviewScreen;
        geometry();
        control();
    }

    private void geometry() {
        panelChoiceMode = new PanelChoiceMode();
        buttonApplyParameter = new JButton("Appliquer le mode sélectionné");

        config = Config.getConfig();

        BorderLayout layout = new BorderLayout();
        Box boxV = Box.createVerticalBox();
        boxV.add(panelChoiceMode);
        boxV.add(buttonApplyParameter);
        boxV.add(Box.createVerticalGlue());
        setLayout(layout);
        add(boxV, BorderLayout.CENTER);
    }

    private void control() {
        buttonApplyParameter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (config.getMode() != Main.getTempMode() || config.getMode() == Computation_I.MODE_FIXE || config.getMode() == Computation_I.MODE_PERSO) {
                    config.setMode(Main.getTempMode());
                    Main.changeMode();
                    panelPreviewScreen.changeComputation();
                }
            }
        });
    }
}
