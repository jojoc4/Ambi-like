package ch.hearc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * represents a personalized mode, is serializable and iterable
 *
 * @author Jonatan Baumgartner
 */
public class ModePersonnalise implements Iterable<Pixel>, Serializable {

    private String name;
    private int nbled[];
    private Vector<Pixel> l;

    public ModePersonnalise() {
        Config cfg = Config.getConfig();
        nbled = cfg.getNbLed();

        int totalLed = cfg.getNbLedTotal();

        l = new Vector<Pixel>(totalLed);

        for (int i = 0; i < totalLed; i++) {
            addLed(new Pixel(0, 0, 0));
        }
    }

    public String getName() {
        return name;
    }

    public int[] getNbled() {
        return nbled;
    }

    public Pixel getPixel(int index) {
        return l.get(index);
    }

    public Pixel setLed(int index, Pixel p) {
        return l.set(index, p);
    }

    public void addLed(Pixel p) {
        l.add(p);
    }

    @Override
    public Iterator<Pixel> iterator() {
        return l.iterator();
    }

    /**
     * return a personalized mode loaded from a file
     *
     * @param file filename to load
     * @return
     */
    public static ModePersonnalise getMode(String file) {
        try {
            File f = new File(file);
            if (f.exists() && !f.isDirectory()) {
                FileInputStream fi = new FileInputStream(f);
                ObjectInputStream oi = new ObjectInputStream(fi);
                ModePersonnalise mp = (ModePersonnalise) oi.readObject();
                fi.close();

                //TODO verify that the number of leds was the same when file created
                return mp;
            } else {
                throw new Exception("File don't exsist");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public static List<ModePersonnalise> getListMode(){
        
    }

    /**
     * saves current mode to a file
     *
     * @param file name of the file
     */
    public void save(String file) {
        try {
            this.name = file;
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
