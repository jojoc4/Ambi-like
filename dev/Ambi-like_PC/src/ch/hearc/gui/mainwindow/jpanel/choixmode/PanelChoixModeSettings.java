/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.choixmode;

import ch.hearc.Config;
import ch.hearc.Main;
import ch.hearc.compute.Computation_I;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author julien.chappuis1
 */
public class PanelChoixModeSettings extends JPanel {

    private PanelChoixModeCouleurs panelChoixModeCouleurs;

    private ButtonGroup group;
    private JRadioButton reactifRB;
    private JRadioButton couleurFixeRB;
    private JRadioButton modePersonnalise1;
    private JRadioButton modePersonnalise2;
    private JRadioButton modePersonnalise3;

    private Config configFile;

    public PanelChoixModeSettings() {

        geometry();
        control();
        appearance();
    }

    private void geometry() {
        reactifRB = new JRadioButton("Réactif (Ambi-light)");
        couleurFixeRB = new JRadioButton("Couleur fixe : ");
        modePersonnalise1 = new JRadioButton("Mode personnalisé 1");
        modePersonnalise2 = new JRadioButton("Mode personnalisé 2");
        modePersonnalise3 = new JRadioButton("Mode personnalisé 3");

        panelChoixModeCouleurs = new PanelChoixModeCouleurs(this);

        group = new ButtonGroup();

        group.add(reactifRB);
        group.add(couleurFixeRB);
        group.add(modePersonnalise1);
        group.add(modePersonnalise2);
        group.add(modePersonnalise3);

        Box boxVertical = Box.createVerticalBox();
        boxVertical.add(reactifRB);
        boxVertical.add(couleurFixeRB);
        boxVertical.add(panelChoixModeCouleurs);
        boxVertical.add(modePersonnalise1);
        boxVertical.add(modePersonnalise2);
        boxVertical.add(modePersonnalise3);

        setLayout(new BorderLayout());

        add(boxVertical, BorderLayout.CENTER);

        configFile = Config.getConfig();

    }

    private void control() {
        reactifRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelChoixModeCouleurs.setVisible(false);
                if (reactifRB.isSelected()) {
                    configFile.setMode(Computation_I.MODE_AMBILIGHT);
                }
            }
        });
        couleurFixeRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (couleurFixeRB.isSelected()) {
                    panelChoixModeCouleurs.setVisible(true);
                    configFile.setMode(Computation_I.MODE_FIXE);
                }
            }
        });
        modePersonnalise1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelChoixModeCouleurs.setVisible(false);
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        modePersonnalise2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelChoixModeCouleurs.setVisible(false);
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

            }
        });
        modePersonnalise3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelChoixModeCouleurs.setVisible(false);
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    private void appearance() {
        reactifRB.setSelected(true);
        modePersonnalise1.setVisible(false);
        modePersonnalise2.setVisible(false);
        modePersonnalise3.setVisible(false);
    }
}
