/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arst.concprg.prodcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartProduction {
    
    	private static Consumer consumidor;
    	private static Producer productor;
    	private static boolean inicioconsumidor=false;
    public static void main(String[] args) {
        
        Queue<Integer> queue=new LinkedBlockingQueue<>();
        new Producer(queue,10).start();
        productor = new Producer(queue,10);
        productor.start();
        
        //let the producer create products for 5 seconds (stock).
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(StartProduction.class.getName()).log(Level.SEVERE, null, ex);
        }
        consumidor = new Consumer(queue);
        consumidor.start();
    }
    	
    public static boolean getInicioconsumidor() {
    	return inicioconsumidor;
    }
    
    public static void setInicioconsumidor(boolean inicio) {
    	inicioconsumidor=inicio;
    }
    
    public static void reiniciarConsumidor() {
    	synchronized(consumidor) {
    		consumidor.notify();
    	}
    }
    
    public static void reiniciarProductor() {
    	synchronized(productor) {
    		productor.notify();
    	}
    }
    
}