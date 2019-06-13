package ch.hearc.gui.creator;

import ch.hearc.gui.creator.jpanel.PanelCreator;
import javax.swing.JFrame;

/**
 * The window on which a mode can be created or modified. Extends JFrame.
 *
 * @version 3.0.1
 * @since 06.06.2019
 * @author Téo Schaffner
 */
public class FrameCreator extends JFrame {

    private PanelCreator panel;

    /**
     * Constructor
     */
    public FrameCreator() {
        geometry();
        appearance();
    }

    /**
     * Creating and displaying the frame's content.
     */
    private void geometry() {
        panel = new PanelCreator();

        add(panel);
    }

    /**
     * Sets's the frame's appearance.
     */
    private void appearance() {
        setTitle("Création de mode personnalisé");
        setSize(1300, 600);
        setResizable(false);
        setLocationRelativeTo(null); //center frame relative to screen
        setVisible(true);
    }
}
