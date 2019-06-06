package ch.hearc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * represents a personalized mode, is serializable and iterable
 *
 * @author Jonatan Baumgartner
 */
public class ModePersonnalise implements Iterable<Pixel>, Serializable {

    private String name;
    private Vector<Pixel> l;

    public ModePersonnalise() {
        name = "";

        int totalLed = Config.getConfig().getNbLedTotal();

        l = new Vector<Pixel>(totalLed);

        for (int i = 0; i < totalLed; i++) {
            addLed(new Pixel(0, 0, 0));
        }
    }

    public ModePersonnalise(String name) {
        this();
        this.name = name;
    }

    /**
     * Gives the name of this mode
     * 
     * @return name of the mode
     */
    public String getName() {
        return name;
    }

    /**
     * get the pixel at the specified index
     * @param index
     * @return Pixel at given index
     */
    public Pixel getPixel(int index) {
        return l.get(index);
    }

    /**
     * get the entire pixels vector
     * 
     * @return Vector containing all the Pixels
     */
    public Vector<Pixel> getPixels() {
        return l;
    }

    /**
     * Sets the name of the mode
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set the pixel of the specified index
     * 
     * @param index index
     * @param p new Pixel
     */
    public void setLed(int index, Pixel p) {
        l.set(index, p);
    }

    /**
     * add pixel at the end
     * 
     * @param p Pixel to add
     */
    public void addLed(Pixel p) {
        l.add(p);
    }

    @Override
    public Iterator<Pixel> iterator() {
        return l.iterator();
    }

    /**
     * returns a personalized mode loaded from a file
     *
     * @param file filename to load
     * @return personalized mode loaded from a file
     */
    public static ModePersonnalise getMode(String file) {
        try {
            File f = new File(file);
            if (f.exists() && !f.isDirectory()) {
                FileInputStream fi = new FileInputStream(f);
                ObjectInputStream oi = new ObjectInputStream(fi);
                ModePersonnalise mp = (ModePersonnalise) oi.readObject();
                fi.close();
                
                if(mp.getPixels().size() != Config.getConfig().getNbLedTotal()){
                    JOptionPane.showMessageDialog(null, "Le nombre de LEDs de votre mode personnalisé ne correspond pas à la configuration actuelle.", "Erreur", JOptionPane.INFORMATION_MESSAGE);
                    mp = new ModePersonnalise(mp.getName());
                }
                
                return mp;
            } else {
                throw new Exception("File doesn't exsist");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ModePersonnalise();
        }
    }

    public static File[] getListMode() {
        File folder = new File("modes/");
        return folder.listFiles();
    }

    /**
     * saves current mode to a file
     *
     * @param file name of the file
     */
    public void save(String file) {
        try {
            if (name == "") {
                this.name = file;
            }

            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
