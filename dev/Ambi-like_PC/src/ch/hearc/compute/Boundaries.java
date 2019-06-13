package ch.hearc.compute;

import ch.hearc.Config;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Contains the areas on which to compute the colors, top-left and bottom-right
 * corners as delimiters <br>
 * Works similarly to producer-consumer pattern <br>
 * Use setNext to insert an area (producer), and getNext to access the next area
 * (consumer) <br>
 * Works in the First-in First-out behaviour. <br>
 * This class is thread-safe!
 *
 * @version 2.1
 * @since 13.05.2019
 * @author Téo Schaffner
 */
public class Boundaries {

    //tools
    private final int[][] boundaries;
    private int indexC;
    private int indexP;
    private final int len;
    private boolean full;

    private final Lock lock;
    private final Condition condition;

    /**
     * Constructor
     *
     * @param nbLeds Number of LEDs you have. There will be as many areas as
     * LEDs.
     */
    public Boundaries(int nbLeds) {
        this.boundaries = new int[nbLeds][5];
        this.indexC = -1;
        this.indexP = -1;
        this.len = nbLeds;
        this.full = false;

        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    /**
     * Gives the next area that should be calculated. Thread-safe.
     *
     * @return Array containing the coordinates of the area and the index of the
     * area {xMin, yMin, xMax, yMax, index}
     */
    public int[] getNext() {
        int ind = 0;

        lock.lock();
        try {
            //only return something that has already been initialized
            if (full || indexC < indexP) {
                indexC = (++indexC) % len;
                ind = indexC;
                //ind = ++indexC;
            } else {
                return new int[]{0, 0, 0, 0, 0};
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return boundaries[ind];
    }

    /**
     * Inserts an area at the end of the array. If the array if already full, it
     * overwrites the first element and so-on. Thread-safe.
     *
     * @param xMin x coordinate of the bottom-left corner
     * @param yMin y coordinate of the bottom-left corner
     * @param xMax x coordinate of the top-right corner
     * @param yMax y coordinate of the top-right corner
     */
    public void setNext(int xMin, int yMin, int xMax, int yMax) {
        int ind;

        //protect this part where we use the shared index
        lock.lock();
        try {
            ind = ++indexP;
            indexP = indexP % len;
        } finally {
            lock.unlock();
        }

        //circle!
        if (ind == len - 1) {
            this.full = true;
        }

        this.boundaries[ind] = new int[]{xMin, yMin, xMax, yMax, ind};
    }

    /**
     * FOR DEBUGGING PURPOSE ONLY!! Prints all the areas to the console. Don't
     * use this method for anything other than debugging as it is not
     * thread-safe.
     */
    public void printAll() {
        int num = 0;
        int cote = 0;
        for (int[] i : boundaries) {
            System.out.println(Arrays.toString(i));

            if (num == Config.getConfig().getNbLed(cote) - 1) {
                System.out.println("Cote");
                num = -1;
                cote++;
            }
            ++num;
        }
    }

}
