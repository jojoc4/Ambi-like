/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.mainwindow.previsualisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author julien.chappuis1
 */
public class PanelPrevisualisationEcran extends JPanel {
    public PanelPrevisualisationEcran(){
        geometry();
        control();
        appearance();
        
    }
    
   private void geometry()
	{
        //ImageIcon warning = MagasinImage.coffee;
	this.coffee = MagasinImage.coffee;
        //button = new JButton(warning);

	}

	@Override
	protected void paintComponent(Graphics g)
	{
            super.paintComponent(g);

            Graphics2D g2D = (Graphics2D)g;

            AffineTransform transform = g2D.getTransform(); //sauvegarde
            Color color = g2D.getColor(); //sauvegarde
            Font font = g2D.getFont(); //sauvegarde

            dessiner(g2D);

            g2D.setFont(font); //restore
            g2D.setColor(color); //restore
            g2D.setTransform(transform); //restore
	}

	private void dessiner(Graphics2D g2d)
	{
            g2d.drawImage(this.coffee.getImage(), 0, 0, this);
	}
	private void control()
		{
		// rien
		}

	private void appearance()
		{
                setPreferredSize(new Dimension(200,200));
                }
        private ImageIcon coffee;
        
}