package ch.hearc.gui.configurator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * frame that contains the configuration panel
 *
 * @author jonatan.baumgart
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

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        add(this.jPanelConfigurator, BorderLayout.CENTER);
    }

    private void control() {

    }

    private void appearance() {
        setLocationRelativeTo(null); // frame centrer
        setVisible(true); // last!
    }

}
