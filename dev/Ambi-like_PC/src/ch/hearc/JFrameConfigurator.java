package ch.hearc;

import ch.hearc.panel.configurator.JPanelConfigurator;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 *
 * @author jonatan.baumgart
 */
public class JFrameConfigurator extends JFrame {

    private JPanelConfigurator panel;

    public JFrameConfigurator() {
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        panel = new JPanelConfigurator();

        setLayout(new BorderLayout());

        add(panel, BorderLayout.CENTER);
    }

    private void control() {
        
    }

    private void appearance() {
        this.setSize(800, 600);
        this.setLocation(300, 500);
        this.setTitle("Configuration");
        this.setVisible(true);
    }
}
