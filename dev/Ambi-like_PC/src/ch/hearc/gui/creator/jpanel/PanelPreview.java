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
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
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
    private static final double DIAMETRE = 10d;

    private Vector<Pixel> vectorLEDs;
    private Vector<Ellipse2D> vectorEllipses;
    private Graphics2D g2d;
    private PanelColorChooser colorChooser;
    private BufferedImage img;

    public PanelPreview(PanelColorChooser colorChooser) {
        this.colorChooser = colorChooser;
        vectorLEDs = new Vector<Pixel>(Config.getConfig().getNbLedTotal()); //initial size, better performance when adding elements
        vectorEllipses = new Vector<Ellipse2D>(Config.getConfig().getNbLedTotal());
        
        fillVectors();
        
        Computation_I c = new Computation_fixedColor(new TestSender(), new Pixel(0,0,0));
        c.stopComputation();
        img = c.getImage();
        c = null;
        
        geometry();
        control();
        appearance();
    }

    private void geometry() {
        
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

        createDisplay();

        g2d.setTransform(backup);
    }

    public void createDisplay() {
        int nbLedsTop = Config.getConfig().getNbLed(Config.NORTH);
        int nbLedsBottom = Config.getConfig().getNbLed(Config.SOUTH);
        int nbLedsLeft = Config.getConfig().getNbLed(Config.WEST);
        int nbLedsRight = Config.getConfig().getNbLed(Config.EAST);
        
        AffineTransform transform = g2d.getTransform();
        // Rectangle
        //g2d.translate(MARGIN, MARGIN);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(MARGIN, MARGIN, WIDTH, HEIGHT);
        g2d.drawImage(img, MARGIN, MARGIN, WIDTH, HEIGHT, this);
        
        double leftSpacing = ((HEIGHT ) - (nbLedsLeft * DIAMETRE)) / nbLedsLeft;
        double topSpacing = ((WIDTH ) - (nbLedsTop * DIAMETRE)) / nbLedsTop;
        double rightSpacing = ((HEIGHT ) - (nbLedsRight * DIAMETRE)) / nbLedsRight;
        double bottomSpacing = ((WIDTH ) - (nbLedsBottom * DIAMETRE)) / nbLedsBottom;
        
        int ellipseIndex = 0;
        
        g2d.setTransform(transform); //restore
        
        //LEFT
//        g2d.translate(MARGIN/2, HEIGHT - MARGIN);
//        g2d.rotate(-Math.PI / 2);
//        g2d.translate(leftSpacing/2, 0);
        
        double x = MARGIN/2 - DIAMETRE/2;
        double y = MARGIN + HEIGHT - leftSpacing/2 - DIAMETRE;
        
        for(int i=0; i<nbLedsLeft; ++i){
            addEllipse(ellipseIndex++, x, y);
            //g2d.translate(leftSpacing + DIAMETRE, 0d);
            y -= (leftSpacing + DIAMETRE);
        }
        
        //TOP
//        g2d.setTransform(transform); //restore
//        g2d.translate(MARGIN, MARGIN/2);
//        g2d.translate(topSpacing/2, 0);
        x = MARGIN + topSpacing/2;
        y = MARGIN/2 - DIAMETRE/2;
        for(int i=0; i<nbLedsTop; ++i){
            addEllipse(ellipseIndex++, x, y);
            //g2d.translate(topSpacing + DIAMETRE, 0d);
            x += (topSpacing + DIAMETRE);
        }
        
        //RIGHT
//        g2d.setTransform(transform); //restore
//        g2d.translate(WIDTH - (MARGIN/2), MARGIN);
//        g2d.rotate(Math.PI / 2);
//        g2d.translate(rightSpacing/2, 0);
        x = WIDTH + MARGIN + (MARGIN/2) - DIAMETRE/2;
        y = MARGIN + rightSpacing/2;
        for(int i=0; i<nbLedsRight; ++i){
            addEllipse(ellipseIndex++, x, y);
            //g2d.translate(rightSpacing + DIAMETRE, 0d);
            y += (rightSpacing + DIAMETRE);
        }
        
        //BOTTOM
//        g2d.setTransform(transform); //restore
//        g2d.translate(WIDTH - MARGIN, HEIGHT - (MARGIN/2));
//        g2d.rotate(-Math.PI);
//        g2d.translate(bottomSpacing/2, 0);
        x = WIDTH + MARGIN - bottomSpacing/2 - DIAMETRE;
        y = HEIGHT + MARGIN + (MARGIN/2) - DIAMETRE/2;
        for(int i=0; i<nbLedsBottom; ++i){
            addEllipse(ellipseIndex++, x, y);
            //g2d.translate((bottomSpacing + DIAMETRE), 0d);
            x -= (bottomSpacing + DIAMETRE);
        }

        g2d.setTransform(transform); //restore
        
        System.out.println(vectorEllipses.size());
        
//        for(Ellipse2D e : vectorEllipses){
//            System.out.println(e.getBounds2D());
//        }
//        System.out.println("okay");
        
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
//                System.out.println(e.getPoint());
                updateLEDColor(e.getPoint());
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    
    private void addEllipse(int index, double x, double y) {
        Ellipse2D ellipse = new Ellipse2D.Double(x, y, DIAMETRE, DIAMETRE);
        g2d.setColor(vectorLEDs.elementAt(index).getColor());
        g2d.fill(ellipse);
        vectorEllipses.set(index, ellipse);
    }
    
    private void updateLEDColor(Point2D p){
        int i=0;
//        System.out.println("Entré!");
//        System.out.println(p);
        for(Ellipse2D ellipse : vectorEllipses){
            if(ellipse.contains(p)){
//                System.out.println("entré");
                double x = ellipse.getX();
                double y = ellipse.getCenterY();
                double w = ellipse.getWidth();
                double h = ellipse.getHeight();
                
                Color c = colorChooser.getColor();
                
                vectorLEDs.elementAt(i).setColor(c);
                //g2d.setColor(c);
                
                //g2d.draw(ellipse);
                repaint();
                break;
            }
            ++i;
        }
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


    private void fillVectors() {
        int nbLeds = Config.getConfig().getNbLedTotal();

        for (int i = 0; i < nbLeds; ++i) {
            this.vectorLEDs.add(new Pixel(0, 0, 0));
            this.vectorEllipses.add(new Ellipse2D.Double());
        }
    }

}
