/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hearc.compute.senders;

/**
 *
 * @author teosc
 */
public interface Sender {
    
    public void send(int nbLed, int r, int g, int b);
    
}