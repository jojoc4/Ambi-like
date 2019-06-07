package ch.hearc.gui.creator.jpanel;

import ch.hearc.Config;
import ch.hearc.Pixel;
import ch.hearc.compute.Computation_I;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import javax.swing.JPanel;

/**
 * Panel containing the preview of the current edited mode. Displays the screen and the LEDs' colors, according to modifications. <br>
 * Used in PanelPreviewButtons. <br>
 * This class is thread-safe!
 * 
 * @version 3.0.1
 * @since 06.06.2019
 * @author Téo Schaffner
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
    private final BufferedImage img;

    private int nbLedsTop;
    private int nbLedsBottom;
    private int nbLedsLeft;
    private int nbLedsRight;
    private int nbLedsTotal;

    private final ReentrantReadWriteLock rwLock;
    private final ReadLock rLock;
    private final WriteLock wLock;
    
    /**
     * Constructor
     * 
     * @param colorChooser PanelColorChooser from which to get the color to set to LEDs.
     */
    public PanelPreview(PanelColorChooser colorChooser) {
        this.colorChooser = colorChooser;

        rwLock = new ReentrantReadWriteLock(true);
        rLock = rwLock.readLock();
        wLock = rwLock.writeLock();

        setNbLeds(Config.getConfig().getNbLed());

        img = Computation_I.printScreen();

        control();
        appearance();
    }
    
    /**
     * Adds behaviour to the panel (Listeners).
     */
    private void control() {
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                updateLEDColorAtClick(e.getPoint());
            }
        });
    }
    
    /**
     * Sets the panel's appearance.
     */
    private void appearance() {
        setPreferredSize(new Dimension(WIDTH + 2 * MARGIN, HEIGHT + 2 * MARGIN));
    }
    
    /**
     * Uses Graphics2D to draw the preview of the LEDs on the panel. Thread-safe.
     * 
     * @param g Graphics object to use.
     */
    @Override
    protected void paintComponent(Graphics g) {
        rLock.lock();
        try {
            super.paintComponent(g);

            Graphics2D g2D = (Graphics2D) g;
            this.g2d = g2D;
            createDisplay();
        } finally {
            rLock.unlock();
        }
    }
    
    /**
     * Draws everything that needs to be drawn. 
     */
    private void createDisplay() {
        // Draw the screen
        g2d.setStroke(new BasicStroke(1));
        g2d.drawImage(img, MARGIN, MARGIN, WIDTH, HEIGHT, this);
        
        //Sapcing between LEDs on each side of the screen
        double leftSpacing = ((HEIGHT) - (nbLedsLeft * DIAMETER)) / nbLedsLeft;
        double topSpacing = ((WIDTH) - (nbLedsTop * DIAMETER)) / nbLedsTop;
        double rightSpacing = ((HEIGHT) - (nbLedsRight * DIAMETER)) / nbLedsRight;
        double bottomSpacing = ((WIDTH) - (nbLedsBottom * DIAMETER)) / nbLedsBottom;

        int ellipseIndex = 0;

        //LEFT
        double x = MARGIN / 2 - DIAMETER / 2; //x coordinate where to draw the LED
        double y = MARGIN + HEIGHT - leftSpacing / 2 - DIAMETER; //y coordinate
        for (int i = 0; i < nbLedsLeft; ++i) {
            addEllipse(ellipseIndex++, x, y);
            y -= (leftSpacing + DIAMETER); //move up for next LED
        }

        //TOP
        x = MARGIN + topSpacing / 2;
        y = MARGIN / 2 - DIAMETER / 2;
        for (int i = 0; i < nbLedsTop; ++i) {
            addEllipse(ellipseIndex++, x, y);
            x += (topSpacing + DIAMETER); //move right
        }

        //RIGHT
        x = WIDTH + MARGIN + (MARGIN / 2) - DIAMETER / 2;
        y = MARGIN + rightSpacing / 2;
        for (int i = 0; i < nbLedsRight; ++i) {
            addEllipse(ellipseIndex++, x, y);
            y += (rightSpacing + DIAMETER); //move down
        }

        //BOTTOM
        x = WIDTH + MARGIN - bottomSpacing / 2 - DIAMETER;
        y = HEIGHT + MARGIN + (MARGIN / 2) - DIAMETER / 2;
        for (int i = 0; i < nbLedsBottom; ++i) {
            addEllipse(ellipseIndex++, x, y);
            x -= (bottomSpacing + DIAMETER); //move left
        }
    }
    
    /**
     * Puts an Ellipse2D to the vector of LEDs, at given index, with the current color.
     * 
     * @param index index at which the Ellipse2D should be added
     * @param x x coordinate where to draw it
     * @param y y coordinate where to draw it
     */
    private void addEllipse(int index, double x, double y) {
        Ellipse2D ellipse = new Ellipse2D.Double(x, y, DIAMETER, DIAMETER);
        g2d.setColor(vectorLEDs.elementAt(index).getColor());
        g2d.fill(ellipse);
        vectorEllipses.set(index, ellipse);
    }

    /**
     * Updates the color of the LED that contains the point at given point's coordinates and triggers a redraw.
     * 
     * @param p Point2D that represents the coordintes at which to update the LED's color
     */
    private void updateLEDColorAtClick(Point2D p) {
        int i = 0;
        for (Ellipse2D ellipse : vectorEllipses) {
            if (ellipse.contains(p)) {

                vectorLEDs.elementAt(i).setColor(colorChooser.getColor());

                repaint();
                break;
            }
            ++i;
        }
    }

    /**
     * Gives the vector of Pixels. Thread-safe.
     * 
     * @return Vector of all the Pixels
     */
    public Vector<Pixel> getVectorPixel() {
        Vector<Pixel> v = null;

        rLock.lock();
        try {
            v = new Vector<Pixel>(this.vectorLEDs);
        } finally {
            rLock.unlock();
        }

        return v;
    }

    /**
     * Changes the number of LEDs and sets them all to default color (see fillVectors). Thread-safe.
     * 
     * @param nbLeds new number of LEDs
     */
    private void setNbLeds(int[] nbLeds) {
        wLock.lock();
        try {
            nbLedsTop = nbLeds[1];
            nbLedsBottom = nbLeds[3];
            nbLedsLeft = nbLeds[0];
            nbLedsRight = nbLeds[2];

            nbLedsTotal = nbLeds[0] + nbLeds[1] + nbLeds[2] + nbLeds[3];

            vectorLEDs = new Vector<Pixel>(nbLedsTotal); //initial size, better performance when adding elements
            vectorEllipses = new Vector<Ellipse2D>(nbLedsTotal);
            
            //fill new vectors with default values
            fillVectors();
        } finally {
            wLock.unlock();
        }
    }

    /**
     * Changes the complete vetor of Pixels and resets the vector of Ellipse2D and triggers a redraw. Thread-safe.
     * 
     * @param pixels new vector of Pixels
     * @param nbLedsBySide number of pixels on each side of the screen
     */
    public void setPixels(Vector<Pixel> pixels, int[] nbLedsBySide) {
        wLock.lock();
        try {
            setNbLeds(nbLedsBySide);
            this.vectorLEDs = pixels;
        } finally {
            wLock.unlock();
        }

        repaint();
    }

    /**
     * Fills both vectors with default Pixels (all are black by default). Thread-safe.
     */
    private void fillVectors() {
        wLock.lock();
        try {
            for (int i = 0; i < nbLedsTotal; ++i) {
                this.vectorLEDs.add(new Pixel(0, 0, 0));
                this.vectorEllipses.add(new Ellipse2D.Double());
            }
        } finally {
            wLock.unlock();
        }
    }

}
