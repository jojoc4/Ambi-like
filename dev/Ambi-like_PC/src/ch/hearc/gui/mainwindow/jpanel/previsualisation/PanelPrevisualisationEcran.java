/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.previsualisation;

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
import java.awt.geom.Rectangle2D;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelPrevisualisationEcran extends JPanel {

    private ImageIcon fontNoir;
    private static final int LONGUEUR = 500;
    private static final int LARGEUR = 400;
    private static final int MARGE = 40;

    private Vector<Pixel> vectorPixels;

    public PanelPrevisualisationEcran() {
        geometry();
        appearance();

    }

    private void geometry() {
        //ImageIcon warning = MagasinImage.coffee;
        this.fontNoir = MagasinImage.fontNoir;
        //button = new JButton(warning);
        vectorPixels = new Vector<Pixel>(Config.getConfig().getNombreTotalLed()); //initial size, better performance when adding elements

        fillVector();
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

        int nbLedsHaut = Config.getConfig().getNbLed(Config.NORTH);
        int nbLedsBas = Config.getConfig().getNbLed(Config.SOUTH);
        int nbLedsGauche = Config.getConfig().getNbLed(Config.EAST);
        int nbLedsDroite = Config.getConfig().getNbLed(Config.WEST);

        // Rectangle
        g2d.translate(MARGE, MARGE);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(0, 0, LONGUEUR - MARGE, LARGEUR - MARGE);

        int diametrePixel = 10;
        double espaceEntreLeds = (double) (LONGUEUR - 2 * MARGE - nbLedsHaut * diametrePixel -2) / (double) (nbLedsHaut);
        double espaceEntreLedsLargeur = (double) (LARGEUR - 2 * MARGE - nbLedsDroite * diametrePixel) / (double) (nbLedsDroite);

        g2d.translate(espaceEntreLeds, -MARGE / 2);
        for(int j = 0; j < 2; j++){
        for (int i = 0; i < nbLedsHaut; i++) {
            g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));
            g2d.translate(espaceEntreLeds + diametrePixel, 0);
        }

        g2d.translate(MARGE / 2 + diametrePixel, 0);
        
        g2d.rotate(Math.PI / 2);

        g2d.translate(espaceEntreLedsLargeur + MARGE / 2, 0);

        for (int i = 0; i < nbLedsGauche; i++) {
            g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));
            g2d.translate(espaceEntreLedsLargeur + diametrePixel, 0);
        }
        
        g2d.translate(diametrePixel + MARGE / 2, 0);
        g2d.rotate(Math.PI / 2);
        g2d.translate(espaceEntreLeds + MARGE / 2, 0);
        }

    }

    public void updateDisplay() {

    }

    public Vector getVectorPixel() {
        return this.vectorPixels;
    }
    
    public synchronized void setPixelAt(int index, Pixel pixel) throws ArrayIndexOutOfBoundsException{
        if(index >= vectorPixels.size())
            throw new ArrayIndexOutOfBoundsException("index " + index + " too big for vectorPixel (" + vectorPixels.size() + " elements)");
        
        vectorPixels.set(index, pixel);
    }

    private void appearance() {
        setPreferredSize(new Dimension(LONGUEUR, LARGEUR));
    }

    private void fillVector() {
        Config config = Config.getConfig();
        int nbLeds = config.getNombreTotalLed();

        for (int i = 0; i < nbLeds; i++) {
            this.vectorPixels.add(new Pixel(0, 0, 0));
        }
    }

}
