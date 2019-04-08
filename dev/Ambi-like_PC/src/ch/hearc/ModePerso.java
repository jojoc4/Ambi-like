package ch.hearc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jonatan Baumgartner
 */
public class ModePerso implements Iterable<Pixel>, Serializable {

    private String name;
    private int nbled[];
    private List<Pixel> l;

    public ModePerso() {
        Config cfg = Config.getConfig();
        nbled = cfg.getNbLed();

        l = new LinkedList<>();

        int totalLed = 0;
        for (int i = 0; i < nbled.length; i++) {
            totalLed += nbled[i];
        }
        for (int i = 0; i < totalLed; i++) {
            setLed(i, new Pixel(0, 0, 0));
        }
    }

    public String getName() {
        return name;
    }

    public int[] getNbled() {
        return nbled;
    }
    
    public Pixel getPixel(int index){
        return l.get(index);
    }

    public void setLed(int index, Pixel p) {
        l.add(index, p);
    }

    @Override
    public Iterator<Pixel> iterator() {
        return l.iterator();
    }

    public static ModePerso getMode(String file) {
        try {
            File f = new File(file);
            if (f.exists() && !f.isDirectory()) {
                FileInputStream fi = new FileInputStream(f);
                ObjectInputStream oi = new ObjectInputStream(fi);
                ModePerso mp = (ModePerso) oi.readObject();
                fi.close();

                return mp;
            } else {
                throw new Exception("File don't exsist");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //serialization
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
