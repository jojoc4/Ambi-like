/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.compute;

import ch.hearc.Config;
import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    private Lock lock;
    private Condition condition;

    public Boundaries(int nbLeds) {
        this.boundaries = new int[nbLeds][5];
        this.indexC = -1;
        this.indexP = -1;
        this.len = nbLeds;
        this.full = false;
        
        this.lock = new ReentrantLock();
        this.condition = lock.newCondition();
    }

    public int[] getNext() {
        int ind = 0;
        lock.lock();
        
        try{
            //If all areas parsed, stop and wait for next image.
            while(indexC >= len-1){
                try {
                    condition.await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Boundaries.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //only return something that has been initialized already
            if (full || indexC < indexP){
                //indexC = (++indexC) % len;
                ind = ++indexC;
            }else{
                return null;//new int[]{0,0,0,0,0};
            }
        }catch(Exception e){e.printStackTrace();}
        finally{
            lock.unlock();
        }
        
        
        return boundaries[ind];
    }

    public void setNext(int xMin, int yMin, int xMax, int yMax) {
        int ind;
        
        //protect this part where we use the shared index
        lock.lock();
        try{
            ind = ++indexP;
            indexP = indexP % len;
        }finally{
            lock.unlock();
        }
        
        //circle!
        if(ind == len){
            this.full = true;
            ind = 0;
        }
       
        this.boundaries[ind] = new int[]{xMin, yMin, xMax, yMax, ind};
    }
    
    public void allowNextLoop(){
        lock.lock();
        
        try{
            indexC = -1;

            condition.signalAll();
        }finally{
            lock.unlock();
        }
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
