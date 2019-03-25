package ch.hearc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author Jonatan Baumgartner
 */
public class Config implements Serializable {

    private Config() {
        nbLed = new int[4];
    }

    //const
    
    //used for number of leds on each side
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    //used to indicate the chossen mode
    public static final int AMBILIGHT = 0;
    public static final int COLOR = 1;
    public static final int PERSO = 2;

    //attribute
    private String raspIp;
    private int[] nbLed;
    private byte lumMax;
    private int mode;
    private int persoModeFile;

    //getter
    public String getRaspIp() {
        return raspIp;
    }

    public int getNbLed(int pos) {
        return nbLed[pos];
    }

    public byte getLumMax() {
        return lumMax;
    }

    public int getMode() {
        return mode;
    }

    public int getPersoModeFile() {
        return persoModeFile;
    }

    //setter
    public void setRaspIp(String raspIp) {
        this.raspIp = raspIp;
    }

    public void setNbLed(int nbLed, int pos) {
        this.nbLed[pos] = nbLed;
    }

    public void setLumMax(byte lumMax) {
        this.lumMax = lumMax;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setPersoModeFile(int persoModeFile) {
        this.persoModeFile = persoModeFile;
    }

    //singleton
    private static Config config;

    public static Config getConfig() {
        if (config == null) {
            try {
                FileInputStream fi = new FileInputStream(new File("config.amb"));
                ObjectInputStream oi = new ObjectInputStream(fi);
                config = (Config) oi.readObject();
                fi.close();
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
