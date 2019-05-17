/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.compute.senders;

/**
 *
 * @author teosc
 */
public class TestSender implements Sender_I {

    @Override
    public synchronized void send(int nbLed, int r, int g, int b) {
        r = checkColor(r);
        g = checkColor(g);
        b = checkColor(b);
        System.out.println("Led n°" + nbLed + ") : RGB(" + r + "; " + g + "; " + b + ")");
    }

    private int checkColor(int color) {
        if (color > 255) {
            System.out.print(" Color error! (corrected from " + color + ") ");
            return 255;
        }
        if (color < 0) {
            System.out.print(" Color error! (corrected from " + color + ") ");
            return 0;
        }

        return color;
    }

}
