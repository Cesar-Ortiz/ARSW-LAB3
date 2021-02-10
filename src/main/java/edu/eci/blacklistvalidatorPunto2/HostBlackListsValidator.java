/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.blacklistvalidatorPunto2;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     * @throws InterruptedException 
     */
    public List<Integer> checkHost(String ipaddress,int hilos) throws InterruptedException{
        
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();
        ArrayList <Hilo1> listahilo= new ArrayList();

        int nhilos=80000/hilos;
        for(int i=0 ; i<hilos; i++) {
        	Hilo1 hilo;
        	if(i==hilos-1) {
        		hilo = new Hilo1((i*nhilos)+1,80000,ipaddress);
        	}else {
        		hilo = new Hilo1((i*nhilos)+1,(i+1)*nhilos,ipaddress);
        	}
        	listahilo.add(hilo);
        	hilo.start();
    	}
        
        
        int checkedListsCount=0;
        //for(int i=0;i<listahilo.size();i++) {
        //	listahilo.get(i).join();
        //}
        int ocurrencesCount=0;
        
        boolean flag=true;
        while(ocurrencesCount<BLACK_LIST_ALARM_COUNT && flag){
        	ocurrencesCount=0;
        	for(int i=0;i<listahilo.size();i++)  {
        		ocurrencesCount+=listahilo.get(i).getcountmal();
            }	
        	flag=listahilo.get(0).isAlive();
        	for(int i=1;i<listahilo.size();i++)  {
    			flag=flag || listahilo.get(i).isAlive();
    		}
        }
        for(int i=0;i<listahilo.size();i++)  {
    		listahilo.get(i).stop();
        }
        
        
        for(int i=0;i<listahilo.size();i++)  {
        	blackListOcurrences.addAll(listahilo.get(i).getblackListOcurrences());
        	checkedListsCount+=listahilo.get(i).getcountrango();
        	ocurrencesCount+=listahilo.get(i).getcountmal();
        	
        }
        
        //int ocurrencesCount=0;
        
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        /*
        int checkedListsCount=0;
        for (int i=0;i<skds.getRegisteredServersCount() && ocurrencesCount<BLACK_LIST_ALARM_COUNT;i++){
            checkedListsCount++;
            if (skds.isInBlackListServer(i, ipaddress)){
                
                blackListOcurrences.add(i);
                
                ocurrencesCount++;
            }
        }*/
        
        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }  
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
   
}
