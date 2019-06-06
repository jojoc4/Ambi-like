package ch.hearc;

import ch.hearc.compute.Computation_I;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Config singleton, use getConfig to get config instance
 *
 * @version 3
 * @since 10.03.2019
 * @author Jonatan Baumgartner
 */
public class Config implements Serializable {

    private Config() {
        nbLed = new int[4];
        raspIp = "192.168.100.100";
        nbLed[0] = 10;
        nbLed[1] = 10;
        nbLed[2] = 10;
        nbLed[3] = 10;

        lumMax = 120;

        mode = Computation_I.MODE_AMBILIGHT;

        persoModeFile = "";
        persoModeDefaultDirectory = "modes\\";

        color = new int[3];

        color[0] = 255;
        color[1] = 255;
        color[2] = 255;

        tempMode = 0;
    }

    //const
    //used for number of leds on each side
    public static final int NORTH = 1;
    public static final int EAST = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 0;

    //attribute
    private String raspIp;
    private int[] nbLed;
    private int lumMax;
    private int mode;
    private int tempMode;
    private String persoModeFile;
    private final String persoModeDefaultDirectory;
    private int[] color;

    //getter
    public String getRaspIp() {
        return raspIp;
    }

    /**
     * return the number of LEDs on the specified side
     * @param pos side of the screen (use Config.NORTH, WEST, EAST or SOUTH)
     * @return number of LEDs on the specified side of the screen
     */
    public int getNbLed(int pos) {
        return nbLed[pos];
    }

    /**
     * return an array with the number of leds on each side
     * @return array with the number of leds on each side
     */
    public int[] getNbLed() {
        return nbLed;
    }

    public int getNbLedTotal() {
        return nbLed[0] + nbLed[1] + nbLed[2] + nbLed[3];
    }

    public int getLumMax() {
        return lumMax;
    }

    public int getMode() {
        return mode;
    }

    public String getPersoModeFile() {
        return persoModeFile;
    }

    public String getPersoModeDefaultDirectory() {
        return persoModeDefaultDirectory;
    }

    public int[] getColor() {
        return color;
    }

    //setter
    public void setRaspIp(String raspIp) {
        this.raspIp = raspIp;
    }

    public void setNbLed(int nbLed, int pos) {
        this.nbLed[pos] = nbLed;
    }

    public void setLumMax(int lumMax) {
        this.lumMax = lumMax;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setPersoModeFile(String persoModeFile) {
        this.persoModeFile = persoModeFile;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    //singleton
    private static Config config;

    /**
     * get the config instance, if not initialized either load it frrom file or if file not exsist, create it with default values
     * @return Config instance 
     */
    public static Config getConfig() {
        if (config == null) {
            try {
                File f = new File("config.amb");
                if (f.exists() && !f.isDirectory()) {
                    FileInputStream fi = new FileInputStream(f);
                    ObjectInputStream oi = new ObjectInputStream(fi);
                    config = (Config) oi.readObject();
                    fi.close();
                } else {
                    config = new Config();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return config;
    }

    //serialization
    public static void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("config.amb");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(config);
            objectOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
