package edu.eci.arsw.highlandersim;

import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Immortal extends Thread {

    private ImmortalUpdateReportCallback updateCallback=null;
    
    private boolean flagLife;
    
    private int health;
    
    private int defaultDamageValue;

    private final List<Immortal> immortalsPopulation;

    private final String name;

    private final Random r = new Random(System.currentTimeMillis());

    private Lock lock;
    public Immortal(Lock lock,String name, List<Immortal> immortalsPopulation, int health, int defaultDamageValue, ImmortalUpdateReportCallback ucb) {
        super(name);
        flagLife=false;
        this.updateCallback=ucb;
        this.name = name;
        this.immortalsPopulation = immortalsPopulation;
        this.health = health;
        this.defaultDamageValue=defaultDamageValue;
        this.lock=lock;
    }

    public void run() {

        while (true) {
            Immortal im;

            int myIndex = immortalsPopulation.indexOf(this);

            int nextFighterIndex = r.nextInt(immortalsPopulation.size());

            //avoid self-fight
            if (nextFighterIndex == myIndex) {
                nextFighterIndex = ((nextFighterIndex + 1) % immortalsPopulation.size());
            }

            im = immortalsPopulation.get(nextFighterIndex);

				this.fight(im);
		

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(flagLife){
	            synchronized(this){
	            	try {
	            		wait();
	            	}catch(Exception e) {
	            		
	            	}
	            }
            }

        }

    }

    public void setFlagLife(boolean flag) {
    	flagLife=flag;
    }
    
    public void fight(Immortal i2) {
    	
    	
    	synchronized(lock){
    	lock.lock();
    	
        if (i2.getHealth() > 0) {
        	i2.changeHealth(i2.getHealth() - defaultDamageValue); 
        	if(i2.getHealth()<=0) {
        		
        		immortalsPopulation.remove(i2);
        		i2.suspend();
        	}
        	this.health += defaultDamageValue;
        	updateCallback.processReport("Fight: " + this + " vs " + i2+"\n");
  
        } else {
            updateCallback.processReport(this + " says:" + i2 + " is already dead!\n");       
    	}lock.unlock();}
    	

    }
    
    


    public void changeHealth(int v) {
        health = v;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public String toString() {

        return name + "[" + health + "]";
    }
    
   
}
