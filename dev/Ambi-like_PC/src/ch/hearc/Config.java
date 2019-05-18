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

        color = new int[3];

        color[0] = 255;
        color[1] = 255;
        color[2] = 255;
        
        tempMode = "";
    }

    //const
    //used for number of leds on each side
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

    //attribute
    private String raspIp;
    private int[] nbLed;
    private int lumMax;
    private String mode;
    private String tempMode;
    private String persoModeFile;
    private int[] color;

    //getter
    public String getRaspIp() {
        return raspIp;
    }

    public int getNbLed(int pos) {
        return nbLed[pos];
    }

    public int[] getNbLed() {
        return nbLed;
    }

    public int getNbLedTotal() {
        return nbLed[0] + nbLed[1] + nbLed[2] + nbLed[3];
    }

    public int getLumMax() {
        return lumMax;
    }

    public String getMode() {
        return mode;
    }
    
    public String getTempMode(){
        return tempMode;
    }

    public String getPersoModeFile() {
        return persoModeFile;
    }

    public int[] getColor() {
        return color;
    }

    public int getNombreTotalLed() {
        return nbLed[Config.NORTH] + nbLed[Config.EAST] + nbLed[Config.SOUTH] + nbLed[Config.WEST];
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

    public void setMode(String mode) {
        this.mode = mode;
    }
    
    public void setTempMode(String mode){
        this.tempMode = mode;
    }

    public void setPersoModeFile(String persoModeFile) {
        this.persoModeFile = persoModeFile;
    }

    public void setColor(int[] color) {
        this.color = color;
    }

    //singleton
    private static Config config;

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
