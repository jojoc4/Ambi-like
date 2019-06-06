package ch.hearc.gui.configurator;

import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 * frame that contains the configuration panel
 *
 * @author Jonatan Baumgartner
 */
public class JFrameConfigurator extends JFrame {

    private JPanelConfigurator jPanelConfigurator;

    public JFrameConfigurator() {
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        this.jPanelConfigurator = new JPanelConfigurator();

        setLayout(new BorderLayout());

        add(this.jPanelConfigurator, BorderLayout.CENTER);
    }

    private void control() {

    }

    private void appearance() {
        this.setSize(220, 330);
        this.setLocation(300, 500);
        this.setTitle("Configurator");
        this.setVisible(true); //imp�rativement EN DERNIER !!
    }

}
