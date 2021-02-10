package edu.eci.blacklistvalidatorPunto2;

import java.util.LinkedList;



public class Hilo1 extends Thread{
	private int inisegmento;
	private int finsegmento;
	private String ip;
	private HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
	private int countmal;
	private int countrango;
	private LinkedList<Integer> blackListOcurrences=new LinkedList<>();
	
	public Hilo1(int inisegmento,int finsegmento,String ip){
		 this.inisegmento=inisegmento;
		 this.finsegmento=finsegmento;
		 this.ip = ip;
	}
	
	public void run() {
		countmal=0;
		countrango=0;
		for(int i=inisegmento; i<= finsegmento;i++) {
			countrango+=1;
			 if (skds.isInBlackListServer(i, ip)){
				 countmal+=1;
				 blackListOcurrences.add(i);
			 }
		 }
	}
	
	public int getcountmal(){
		return countmal;
	}
	
	public LinkedList<Integer> getblackListOcurrences(){
		return blackListOcurrences;
	}
	
	public int getcountrango() {
		return countrango;
	}
}
