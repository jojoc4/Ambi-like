/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.mainwindow.choixmode;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author julien.chappuis1
 */
public class PanelChoixModeCouleurs extends JPanel {
    public PanelChoixModeCouleurs(){
        geometry();
        control();
        appearance();
    }
    
    private void geometry(){
        labelRouge = new JLabel("Rouge : ");
        jTextFieldRouge = new JTextField();
        jSliderRouge = new JSlider();
        
        labelBleu = new JLabel("Bleu : ");
        jTextFieldBleu = new JTextField();
        jSliderBleu = new JSlider();
        
        labelVert = new JLabel("Vert : ");
        jTextFieldVert = new JTextField();
        jSliderVert = new JSlider();
        
        Box boxHorizontalRouge = Box.createHorizontalBox();
        boxHorizontalRouge.add(labelRouge);
        boxHorizontalRouge.add(jTextFieldRouge);
        boxHorizontalRouge.add(jSliderRouge);
        
        Box boxHorizontalBleu = Box.createHorizontalBox();
        boxHorizontalBleu.add(labelBleu);
        boxHorizontalBleu.add(jTextFieldBleu);
        boxHorizontalBleu.add(jSliderBleu);
        
        Box boxHorizontalVert = Box.createHorizontalBox();
        boxHorizontalVert.add(labelVert);
        boxHorizontalVert.add(jTextFieldVert);
        boxHorizontalVert.add(jSliderVert);
        
        Box boxVertical = Box.createVerticalBox();
        boxVertical.add(boxHorizontalRouge);
        boxVertical.add(boxHorizontalBleu);
        boxVertical.add(boxHorizontalVert);
        
        setLayout(new BorderLayout());
        add(boxVertical, BorderLayout.CENTER);
        
    }
    
    private void control(){
        jSliderRouge.addChangeListener(addListener(jTextFieldRouge,jSliderRouge));
        jSliderVert.addChangeListener(addListener(jTextFieldVert,jSliderVert));
        jSliderBleu.addChangeListener(addListener(jTextFieldBleu,jSliderBleu));
    }
    
    private ChangeListener addListener(JTextField textField,JSlider slider){
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textField.setText(Integer.toString(slider.getValue()));
            }
        }; 
    }
    private void appearance(){
        jSliderRouge.setMaximum(255);
        jSliderRouge.setMinimum(0);
        jSliderRouge.setValue(127);
        
        jSliderBleu.setMaximum(255);
        jSliderBleu.setMinimum(0);
        jSliderBleu.setValue(127);
        
        jSliderVert.setMaximum(255);
        jSliderVert.setMinimum(0);
        jSliderVert.setValue(127);
    }
    
    private JLabel labelRouge;
    private JTextField jTextFieldRouge;
    private JSlider jSliderRouge;
    
    private JLabel labelBleu;
    private JTextField jTextFieldBleu;
    private JSlider jSliderBleu;
    
    private JLabel labelVert;
    private JTextField jTextFieldVert;
    private JSlider jSliderVert;
}
