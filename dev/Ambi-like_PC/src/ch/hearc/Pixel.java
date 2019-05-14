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
    public Pixel(int rouge, int vert, int bleu) throws IllegalArgumentException{

        if (rouge > 255 || rouge < 0) {
            throw new IllegalArgumentException("red value must be between [0-255], got " + rouge);
        }
        if (vert > 255 || vert < 0) {
            throw new IllegalArgumentException("green value must be between [0-255], got " + rouge);
        }
        if (bleu > 255 || bleu < 0) {
            throw new IllegalArgumentException("blue value must be between [0-255], got " + rouge);
        }
        
        this.red = rouge;
        this.green = vert;
        this.blue = bleu;
    }

    public synchronized int getRed() {
        return red;
    }

    public synchronized void setRed(int red) throws IllegalArgumentException {
        if (red > 255 || red < 0) {
            throw new IllegalArgumentException("red value must be between [0-255], got " + red);
        }
        this.red = red;

    }

    public synchronized int getGreen() {
        return green;
    }

    public synchronized void setGreen(int green) throws IllegalArgumentException {
        if (green > 255 || green < 0) {
            throw new IllegalArgumentException("green value must be between [0-255], got " + green);
        }
        this.green = green;
    }

    public synchronized int getBlue() {

        return blue;
    }

    public synchronized void setBlue(int blue) throws IllegalArgumentException {
        if (blue > 255 || blue < 0) {
            throw new IllegalArgumentException("blue value must be between [0-255], got " + blue);
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
        return "rouge : " + red + " vert : " + green + " bleu : " + blue;
    }
}
