/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import ch.hearc.Config;
import ch.hearc.Pixel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author teosc
 */
public class PanelPreview extends JPanel {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    private static final int MARGIN = 40;

    private Vector<Pixel> vectorLEDs;
    private Graphics2D g2d;

    public PanelPreview() {
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        vectorLEDs = new Vector<Pixel>(Config.getConfig().getNombreTotalLed()); //initial size, better performance when adding elements
        fillVector();
    }

    private void control() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        AffineTransform transform = g2D.getTransform(); //sauvegarde
        Color color = g2D.getColor(); //sauvegarde
        Font font = g2D.getFont(); //sauvegarde

        dessiner(g2D);

        g2D.setFont(font); //restore
        g2D.setColor(color); //restore
        g2D.setTransform(transform); //restore
    }

    private void dessiner(Graphics2D g2d) {
        AffineTransform backup = g2d.getTransform();

        this.g2d = g2d;

        updateDisplay();

        g2d.setTransform(backup);
    }

    public void updateDisplay() {

        int nbLedsHaut = Config.getConfig().getNbLed(Config.NORTH);
        int nbLedsBas = Config.getConfig().getNbLed(Config.SOUTH);
        int nbLedsGauche = Config.getConfig().getNbLed(Config.EAST);
        int nbLedsDroite = Config.getConfig().getNbLed(Config.WEST);

        // Rectangle
        g2d.translate(MARGIN, MARGIN);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(0, 0, WIDTH - 2 * MARGIN, HEIGHT - 2 * MARGIN);

        double diametrePixel = 10.;
        double espaceEntreLeds = (double) (WIDTH - 2 * MARGIN - nbLedsHaut * diametrePixel) / (double) (nbLedsHaut + 1);
        double espaceEntreLedsLargeur = (double) (HEIGHT - 2 * MARGIN - nbLedsDroite * diametrePixel - 2) / (double) (nbLedsDroite + 1);

        double demiMarge = MARGIN / 2;

        g2d.translate(espaceEntreLeds, -demiMarge);
        int index = nbLedsGauche - 1;
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < nbLedsHaut; i++) {
                Pixel pixel = this.vectorLEDs.get(index);
                System.out.println(pixel.getColor());
                //g2d.setColor(pixel.getColor());
                index++;
                g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));
                g2d.translate(espaceEntreLeds + diametrePixel, 0.0);
            }

            g2d.translate(demiMarge, demiMarge);
            g2d.rotate(Math.PI / 2);
            g2d.translate(espaceEntreLedsLargeur, 0.0);

            for (int i = 0; i < nbLedsGauche; i++) {
                g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));
                g2d.translate(espaceEntreLedsLargeur + diametrePixel, 0.0);
            }
            g2d.translate(demiMarge, demiMarge);
            g2d.rotate(Math.PI / 2);
            g2d.translate(espaceEntreLeds, 0.0);
            g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));
        }
    }

    public Vector<Pixel> getVectorPixel() {
        return this.vectorLEDs;
    }

    public synchronized void setPixelAt(int index, Pixel pixel) {
        if (index < vectorLEDs.size()) {
            vectorLEDs.set(index, pixel);
            this.updateDisplay();
        }
    }

    private void appearance() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    private void fillVector() {
        int nbLeds = Config.getConfig().getNombreTotalLed();

        for (int i = 0; i < nbLeds; ++i) {
            this.vectorLEDs.add(new Pixel(0, 0, 0));
        }
    }

}
