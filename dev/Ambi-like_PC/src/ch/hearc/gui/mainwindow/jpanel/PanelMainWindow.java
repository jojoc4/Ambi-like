package ch.hearc.gui.mainwindow.jpanel;

import ch.hearc.gui.mainwindow.jpanel.previsualisation.PanelPrevisualisationDecorator;
import ch.hearc.gui.mainwindow.jpanel.choixmode.PanelChoix;
import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelMainWindow extends JPanel {

    private PanelChoix panelChoix;
    private PanelPrevisualisationDecorator panelPrevisualisation;
    
    public PanelMainWindow() {

        geometry();
        control();
        appearance();
    }

    private void geometry() {
        panelChoix = new PanelChoix();
        panelPrevisualisation = new PanelPrevisualisationDecorator();

        GridLayout layout = new GridLayout(1, 2);
        setLayout(layout);
        add(panelChoix);
        add(panelPrevisualisation);
    }

    private void control() {

    }

    private void appearance() {

    }


}
