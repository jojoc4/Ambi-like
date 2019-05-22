package ch.hearc.gui.mainwindow.jpanel;

import ch.hearc.gui.mainwindow.jpanel.ChoiceMode.PanelChoice;
import ch.hearc.gui.mainwindow.jpanel.Preview.PanelPreview;
import java.awt.BorderLayout;
import javax.swing.Box;

import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelMainWindow extends JPanel {

    private PanelChoice panelChoice;
    private PanelPreview panelPreview;

    public PanelMainWindow() {

        geometry();
        control();
        appearance();
    }

    private void geometry() {
        panelChoice = new PanelChoice();
        panelPreview = new PanelPreview();

        Box boxH = Box.createHorizontalBox();

        boxH.add(panelChoice);
        boxH.add(panelPreview);

        setLayout(new BorderLayout());
        add(boxH);

    }

    private void control() {

    }

    private void appearance() {

    }

    public void stopComputation() {
        this.panelPreview.stopComputation();
    }

}
