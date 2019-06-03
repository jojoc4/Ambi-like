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
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author julien.chappuis1
 */
public class PanelChoiceModeSettings extends JPanel {

    private PanelChoiceModeColors panelChoiceModeColors;
    private Box boxVertical;
    private ButtonGroup group;
    private JRadioButton rbAmbilight;
    private JRadioButton rbFixedColor;
    private JRadioButton rbCustomMode1;
    private JRadioButton rbCustomMode2;
    private JRadioButton rbCustomMode3;

    private List<JRadioButton> listRadioButton;
    private JButton buttonBrowse;

    private File[] listFile;
    private String folderPath;

    private Config configFile;

    public PanelChoiceModeSettings() {

        geometry();
        control();
        appearance();

    }

    private void geometry() {
        rbAmbilight = new JRadioButton("Réactif (Ambi-light)");
        rbFixedColor = new JRadioButton("Couleur fixe : ");
//        rbCustomMode1 = new JRadioButton("Mode personnalisé 1");
//        rbCustomMode2 = new JRadioButton("Mode personnalisé 2");
//        rbCustomMode3 = new JRadioButton("Mode personnalisé 3");

        panelChoiceModeColors = new PanelChoiceModeColors(this);

        group = new ButtonGroup();
        buttonBrowse = new JButton("Choisir dossier mode perso");

        group.add(rbAmbilight);
        group.add(rbFixedColor);
//        group.add(rbCustomMode1);
//        group.add(rbCustomMode2);
//        group.add(rbCustomMode3);

        boxVertical = Box.createVerticalBox();
        boxVertical.add(rbAmbilight);
        boxVertical.add(rbFixedColor);
        boxVertical.add(panelChoiceModeColors);
//        boxVertical.add(rbCustomMode1);
//        boxVertical.add(rbCustomMode2);
//        boxVertical.add(rbCustomMode3);
        boxVertical.add(buttonBrowse);

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
//        rbCustomMode1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                panelChoiceModeColors.setVisible(false);
//                configFile.setPersoModeFile(listFile[0].getAbsolutePath());
//                Main.setTempMode(Computation_I.MODE_PERSO);
//            }
//        });
//        rbCustomMode2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                panelChoiceModeColors.setVisible(false);
//                configFile.setPersoModeFile(listFile[1].getAbsolutePath());
//                Main.setTempMode(Computation_I.MODE_PERSO);
//
//            }
//        });
//        rbCustomMode3.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                panelChoiceModeColors.setVisible(false);
//                configFile.setPersoModeFile(listFile[2].getAbsolutePath());
//                Main.setTempMode(Computation_I.MODE_PERSO);
//            }
//        });

        buttonBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser;
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Titre");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File f = chooser.getSelectedFile();
                    folderPath = f.getAbsolutePath();
                } else {
                    System.out.println("No Selection ");
                }
                displayModePerso();
            }
        });
    }

    private void appearance() {
        rbAmbilight.setSelected(true);
    }

    private void displayModePerso() {
        File folder = new File(folderPath);
        this.listFile = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".amm");
            }
        });
        listRadioButton = new ArrayList<JRadioButton>();

        for (int i = 0; i < listFile.length; i++) {
            listRadioButton.add(new JRadioButton(ModePersonnalise.getMode(listFile[i].toString()).getName()));
            String path = listFile[i].getAbsolutePath();
            listRadioButton.get(i).setVisible(true);
            group.add(listRadioButton.get(i));

            boxVertical.add(listRadioButton.get(i));
            listRadioButton.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panelChoiceModeColors.setVisible(false);
                    configFile.setPersoModeFile(path);
                    Main.setTempMode(Computation_I.MODE_PERSO);
                }
            });
        }
        repaint();

//        if (listRadioButton.size() > 0) {
//            if (listFile.length > 0) {
//                rbCustomMode1
//                rbCustomMode1.setText((ModePersonnalise.getMode(listFile[0].toString()).getName()));
//            }
//            if (listFile.length > 1) {
//                rbCustomMode2.setVisible(true);
//                rbCustomMode2.setText((ModePersonnalise.getMode(listFile[1].toString()).getName()));
//            }
//            if (listFile.length > 2) {
//                rbCustomMode3.setVisible(true);
//                rbCustomMode3.setText((ModePersonnalise.getMode(listFile[2].toString()).getName()));
//            }
//        } else {
//            rbCustomMode1.setVisible(false);
//            rbCustomMode2.setVisible(false);
//            rbCustomMode3.setVisible(false);
//            rbCustomMode1.setSelected(false);
//            rbCustomMode2.setSelected(false);
//            rbCustomMode3.setSelected(false);
//            rbFixedColor.setSelected(false);
//            rbAmbilight.doClick();
//            
//            
//        }
    }
}
