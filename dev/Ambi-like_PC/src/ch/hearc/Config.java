package ch.hearc;

/**
 *
 * @author Jonatan Baumgartner
 */
public class Config {

    private Config() {
        nbLed = new int[4];
    }

    //const
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;

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
            config = new Config();
        }
        return config;
    }
}
