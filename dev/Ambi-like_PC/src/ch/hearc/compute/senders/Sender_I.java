package ch.hearc.compute.senders;

/**
 * interface to commande either the led, do a sysout or update the
 * previsualisation interface
 *
 * @version 1.2
 * @since 06.05.2019
 * @author teosc
 */
public interface Sender_I {

    /**
     * send new color to raspberry pi
     *
     * @param nbLed led number on ledstrip
     * @param r red color, beetween 1 and 255
     * @param g green color, beetween 1 and 255
     * @param b blue color, beetween 1 and 255
     */
    public void send(int nbLed, int r, int g, int b);

}
