package ch.hearc.compute.senders;

import ch.hearc.Pixel;
import ch.hearc.gui.mainwindow.jpanel.Preview.PanelPreviewScreen;

/**
 * updates the preview panel
 *
 * @version 1.2
 * @since 20.05.2019
 * @author teosc
 */
public class PreviewSender implements Sender_I {

    private PanelPreviewScreen ppe;

    /**
     *
     * @param ppe previsualisation panel
     */
    public PreviewSender(PanelPreviewScreen ppe) {
        this.ppe = ppe;
    }

    /**
     * update the leds in previsualisation
     *
     * @param indexLed led number on ledstrip
     * @param r red color, beetween 1 and 255
     * @param g green color, beetween 1 and 255
     * @param b blue color, beetween 1 and 255
     */
    @Override
    public void send(int indexLed, int r, int g, int b) {

        r = checkColor(r);
        g = checkColor(g);
        b = checkColor(b);

        try {
            ppe.setPixelAt(indexLed, new Pixel(r, g, b));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(e.getMessage());
        }
    }

    private int checkColor(int color) {
        if (color > 255) {
            return 255;
        }
        if (color < 0) {
            return 0;
        }
        return color;
    }
}
