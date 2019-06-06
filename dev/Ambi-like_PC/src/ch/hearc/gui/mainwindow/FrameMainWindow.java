package ch.hearc.gui.mainwindow;

import ch.hearc.gui.mainwindow.jpanel.PanelMainWindow;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * Contain the mainWindow panel
 *
 * @version 1.0
 * @since 17.04.2019
 * @author Julien Chappuis
 */
public class FrameMainWindow extends JFrame {

    private PanelMainWindow panelMainWindow;

    public FrameMainWindow() {
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        panelMainWindow = new PanelMainWindow();
        add(panelMainWindow);
    }

    private void control() {

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                panelMainWindow.stopComputation();
            }
        });
    }

    private void appearance() {
        setTitle("Demo");
        setSize(1000, 600);
        setResizable(false);
        setLocationRelativeTo(null); // frame centrer
        setVisible(true); // last!
    }
}
