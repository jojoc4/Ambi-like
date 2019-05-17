/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.Preview;

import ch.hearc.Config;
import ch.hearc.PrivateMode;
import ch.hearc.Pixel;
import ch.hearc.compute.Computation_Ambilight;
import ch.hearc.compute.Computation_I;
import ch.hearc.compute.Computation_fixedColor;
import ch.hearc.compute.Computation_perso;
import ch.hearc.compute.senders.PrevisualisationSender;
import ch.hearc.compute.senders.TestSender;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author julien.chappuis1
 */
public class PanelPreviewScreen extends JPanel {

    private ImageIcon background;
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    private static final int MARGIN = 40;

    private Vector<Pixel> vectorPixels;
    private Graphics2D g2d;

    private PrevisualisationSender previewSender;
    private Computation_I computation;

    private int nbRefresh = 0;

    public PanelPreviewScreen() {
        geometry();
        appearance();

    }

    private void geometry() {
        //ImageIcon warning = MagasinImage.coffee;
        this.background = MagasinImage.fontNoir;
        //button = new JButton(warning);
        vectorPixels = new Vector<Pixel>(Config.getConfig().getNombreTotalLed()); //initial size, better performance when adding elements
        fillVector();
        this.computation = createComputation();

        Thread t = new Thread(computation);
        t.setName("Computation");
        t.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        AffineTransform transform = g2D.getTransform(); //sauvegarde
        Color color = g2D.getColor(); //sauvegarde
        Font font = g2D.getFont(); //sauvegarde

        draw(g2D);

        g2D.setFont(font); //restore
        g2D.setColor(color); //restore
        g2D.setTransform(transform); //restore
    }

    private void draw(Graphics2D g2d) {

        AffineTransform backup = g2d.getTransform();

        //this.g2d = g2d;
        updateDisplay(g2d);

        g2d.setTransform(backup);

    }

    public void updateDisplay(Graphics2D g2d) {

        int nbLedsTop = Config.getConfig().getNbLed(Config.NORTH);
        int nbLedsBottom = Config.getConfig().getNbLed(Config.SOUTH);
        int nbLedsLeft = Config.getConfig().getNbLed(Config.EAST);
        int nbLedsRight = Config.getConfig().getNbLed(Config.WEST);

        // Rectangle
        g2d.translate(MARGIN, MARGIN);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(0, 0, WIDTH - 2 * MARGIN, HEIGHT - 2 * MARGIN);

        double diameterPixel = 10.;
        double spaceBetweenLeds = (double) (WIDTH - 2 * MARGIN - nbLedsTop * diameterPixel) / (double) (nbLedsTop + 1);
        double spaceBetweenLedsWidth = (double) (HEIGHT - 2 * MARGIN - nbLedsRight * diameterPixel - 2) / (double) (nbLedsRight + 1);

        double halfMargin = MARGIN / 2;

        g2d.translate(spaceBetweenLeds, -halfMargin);
        int index = nbLedsLeft;
        for (int j = 0; j < 2; j++) {

            for (int i = 0; i < nbLedsTop; i++) {
                Pixel pixel = this.vectorPixels.get(index);
                g2d.setColor(pixel.getColor());
                index++;
                index %= Config.getConfig().getNbLedTotal();
                g2d.fill(new Ellipse2D.Double(0, 0, diameterPixel, diameterPixel));
                g2d.translate(spaceBetweenLeds + diameterPixel, 0.0);
            }

            g2d.translate(halfMargin, halfMargin);
            g2d.rotate(Math.PI / 2);
            g2d.translate(spaceBetweenLedsWidth, 0.0);

            for (int i = 0; i < nbLedsLeft; i++) {
                Pixel pixel = this.vectorPixels.get(index);
                g2d.setColor(pixel.getColor());
                index++;
                index %= Config.getConfig().getNbLedTotal();

                g2d.fill(new Ellipse2D.Double(0, 0, diameterPixel, diameterPixel));
                g2d.translate(spaceBetweenLedsWidth + diameterPixel, 0.0);

            }
            g2d.translate(halfMargin, halfMargin);
            g2d.rotate(Math.PI / 2);
            g2d.translate(spaceBetweenLeds, 0.0);
            g2d.fill(new Ellipse2D.Double(0, 0, diameterPixel, diameterPixel));

        }
    }

    public Vector<Pixel> getVectorPixel() {
        return this.vectorPixels;
    }

    public synchronized void setPixelAt(int index, Pixel pixel) {
        if (index < vectorPixels.size()) {
            vectorPixels.set(index, pixel);
            nbRefresh++;
            if (nbRefresh % 100 == 0) {
                nbRefresh = 0;
                repaint();
            }

        }
    }

    private void appearance() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    private void fillVector() {
        Config config = Config.getConfig();
        int nbLeds = config.getNombreTotalLed();

        for (int i = 0; i < nbLeds; i++) {
            this.vectorPixels.add(new Pixel(1, 1, 1));
        }
    }

    private Computation_I createComputation() {
        Computation_I c;
        this.previewSender = new PrevisualisationSender(this);
        switch (Config.getConfig().getMode()) {
            case Computation_I.MODE_AMBILIGHT:
                c = new Computation_Ambilight(previewSender);
                break;
            case Computation_I.MODE_FIXE:
                c = new Computation_fixedColor(previewSender, new Pixel(Config.getConfig().getColor()[0], Config.getConfig().getColor()[1], Config.getConfig().getColor()[3]));
                break;

            case Computation_I.MODE_PERSO:
                c = new Computation_perso(previewSender, PrivateMode.getMode(Config.getConfig().getPersoModeFile()));
                break;
            default:
                c = new Computation_Ambilight(previewSender);
        }
        return c;
    }
}
