package ch.hearc.gui.mainwindow.jpanel.Preview;

import ch.hearc.Config;
import ch.hearc.ModePersonnalise;
import ch.hearc.Pixel;
import ch.hearc.compute.Computation_Ambilight;
import ch.hearc.compute.Computation_I;
import ch.hearc.compute.Computation_fixedColor;
import ch.hearc.compute.Computation_perso;
import ch.hearc.compute.senders.PreviewSender;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.*;
import javax.swing.JPanel;

/**
 * Describe the content of PanelPreviewScreen
 *
 * @version 1.0
 * @since 17.04.2019
 * @author Julien Chappuis
 */
public class PanelPreviewScreen extends JPanel {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 400;
    private static final int MARGIN = 40;

    private Vector<Pixel> vectorPixels;

    private PreviewSender previewSender;
    private Computation_I computation;
    private Thread t;
    private int lastMode;

    private int nbRefresh = 0;

    private Config config;

    private Pixel previousPixel;

    private double diameterPixel;
    private double spaceBetweenLeds;
    private double spaceBetweenLedsWidth;
    private double halfMargin;
    private int nbLedsTotal;

    public PanelPreviewScreen() {
        config = Config.getConfig();
        previousPixel = new Pixel(0, 0, 0);
        geometry();
        appearance();

    }

    public void startComputation() {
        this.lastMode = Config.getConfig().getMode();
        this.computation = createComputation();
        t = new Thread(computation);
        t.setName("Computation");
        t.start();
    }

    private void geometry() {
        vectorPixels = new Vector<Pixel>(Config.getConfig().getNbLedTotal()); //initial size, better performance when adding elements
        fillVector();
        startComputation();
    }

    /**
     * Draw the preview screen on this JPanel
     *
     * @param g graphical con text
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        // sauvegarde
        AffineTransform transform = g2D.getTransform();
        Color color = g2D.getColor();
        Font font = g2D.getFont();

        draw(g2D);

        // restaure
        g2D.setFont(font);
        g2D.setColor(color);
        g2D.setTransform(transform);
    }

    /**
     * Draw the preview screen on this JPanel without axis transformation
     *
     * @param g graphical context
     */
    private void draw(Graphics2D g2d) {
        AffineTransform backup = g2d.getTransform();

        updateDisplay(g2d);

        g2d.setTransform(backup);
    }

    /**
     * Draw the stuff with all the parameters required
     *
     * @param g2d graphical context
     */
    public void updateDisplay(Graphics2D g2d) {
        int nbLedsTop = Config.getConfig().getNbLed(Config.NORTH);
        int nbLedsLeft = Config.getConfig().getNbLed(Config.WEST);
        int nbLedsRight = Config.getConfig().getNbLed(Config.EAST);
        int nbLedsBottom = Config.getConfig().getNbLed(Config.SOUTH);

        nbLedsTotal = nbLedsTop + nbLedsBottom + nbLedsLeft + nbLedsRight;

        // Rectangle
        g2d.translate(MARGIN, MARGIN);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(0, 0, WIDTH - 2 * MARGIN, HEIGHT - 2 * MARGIN);
        g2d.drawImage(computation.getImage(), 1, 1, WIDTH - 2 * MARGIN - 1, HEIGHT - 2 * MARGIN - 1, this);

        diameterPixel = 10.;
        spaceBetweenLeds = (double) (WIDTH - 2 * MARGIN - nbLedsTop * diameterPixel) / (double) (nbLedsTop + 1);
        spaceBetweenLedsWidth = (double) (HEIGHT - 2 * MARGIN - nbLedsRight * diameterPixel - 2) / (double) (nbLedsRight + 1);

        halfMargin = MARGIN / 2;

        g2d.translate(spaceBetweenLeds, -halfMargin);
        int index = nbLedsLeft;

        for (int i = 0; i < nbLedsTop; i++) {
            index = drawEllipse(g2d, index);
            g2d.translate(spaceBetweenLeds + diameterPixel, 0.0);
        }

        rotateTranslate(g2d);
        g2d.translate(spaceBetweenLedsWidth, 0.0);

        for (int i = 0; i < nbLedsRight; i++) {
            index = drawEllipse(g2d, index);
            g2d.translate(spaceBetweenLedsWidth + diameterPixel, 0.0);

        }
        rotateTranslate(g2d);
        g2d.translate(spaceBetweenLeds, 0.0);

        for (int i = 0; i < nbLedsBottom; i++) {
            index = drawEllipse(g2d, index);
            g2d.translate(spaceBetweenLeds + diameterPixel, 0.0);
        }
        // s'il n'y a pas de leds en bas
        for (int i = 0; i < nbLedsTop - nbLedsBottom; i++) {
            g2d.translate(spaceBetweenLeds + diameterPixel, 0.0);
        }

        rotateTranslate(g2d);
        g2d.translate(spaceBetweenLedsWidth, 0.0);

        for (int i = 0; i < nbLedsLeft; i++) {
            index = drawEllipse(g2d, index);
            g2d.translate(spaceBetweenLedsWidth + diameterPixel, 0.0);

        }
        rotateTranslate(g2d);
        g2d.translate(spaceBetweenLeds, 0.0);

    }

    /**
     * Rotate and translate
     *
     * @param g2d graphical context
     */
    public void rotateTranslate(Graphics2D g2d) {
        g2d.translate(halfMargin, halfMargin);
        g2d.rotate(Math.PI / 2);
    }

    /**
     * Draw an ellipse
     *
     * @param g2d g2d instance
     * @param index : index of the led to draw
     * @return the index of the next led
     */
    public int drawEllipse(Graphics2D g2d, int index) {
        Pixel pixel = this.vectorPixels.get(index);
        g2d.setColor(pixel.getColor());
        index++;
        index %= nbLedsTotal;

        g2d.fill(new Ellipse2D.Double(0, 0, diameterPixel, diameterPixel));

        return index;
    }

    /**
     * Getter
     *
     * @return Vector contains pixel object
     */
    public Vector<Pixel> getVectorPixel() {
        return this.vectorPixels;
    }

    /**
     * Called by PreviewSender to fill the vector of Pixel
     *
     * @param index of the led
     * @param pixel to add on a specific index
     */
    public synchronized void setPixelAt(int index, Pixel pixel) {
        if (index < vectorPixels.size()) {
            vectorPixels.set(index, pixel);
            nbRefresh++;
            if (nbRefresh % 100 == 0 && config.getMode() == Computation_I.MODE_AMBILIGHT) {
                nbRefresh = 0;
                repaint();
            }
            if (Computation_I.MODE_FIXE == config.getMode() && nbRefresh % 100 == 0) {
                nbRefresh = 0;
                repaint();
            }

            if (Computation_I.MODE_PERSO == config.getMode() && nbRefresh % 5 == 0) {
                nbRefresh = 0;
                repaint();
            }
        }
    }

    private void appearance() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    /**
     * Initialize the vector with default values
     */
    private void fillVector() {
        Config config = Config.getConfig();
        int nbLeds = config.getNbLedTotal();

        for (int i = 0; i < nbLeds; i++) {
            this.vectorPixels.add(new Pixel(1, 1, 1));
        }
    }

    /**
     * Manage the computation when the user changes the mode
     *
     * @return Computation_I
     */
    private Computation_I createComputation() {
        Computation_I c;
        this.previewSender = new PreviewSender(this);
        switch (Config.getConfig().getMode()) {
            case Computation_I.MODE_AMBILIGHT:
                c = new Computation_Ambilight(previewSender);
                break;
            case Computation_I.MODE_FIXE:
                c = new Computation_fixedColor(previewSender, new Pixel(Config.getConfig().getColor()[0], Config.getConfig().getColor()[1], Config.getConfig().getColor()[2]));
                this.previousPixel = new Pixel(Config.getConfig().getColor()[0], Config.getConfig().getColor()[1], Config.getConfig().getColor()[2]);
                break;

            case Computation_I.MODE_PERSO:
                c = new Computation_perso(previewSender, ModePersonnalise.getMode(Config.getConfig().getPersoModeFile()));
                break;
            default:
                c = new Computation_Ambilight(previewSender);
        }
        return c;
    }

    /**
     * Change the computation mode according to the Configfile Singleton
     */
    public void changeComputation() {
        if (config.getMode() != this.lastMode || (config.getColor()[0] != this.previousPixel.getRed()
                || config.getColor()[1] != this.previousPixel.getGreen()
                || config.getColor()[2] != this.previousPixel.getBlue()) || config.getMode() == Computation_I.MODE_PERSO) {
            this.computation.stopComputation();
            this.startComputation();
        }
    }

    /**
     * Stop the ancient computation when the user change the mode.
     */
    public void stopComputation() {
        this.computation.stopComputation();
    }
}
