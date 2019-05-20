/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import ch.hearc.Config;
import ch.hearc.Pixel;
import ch.hearc.compute.Computation_I;
import ch.hearc.compute.Computation_fixedColor;
import ch.hearc.compute.Computation_perso;
import ch.hearc.compute.senders.TestSender;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import javax.swing.JPanel;

/**
 *
 * @author teosc
 */
public class PanelPreview extends JPanel {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 360;
    private static final int MARGIN = 40;

    private Vector<Pixel> vectorLEDs;
    private Graphics2D g2d;
    private PanelColorChooser colorChooser;
    private BufferedImage img;

    public PanelPreview(PanelColorChooser colorChooser) {
        this.colorChooser = colorChooser;
        vectorLEDs = new Vector<Pixel>(Config.getConfig().getNbLedTotal()); //initial size, better performance when adding elements
        
        Computation_I c = new Computation_fixedColor(new TestSender(), new Pixel(0,0,0));
        c.stopComputation();
        img = c.getImage();
        c = null;
        
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        
        fillVector();
    }

    private void control() {

    }
    
    private void appearance() {
        setPreferredSize(new Dimension(WIDTH + 2*MARGIN, HEIGHT + 2*MARGIN));
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
        int nbLedsGauche = Config.getConfig().getNbLed(Config.WEST);
        int nbLedsDroite = Config.getConfig().getNbLed(Config.EAST);
        
        
        AffineTransform transform = g2d.getTransform();
        // Rectangle
        g2d.translate(MARGIN, MARGIN);
        g2d.setStroke(new BasicStroke(1));
        //g2d.drawRect(0, 0, WIDTH - 2*MARGIN, HEIGHT - 2*MARGIN);
        g2d.drawImage(img, 0, 0, WIDTH - 2*MARGIN, HEIGHT - 2*MARGIN, this);

        double diametrePixel = 10.;
        double espaceEntreLeds = (double) (WIDTH - 2 * MARGIN - nbLedsHaut * diametrePixel) / (double) (nbLedsHaut + 1);
        double espaceEntreLedsLargeur = (double) (HEIGHT - 2 * MARGIN - nbLedsDroite * diametrePixel - 2) / (double) (nbLedsDroite + 1);
        
        double espaceGauche = ((HEIGHT - 2*MARGIN) - (nbLedsGauche * diametrePixel)) / nbLedsGauche;
        double espaceHaut = ((WIDTH - 2*MARGIN) - (nbLedsHaut * diametrePixel)) / nbLedsHaut;
        double espaceDroite = ((HEIGHT - 2*MARGIN) - (nbLedsDroite * diametrePixel)) / nbLedsDroite;
        double espaceBas = ((WIDTH - 2*MARGIN) - (nbLedsBas * diametrePixel)) / nbLedsBas;
        
        g2d.setTransform(transform); //restore
        g2d.translate(MARGIN/2, MARGIN);
        g2d.rotate(Math.PI / 2);
        g2d.translate(espaceGauche/2, 0);
        
        //LEFT
        for(int i=0; i<nbLedsGauche; ++i){
            g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));
            g2d.translate(espaceGauche + diametrePixel, 0d);
        }
        
        //TOP
        g2d.setTransform(transform); //restore
        g2d.translate(MARGIN, MARGIN/2);
        g2d.translate(espaceHaut/2, 0);
        
        for(int i=0; i<nbLedsHaut; ++i){
            g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));
            g2d.translate(espaceHaut + diametrePixel, 0d);
        }
        
        //RIGHT
        g2d.setTransform(transform); //restore
        g2d.translate(WIDTH - (MARGIN/2), MARGIN);
        g2d.rotate(Math.PI / 2);
        g2d.translate(espaceDroite/2, 0);
        
        for(int i=0; i<nbLedsDroite; ++i){
            g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));
            g2d.translate(espaceDroite + diametrePixel, 0d);
        }
        
        //BOTTOM
        g2d.setTransform(transform); //restore
        g2d.translate(WIDTH - MARGIN, HEIGHT - (MARGIN/2));
        g2d.rotate(-Math.PI);
        g2d.translate(espaceBas/2, 0);
        
        for(int i=0; i<nbLedsBas; ++i){
            g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));
            g2d.translate((espaceBas + diametrePixel), 0d);
        }

        g2d.setTransform(transform); //restore
        /*
        double demiMarge = MARGIN / 2;

        g2d.translate(espaceEntreLeds, -demiMarge);
        
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < nbLedsHaut; i++) {
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
        */
    }

    public Vector<Pixel> getVectorPixel() {
        return this.vectorLEDs;
    }

    public synchronized void setPixelAt(int index, Pixel pixel) {
        if (index < vectorLEDs.size()) {
            vectorLEDs.set(index, pixel);
            //this.updateDisplay();
        }
    }


    private void fillVector() {
        int nbLeds = Config.getConfig().getNbLedTotal();

        for (int i = 0; i < nbLeds; ++i) {
            this.vectorLEDs.add(new Pixel(0, 0, 0));
        }
    }

}
