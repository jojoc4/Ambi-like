/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow;

import ch.hearc.gui.mainwindow.jpanel.mainwindow.PanelMainWindow;
import javax.swing.JFrame;

/**
 *
 * @author julien.chappuis1
 */
public class FrameMainWindow extends JFrame{
    
    public FrameMainWindow(){
        
        geometry();
        control();
        appearance();
        
    }
    
    private void geometry()
    {
        panelMainWindow = new PanelMainWindow();
        add(panelMainWindow);
    }
    
    private void control(){
        
    }
    
    private void appearance()
    {    
        setTitle("Demo");
	setSize(900, 350);
	setLocationRelativeTo(null); // frame centrer
	setVisible(true); // last!
    }

    private PanelMainWindow panelMainWindow;
}
