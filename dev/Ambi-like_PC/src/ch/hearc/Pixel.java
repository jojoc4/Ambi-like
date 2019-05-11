package ch.hearc;

import java.awt.Color;

/**
 * represents a pixel with its color
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
        this.red = rouge;
        this.green = vert;
        this.blue = bleu;
    }

    public synchronized int getRed() {
        return red;
    }

    public synchronized void setRed(int red) {
        this.red = red;
    }

    public synchronized int getGreen() {
        return green;
    }

    public synchronized void setGreen(int green) {
        this.green = green;
    }

    public synchronized int getBlue() {
        return blue;
    }

    public synchronized void setBlue(int blue) {
        this.blue = blue;
    }

    /**
     * returns the color of the pixel
     * @return Color of the pixel
     */
    public synchronized Color getColor() {
        return new Color(red, green, blue);
    }

    /**
     * set pixel color
     * @param c new Color
     */
    public synchronized void setColor(Color c) {
        red = (int) c.getRed();
        green = (int) c.getGreen();
        blue = (int) c.getBlue();
    }

}
