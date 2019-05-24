/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.gui.mainwindow.jpanel.ChoiceMode;

import ch.hearc.Config;
import ch.hearc.Main;
import ch.hearc.ModePersonnalise;
import ch.hearc.compute.Computation_I;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 *
 * @author julien.chappuis1
 */
public class PanelChoiceModeSettings extends JPanel {

    private PanelChoiceModeColors panelChoiceModeColors;

    private ButtonGroup group;
    private JRadioButton rbAmbilight;
    private JRadioButton rbFixedColor;
    private JRadioButton rbCustomMode1;
    private JRadioButton rbCustomMode2;
    private JRadioButton rbCustomMode3;

    private File[] listFile;

    private Config configFile;

    public PanelChoiceModeSettings() {

        geometry();
        control();
        appearance();

        displayModePerso();
    }

    private void geometry() {
        rbAmbilight = new JRadioButton("Réactif (Ambi-light)");
        rbFixedColor = new JRadioButton("Couleur fixe : ");
        rbCustomMode1 = new JRadioButton("Mode personnalisé 1");
        rbCustomMode2 = new JRadioButton("Mode personnalisé 2");
        rbCustomMode3 = new JRadioButton("Mode personnalisé 3");

        panelChoiceModeColors = new PanelChoiceModeColors(this);

        group = new ButtonGroup();

        group.add(rbAmbilight);
        group.add(rbFixedColor);
        group.add(rbCustomMode1);
        group.add(rbCustomMode2);
        group.add(rbCustomMode3);

        Box boxVertical = Box.createVerticalBox();
        boxVertical.add(rbAmbilight);
        boxVertical.add(rbFixedColor);
        boxVertical.add(panelChoiceModeColors);
        boxVertical.add(rbCustomMode1);
        boxVertical.add(rbCustomMode2);
        boxVertical.add(rbCustomMode3);

        setLayout(new BorderLayout());

        add(boxVertical, BorderLayout.CENTER);

        configFile = Config.getConfig();

    }

    private void control() {
        rbAmbilight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelChoiceModeColors.setVisible(false);
                if (rbAmbilight.isSelected()) {
                    Main.setTempMode(Computation_I.MODE_AMBILIGHT);
                    //configFile.setMode(Computation_I.MODE_AMBILIGHT);
                }
            }
        });
        rbFixedColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (rbFixedColor.isSelected()) {
                    panelChoiceModeColors.setVisible(true);
                    Main.setTempMode(Computation_I.MODE_FIXE);
                    //configFile.setMode(Computation_I.MODE_FIXE);

                }
            }
        });
        rbCustomMode1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelChoiceModeColors.setVisible(false);
                configFile.setPersoModeFile(listFile[0].getAbsolutePath());
                Main.setTempMode(Computation_I.MODE_PERSO);
            }
        });
        rbCustomMode2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelChoiceModeColors.setVisible(false);
                configFile.setPersoModeFile(listFile[1].getAbsolutePath());
                Main.setTempMode(Computation_I.MODE_PERSO);

            }
        });
        rbCustomMode3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelChoiceModeColors.setVisible(false);
                configFile.setPersoModeFile(listFile[2].getAbsolutePath());
                Main.setTempMode(Computation_I.MODE_PERSO);
            }
        });
    }

    private void appearance() {
        rbAmbilight.setSelected(true);
        rbCustomMode1.setVisible(false);
        rbCustomMode2.setVisible(false);
        rbCustomMode3.setVisible(false);
    }

    private void displayModePerso() {
        this.listFile = ModePersonnalise.getListMode();

        if (listFile != null) {
            if (listFile.length > 0) {
                rbCustomMode1.setVisible(true);
            }
            if (listFile.length > 1) {
                rbCustomMode2.setVisible(true);
            }
            if (listFile.length > 2) {
                rbCustomMode3.setVisible(true);
            }
        }

    }
}
