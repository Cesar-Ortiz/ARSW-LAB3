/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.eci.arsw.threads.CountThread;

/**
 *
 * @author hcadavid
 */
public class Main {
    public static void main(String a[]) throws InterruptedException, IOException{
    	Runtime r=Runtime.getRuntime();
        HostBlackListsValidator hblv=new HostBlackListsValidator();
        List<Integer> blackListOcurrences;
        //blackListOcurrences=hblv.checkHost("202.24.34.55",1);
        //blackListOcurrences=hblv.checkHost("202.24.34.55",r.availableProcessors());
        //blackListOcurrences=hblv.checkHost("202.24.34.54",r.availableProcessors()*2);
        blackListOcurrences=hblv.checkHost("202.24.34.54",50);
        //blackListOcurrences=hblv.checkHost("202.24.34.54",100);
        System.out.println("The host was found in the following blacklists:"+blackListOcurrences);
    }
    
}
