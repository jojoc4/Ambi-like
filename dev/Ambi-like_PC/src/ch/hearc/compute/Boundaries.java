/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.compute;

import ch.hearc.Config;
import java.util.Arrays;

/**
 *
 * @author teosc Fonctionnement inspiré du pattern producteur-consommateur
 */
public class Boundaries {

    //tools
    private int[][] boundaries; //boundaries[nb total LEDs]{xMin, yMin, xMax, yMax, index}
    //private Vector<int[]> boundaries;
    private int indexC;
    private int indexP;
    private int len;
    private boolean full;

    public Boundaries(int nbLeds) {
        this.boundaries = new int[nbLeds][5]; //new Vector<int[]>(nbLeds);
        this.indexC = -1;
        this.indexP = -1;
        this.len = nbLeds;
        this.full = false;
    }

    public synchronized int[] getNext() {
        if (full || indexC < indexP)
            indexC = (++indexC) % len;
        else
            return new int[]{0,0,0,0,0};
        
        //return boundaries.elementAt(indexC);
        return boundaries[indexC];
    }

    public synchronized void setNext(int xMin, int yMin, int xMax, int yMax) {
        indexP = (++indexP) % len;
       
        int[] boundary = new int[]{xMin, yMin, xMax, yMax, indexP};
       
        //this.boundaries.add(indexP, boundary);
        this.boundaries[indexP] = boundary;
        
        if(indexP%len > 0)
            this.full = true;
    }

    /**
     * FOR DEBUGGING PURPOSE ONLY!!
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
