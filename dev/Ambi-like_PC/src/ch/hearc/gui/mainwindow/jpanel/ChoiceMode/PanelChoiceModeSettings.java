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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Describe PanelChoiceModeSettings content
 *
 * @version 1.0
 * @since 18.04.2019
 * @author Julien Chappuis
 */
public class PanelChoiceModeSettings extends JPanel {

    private PanelChoiceModeColors panelChoiceModeColors;
    private Box boxVertical;
    private ButtonGroup group;
    private JRadioButton rbAmbilight;
    private JRadioButton rbFixedColor;

    private JLabel labelModesPerso;
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

        panelChoiceModeColors = new PanelChoiceModeColors(this);

        group = new ButtonGroup();
        buttonBrowse = new JButton("Choisir emplacement des modes");
        labelModesPerso = new JLabel("Modes personnalisés :");
        listRadioButton = new ArrayList<JRadioButton>();

        group.add(rbAmbilight);
        group.add(rbFixedColor);

        boxVertical = Box.createVerticalBox();
        boxVertical.add(rbAmbilight);
        boxVertical.add(rbFixedColor);
        boxVertical.add(panelChoiceModeColors);
        boxVertical.add(Box.createVerticalStrut(20));
        boxVertical.add(labelModesPerso);
        boxVertical.add(Box.createVerticalStrut(10));
        boxVertical.add(buttonBrowse);
        boxVertical.add(Box.createVerticalStrut(10));

        setLayout(new BorderLayout());
        add(boxVertical, BorderLayout.CENTER);

        configFile = Config.getConfig();

        folderPath = "./modes";
        displayModePerso();
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

    /**
     * Create a specific number of radio buttons according to the list of files
     * that contains a PersoMode.
     */
    private void displayModePerso() {
        for (JRadioButton jrb : listRadioButton) {
            boxVertical.remove(jrb);
        }

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

        revalidate();
        repaint();
    }
}
