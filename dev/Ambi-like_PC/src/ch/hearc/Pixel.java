package ch.hearc;

import java.awt.Color;
import java.io.Serializable;

/**
 * represents a pixel with its color
 *
 * @author Jonatan Baumgartner
 */
public class Pixel implements Serializable {

    private int red;
    private int green;
    private int blue;

    /**
     *
     * @param red red, beetween 0 and 255
     * @param green green, beetween 0 and 255
     * @param blue blue, beetween 0 and 255
     */
    public Pixel(int red, int green, int blue) throws IllegalArgumentException {

        if (red > 255 || red < 0) {
            throw new IllegalArgumentException("red value must be between [0-255], got " + red);
        }
        if (green > 255 || green < 0) {
            throw new IllegalArgumentException("green value must be between [0-255], got " + red);
        }
        if (blue > 255 || blue < 0) {
            throw new IllegalArgumentException("blue value must be between [0-255], got " + red);
        }

        this.red = red;
        this.green = green;
        this.blue = blue;
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
