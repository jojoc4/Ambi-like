/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.previsualisation;

import ch.hearc.Config;
import ch.hearc.ModePerso;
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
public class PanelPrevisualisationEcran extends JPanel {

    private ImageIcon fontNoir;
    private static final int LONGUEUR = 500;
    private static final int LARGEUR = 400;
    private static final int MARGE = 40;

    private Vector<Pixel> vectorPixels;
    private Graphics2D g2d;

    private PrevisualisationSender previsualisationSender;
    private Computation_I computation;
    
    private int nbRefresh = 0;

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

        dessiner(g2D);

        g2D.setFont(font); //restore
        g2D.setColor(color); //restore
        g2D.setTransform(transform); //restore
    }

    private void dessiner(Graphics2D g2d) {

        AffineTransform backup = g2d.getTransform();

        //this.g2d = g2d;

        updateDisplay(g2d);

        g2d.setTransform(backup);

    }
    public void updateDisplay(Graphics2D g2d) {

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
        int index = nbLedsGauche;
        for (int j = 0; j < 2; j++) {
            
            for (int i = 0; i < nbLedsHaut; i++) {
                Pixel pixel = this.vectorPixels.get(index);
                g2d.setColor(pixel.getColor());
                index++;
                index %= Config.getConfig().getNbLedTotal();
                g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));
                g2d.translate(espaceEntreLeds + diametrePixel, 0.0);
            }

            g2d.translate(demiMarge, demiMarge);
            g2d.rotate(Math.PI / 2);
            g2d.translate(espaceEntreLedsLargeur, 0.0);

            for (int i = 0; i < nbLedsGauche; i++) {
                Pixel pixel = this.vectorPixels.get(index);
                g2d.setColor(pixel.getColor());
                index %= Config.getConfig().getNbLedTotal();

                g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));
                g2d.translate(espaceEntreLedsLargeur + diametrePixel, 0.0);
                index++;
            }
            g2d.translate(demiMarge, demiMarge);
            g2d.rotate(Math.PI / 2);
            g2d.translate(espaceEntreLeds, 0.0);
            g2d.fill(new Ellipse2D.Double(0, 0, diametrePixel, diametrePixel));

        }
    }

    public Vector<Pixel> getVectorPixel() {
        return this.vectorPixels;
    }

    public synchronized void setPixelAt(int index, Pixel pixel) {
        if (index < vectorPixels.size()) {
            vectorPixels.set(index, pixel);
                nbRefresh++;
                if(nbRefresh % 100 == 0 ){
                    nbRefresh = 0;
                    repaint();
                }
        
            
        }
    }

    private void appearance() {
        setPreferredSize(new Dimension(LONGUEUR, LARGEUR));
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
        this.previsualisationSender = new PrevisualisationSender(this);
        switch (Config.getConfig().getMode()) {
            case Computation_I.MODE_AMBILIGHT:
                c = new Computation_Ambilight(previsualisationSender);
                break;
            case Computation_I.MODE_FIXE:
                c = new Computation_fixedColor(previsualisationSender, new Pixel(Config.getConfig().getColor()[0], Config.getConfig().getColor()[1], Config.getConfig().getColor()[3]));
                break;

            case Computation_I.MODE_PERSO:
                c = new Computation_perso(previsualisationSender, ModePerso.getMode(Config.getConfig().getPersoModeFile()));
                break;
            default:
                c = new Computation_Ambilight(previsualisationSender);
        }
        return c;
    }
}
