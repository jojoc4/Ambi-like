/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.Preview;

import ch.hearc.Config;
import ch.hearc.ModePersonnalise;
import ch.hearc.Pixel;
import ch.hearc.compute.Computation_Ambilight;
import ch.hearc.compute.Computation_I;
import ch.hearc.compute.Computation_fixedColor;
import ch.hearc.compute.Computation_perso;
import ch.hearc.compute.senders.PreviewSender;
import ch.hearc.compute.senders.TestSender;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

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

    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    private static final int MARGIN = 40;

    private Vector<Pixel> vectorPixels;
    private Graphics2D g2d;

    private PreviewSender previewSender;
    private Computation_I computation;
    private Thread t;
    private int lastMode;

    private int nbRefresh = 0;
    
    private Config config;
    
    private Pixel previousPixel;

    public PanelPreviewScreen() {
        config = Config.getConfig();
        previousPixel = new Pixel(0,0,0);
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
        //ImageIcon warning = MagasinImage.coffee;
        //button = new JButton(warning);
        vectorPixels = new Vector<Pixel>(Config.getConfig().getNombreTotalLed()); //initial size, better performance when adding elements
        fillVector();
        startComputation();
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
       
        changeComputation();
        updateDisplay(g2d);

        g2d.setTransform(backup);

    }

    public void updateDisplay(Graphics2D g2d) {
        int nbLedsTop = Config.getConfig().getNbLed(Config.NORTH);
        int nbLedsLeft = Config.getConfig().getNbLed(Config.EAST);
        int nbLedsRight = Config.getConfig().getNbLed(Config.WEST);

        // Rectangle
        g2d.translate(MARGIN, MARGIN);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(0, 0, WIDTH - 2 * MARGIN, HEIGHT - 2 * MARGIN);
        g2d.drawImage(computation.getImage(), 1,1,WIDTH - 2 * MARGIN-1,HEIGHT - 2 * MARGIN-1, this);

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
            if (nbRefresh % 100 == 0 && config.getMode() == Computation_I.MODE_AMBILIGHT) {
                nbRefresh = 0;
                repaint();
            }
            if(Computation_I.MODE_FIXE == config.getMode() && nbRefresh % 100 == 0){
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

    private void changeComputation() {
        if(config.getMode() != this.lastMode || config.getColor()[0] != this.previousPixel.getRed()
                || config.getColor()[1] != this.previousPixel.getGreen()
                || config.getColor()[2] != this.previousPixel.getBlue()){
            this.computation.stopComputation();
            this.startComputation();
        }
    }
    public void stopComputation(){
        this.computation.stopComputation();
    }
}
