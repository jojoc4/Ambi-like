package ch.hearc.gui.mainwindow.jpanel.Preview;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;

/**
 * Describe the content of PanelPreview
 *
 * @version 1.0
 * @since 17.04.2019
 * @author Julien Chappuis
 */
public class PanelPreview extends JPanel {

    private PanelPreviewScreen panelPreviewScreen;
    private PanelPreviewButton panelPreviewButton;

    public PanelPreview(PanelPreviewScreen panelPreviewScreen) {
        this.panelPreviewScreen = panelPreviewScreen;
        geometry();
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

    private void appearance() {
        setBorder(BorderFactory.createTitledBorder("Prévisualisation"));
    }

    public void stopComputation() {
        this.panelPreviewScreen.stopComputation();
    }

}
