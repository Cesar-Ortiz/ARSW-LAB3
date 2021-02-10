package edu.eci.arsw.threads;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author hcadavid
 */

public class CountThread extends Thread{
	private int rango1,rango2;
	
	public CountThread(int rango1,int rango2) {
		this.rango1=rango1;
		this.rango2=rango2;
	}
	

	public void run() {
		for(int i=rango1;i<=rango2;i++) {
			System.out.println(i);
		}
	}

}
