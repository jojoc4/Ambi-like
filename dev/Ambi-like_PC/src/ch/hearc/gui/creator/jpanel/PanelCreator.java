/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import ch.hearc.Config;
import ch.hearc.Pixel;
import ch.hearc.gui.mainwindow.jpanel.previsualisation.MagasinImage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author teosc
 */
public class PanelCreator extends JPanel {

    private static final int LONGUEUR = 500;
    private static final int LARGEUR = 400;
    private static final int MARGE = 40;

    private Vector<Pixel> vectorLEDs;
    private Graphics2D g2d;

    public PanelCreator() {
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
        g2d.translate(MARGE, MARGE);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(0, 0, LONGUEUR - 2 * MARGE, LARGEUR - 2 * MARGE);

        double diametrePixel = 10.;
        double espaceEntreLeds = (double) (LONGUEUR - 2 * MARGE - nbLedsHaut * diametrePixel) / (double) (nbLedsHaut + 1);
        double espaceEntreLedsLargeur = (double) (LARGEUR - 2 * MARGE - nbLedsDroite * diametrePixel - 2) / (double) (nbLedsDroite + 1);

        double demiMarge = MARGE / 2;

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

    public synchronized void setPixelAt(int index, Pixel pixel) throws ArrayIndexOutOfBoundsException {
        if (index >= vectorLEDs.size()) {
            throw new ArrayIndexOutOfBoundsException("index " + index + " too big for vectorPixel (" + vectorLEDs.size() + " elements)");
        }
        this.updateDisplay();
        vectorLEDs.set(index, pixel);
    }

    private void appearance() {
        setPreferredSize(new Dimension(LONGUEUR, LARGEUR));
    }

    private void fillVector() {
        int nbLeds = Config.getConfig().getNombreTotalLed();

        for (int i = 0; i < nbLeds; ++i) {
            this.vectorLEDs.add(new Pixel(0, 0, 0));
        }
    }

}
