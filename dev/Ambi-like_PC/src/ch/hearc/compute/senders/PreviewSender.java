/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.compute.senders;

import ch.hearc.Pixel;
import ch.hearc.gui.mainwindow.jpanel.Preview.PanelPreviewScreen;

/**
 *
 * @author teosc
 */
public class PreviewSender implements Sender_I {

    private PanelPreviewScreen ppe;

    public PreviewSender(PanelPreviewScreen ppe) {
        this.ppe = ppe;
    }

    @Override
    public void send(int indexLed, int r, int g, int b) {
//        Pixel test = (Pixel) ppe.getVectorPixel().elementAt(nbLed);
//        test.setRed(r);
//        test.setGreen(g);
//        test.setBlue(b);

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
