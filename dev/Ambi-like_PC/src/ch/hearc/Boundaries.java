/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc;

/**
 *
 * @author teosc
 * Fonctionnement inspiré du pattern producteur-consommateur
 */
public class Boundaries {

    //tools
    private int[][] boundaries; //boundaries[nb total LEDs]{xMin, yMin, xMax, yMax}
    private int indexC;
    private int indexP;
    private int len;
    private boolean full;

    public Boundaries(int nbLeds) {
        this.boundaries = new int[nbLeds][4];
        this.indexC = -1;
        this.indexP = -1;
        this.len = nbLeds;
        this.full = false;
    }

    public synchronized int[] getNext() {
        if (full || indexC < indexP)
        {
            indexC = (++indexC) % len;
        }
        else
        {
            return null;
        }
        
        return boundaries[indexC];
    }

    public synchronized void setNext(int xMin, int yMin, int xMax, int yMax) {
        indexP = (++indexP) % len;
        boundaries[indexP][0] = xMin;
        boundaries[indexP][1] = yMin;
        boundaries[indexP][2] = xMax;
        boundaries[indexP][3] = yMax;
        
        if(indexP%len > 0)
        {
            this.full = true;
        }
    }

}
