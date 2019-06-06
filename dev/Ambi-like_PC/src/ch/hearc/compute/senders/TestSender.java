package ch.hearc.compute.senders;

/**
 * test class
 *
 * @version 1
 * @since 03.04.2019
 * @author teosc
 */
public class TestSender implements Sender_I {

    /**
     * sysout the datas
     *
     * @param nbLed led number on ledstrip
     * @param r red color, beetween 1 and 255
     * @param g green color, beetween 1 and 255
     * @param b blue color, beetween 1 and 255
     */
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
