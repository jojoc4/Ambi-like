package ch.hearc;

import java.awt.Color;

/**
 * represents a pixel with its color
 *
 * @author Jonatan Baumgartner
 */
public class Pixel {

    private int red;
    private int green;
    private int blue;

    /**
     *
     * @param rouge red, beetween 0 and 255
     * @param vert green, beetween 0 and 255
     * @param bleu blue, beetween 0 and 255
     */
    public Pixel(int rouge, int vert, int bleu) {

        if (rouge > 255 || rouge < 0) {
            rouge = 0;
        }
        if (vert > 255 || vert < 0) {
            vert = 0;
        }
        if (bleu > 255 || bleu < 0) {
            bleu = 0;
        }
        this.red = rouge;
        this.green = vert;
        this.blue = bleu;
    }

    public synchronized int getRed() {
        return red;
    }

    public synchronized void setRed(int red) {
        if (red > 255 || red < 0) {
            red = 0;

        }
        this.red = red;

    }

    public synchronized int getGreen() {
        return green;
    }

    public synchronized void setGreen(int green) {
        if (green > 255 || green < 0) {
            green = 0;
        }
        this.green = green;
    }

    public synchronized int getBlue() {

        return blue;
    }

    public synchronized void setBlue(int blue) {
        if (blue > 255 || blue < 0) {
            blue = 0;
        }
        this.blue = blue;
    }

    /**
     * returns the color of the pixel
     *
     * @return Color of the pixel
     */
    public synchronized Color getColor() {
        System.out.println(red + " " + green + " " + blue);
        return new Color(red, green, blue);
    }

    /**
     * set pixel color
     *
     * @param c new Color
     */
    public synchronized void setColor(Color c) {
        red = (int) c.getRed();
        green = (int) c.getGreen();
        blue = (int) c.getBlue();
    }

    public String toString() {
        return "rouge : " + red + " vert : " + green + "bleu : " + blue;
    }
}
