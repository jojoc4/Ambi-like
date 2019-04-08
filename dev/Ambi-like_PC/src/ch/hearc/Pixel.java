package ch.hearc;

import javafx.scene.paint.Color;

/**
 *
 * @author Jonatan Baumgartner
 */
public class Pixel {

    private int red;
    private int green;
    private int blue;

    public Pixel(int rouge, int vert, int bleu) {
        this.red = rouge;
        this.green = vert;
        this.blue = bleu;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public Color getColor() {
        return Color.color(red, green, blue);
    }

    public void setColor(Color c) {
        red = (int) c.getRed();
        green = (int) c.getGreen();
        blue = (int) c.getBlue();
    }

}
