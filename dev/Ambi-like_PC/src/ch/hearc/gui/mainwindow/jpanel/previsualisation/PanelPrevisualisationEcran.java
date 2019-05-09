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
    private static final int LONGUEUR = 400;
    private static final int LARGEUR = 300;

    private Vector<Pixel> vectorPixels;

    public PanelPrevisualisationEcran() {
        geometry();
        appearance();

    }

    private void geometry() {
        //ImageIcon warning = MagasinImage.coffee;
        this.fontNoir = MagasinImage.fontNoir;
        //button = new JButton(warning);
        vectorPixels = new Vector<Pixel>();

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

        int longueurEcran = LONGUEUR - 10;
        int largeurEcran = LARGEUR - 10;

        double diametreCercle = (double) longueurEcran / (double) nbLedsHaut / 2.;
        g2d.setStroke(new BasicStroke(10));
        g2d.drawRect(10, (int) diametreCercle + 10, longueurEcran, longueurEcran);

        g2d.setColor(Color.RED);

        g2d.translate(20, 0);

        for (int i = 0; i < nbLedsHaut; i++) {
            g2d.fill(new Ellipse2D.Double(0, 0, diametreCercle, diametreCercle));
            g2d.draw(new Ellipse2D.Double(0, 0, diametreCercle, diametreCercle));
            g2d.translate(diametreCercle * 2, 0);
        }
        g2d.translate(diametreCercle, 0);
        g2d.rotate(Math.PI / 2);
        g2d.translate(diametreCercle, 0);

        for (int i = 0; i < nbLedsDroite; i++) {
            g2d.fill(new Ellipse2D.Double(0, 0, diametreCercle, diametreCercle));
            g2d.draw(new Ellipse2D.Double(0, 0, diametreCercle, diametreCercle));
            g2d.translate(diametreCercle * 2, 0);
        }
        g2d.translate(2 * diametreCercle, 0);

        g2d.rotate(Math.PI / 2);

        for (int i = 0; i < nbLedsDroite; i++) {
            g2d.fill(new Ellipse2D.Double(0, 0, diametreCercle, diametreCercle));
            g2d.draw(new Ellipse2D.Double(0, 0, diametreCercle, diametreCercle));
            g2d.translate(diametreCercle * 2, 0);
        }
    }

    public void updateDisplay() {

    }

    public Vector getVectorPixel() {
        return this.vectorPixels;
    }

    private void appearance() {
        setPreferredSize(new Dimension(LONGUEUR + 300, LARGEUR + 300));
    }

    private void fillVector() {
        Config config = Config.getConfig();
        int nbLeds = config.getNombreTotalLed();
        
        for(int i = 0; i < nbLeds; i++){
            this.vectorPixels.add(new Pixel(0,0,0));
        }  
    }

}
