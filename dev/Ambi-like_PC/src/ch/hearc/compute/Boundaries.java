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
 * @author teosc
 * Fonctionnement inspiré du pattern producteur-consommateur
 */
public class Boundaries {

    //tools
    private int[][] boundaries; 
    private int indexC;
    private int indexP;
    private final int len;
    private boolean full;

    public Boundaries(int nbLeds) {
        this.boundaries = new int[nbLeds][5];
        this.indexC = -1;
        this.indexP = -1;
        this.len = nbLeds;
        this.full = false;
    }

    public synchronized int[] getNext() {
        //only return something that has been initialized already
        if (full || indexC < indexP){
            indexC = (++indexC) % len;
        }else{
            return new int[]{0,0,0,0,0};
        }
        return boundaries[indexC];
    }

    public void setNext(int xMin, int yMin, int xMax, int yMax) {
        int ind;
        
        //protect this part where we use the shared index
        synchronized(this){
            ind = ++indexP;
            indexP = indexP % len;
        }
        
        //circle!
        if(ind == len-1){
            this.full = true;
            ind = 0;
        }
       
        this.boundaries[ind] = new int[]{xMin, yMin, xMax, yMax, ind};
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
