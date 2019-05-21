/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.creator.jpanel;

import ch.hearc.Config;
import ch.hearc.Pixel;
import ch.hearc.compute.Computation_I;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
    private static final double DIAMETER = 10d;

    private Vector<Pixel> vectorLEDs;
    private Vector<Ellipse2D> vectorEllipses;
    private Graphics2D g2d;
    private final PanelColorChooser colorChooser;
    private BufferedImage img;

    public PanelPreview(PanelColorChooser colorChooser) {
        this.colorChooser = colorChooser;
        vectorLEDs = new Vector<Pixel>(Config.getConfig().getNbLedTotal()); //initial size, better performance when adding elements
        vectorEllipses = new Vector<Ellipse2D>(Config.getConfig().getNbLedTotal());
        
        fillVectors();
        
        img = Computation_I.printScreen();
        
        geometry();
        control();
        appearance();
        System.out.println("PanelPreview - width: " + getWidth() + " height: " + getHeight() + " x: " + getX() + " y: " + getY() + " visible: " + isVisible() + " valid: " + isValid());
    }

    private void geometry() {
        
    }

    private void control() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                updateLEDColor(e.getPoint());
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    
    private void appearance() {
        setPreferredSize(new Dimension(WIDTH + 2*MARGIN, HEIGHT + 2*MARGIN));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        this.g2d = g2D;
        createDisplay();
    }

    public void createDisplay() {
        int nbLedsTop = Config.getConfig().getNbLed(Config.NORTH);
        int nbLedsBottom = Config.getConfig().getNbLed(Config.SOUTH);
        int nbLedsLeft = Config.getConfig().getNbLed(Config.WEST);
        int nbLedsRight = Config.getConfig().getNbLed(Config.EAST);
        
        // Draw the screen
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(MARGIN, MARGIN, WIDTH, HEIGHT);
        g2d.drawImage(img, MARGIN, MARGIN, WIDTH, HEIGHT, this);
        
        double leftSpacing = ((HEIGHT ) - (nbLedsLeft * DIAMETER)) / nbLedsLeft;
        double topSpacing = ((WIDTH ) - (nbLedsTop * DIAMETER)) / nbLedsTop;
        double rightSpacing = ((HEIGHT ) - (nbLedsRight * DIAMETER)) / nbLedsRight;
        double bottomSpacing = ((WIDTH ) - (nbLedsBottom * DIAMETER)) / nbLedsBottom;
        
        int ellipseIndex = 0;
        
        //LEFT
        double x = MARGIN/2 - DIAMETER/2;
        double y = MARGIN + HEIGHT - leftSpacing/2 - DIAMETER;
        for(int i=0; i<nbLedsLeft; ++i){
            addEllipse(ellipseIndex++, x, y);
            y -= (leftSpacing + DIAMETER);
        }
        
        //TOP
        x = MARGIN + topSpacing/2;
        y = MARGIN/2 - DIAMETER/2;
        for(int i=0; i<nbLedsTop; ++i){
            addEllipse(ellipseIndex++, x, y);
            x += (topSpacing + DIAMETER);
        }
        
        //RIGHT
        x = WIDTH + MARGIN + (MARGIN/2) - DIAMETER/2;
        y = MARGIN + rightSpacing/2;
        for(int i=0; i<nbLedsRight; ++i){
            addEllipse(ellipseIndex++, x, y);
            y += (rightSpacing + DIAMETER);
        }
        
        //BOTTOM
        x = WIDTH + MARGIN - bottomSpacing/2 - DIAMETER;
        y = HEIGHT + MARGIN + (MARGIN/2) - DIAMETER/2;
        for(int i=0; i<nbLedsBottom; ++i){
            addEllipse(ellipseIndex++, x, y);
            x -= (bottomSpacing + DIAMETER);
        }
    }
    
    private void addEllipse(int index, double x, double y) {
        Ellipse2D ellipse = new Ellipse2D.Double(x, y, DIAMETER, DIAMETER);
        g2d.setColor(vectorLEDs.elementAt(index).getColor());
        g2d.fill(ellipse);
        vectorEllipses.set(index, ellipse);
    }
    
    private void updateLEDColor(Point2D p){
        int i=0;
        for(Ellipse2D ellipse : vectorEllipses){
            if(ellipse.contains(p)){
                double x = ellipse.getX();
                double y = ellipse.getCenterY();
                double w = ellipse.getWidth();
                double h = ellipse.getHeight();
                
                vectorLEDs.elementAt(i).setColor(colorChooser.getColor());
                
                repaint();
                break;
            }
            ++i;
        }
    }

    public Vector<Pixel> getVectorPixel() {
        return this.vectorLEDs;
    }

    private void fillVectors() {
        int nbLeds = Config.getConfig().getNbLedTotal();

        for (int i = 0; i < nbLeds; ++i) {
            this.vectorLEDs.add(new Pixel(0, 0, 0));
            this.vectorEllipses.add(new Ellipse2D.Double());
        }
    }

}
