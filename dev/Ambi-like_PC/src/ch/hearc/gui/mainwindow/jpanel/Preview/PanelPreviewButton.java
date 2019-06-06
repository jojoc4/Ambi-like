package ch.hearc.gui.mainwindow.jpanel.Preview;

import ch.hearc.gui.configurator.JFrameConfigurator;
import ch.hearc.gui.creator.FrameCreator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Describe the content of PanelPreviewButton
 *
 * @version 1.0
 * @since 17.04.2019
 * @author Julien Chappuis
 */
public class PanelPreviewButton extends JPanel {

    private JButton buttonCreateNewDisplayMode;
    private JButton buttonParameters;

    private JFrameConfigurator jFrameConfigurator;

    public PanelPreviewButton() {
        geometry();
        control();
    }

    private void geometry() {
        String message1 = "<html>Créer un mode<br>de visualisation LED</html>";

        String message2 = "<html> Paramètres de<br>l'application</html>";

        buttonCreateNewDisplayMode = new JButton(message1);
        buttonParameters = new JButton(message2);

        Box hBox = Box.createHorizontalBox();
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
}
