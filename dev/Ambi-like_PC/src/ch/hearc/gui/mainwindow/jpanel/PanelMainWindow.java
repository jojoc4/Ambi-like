package ch.hearc.gui.mainwindow.jpanel;

import ch.hearc.gui.mainwindow.jpanel.previsualisation.PanelPrevisualisationDecorator;
import ch.hearc.gui.mainwindow.jpanel.choixmode.PanelChoix;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.Box;

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

        Box boxH = Box.createHorizontalBox();
        
        boxH.add(panelChoix);
        boxH.add(panelPrevisualisation);

        setLayout(new BorderLayout());
        add(boxH);
        
    }

    private void control() {

    }

    private void appearance() {

    }


}
