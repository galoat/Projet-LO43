package Controleur;

import java.util.Random;

import Exception.RDepartException;
import Exception.RequeteException;

public class RequestGenerator extends Thread{
	private Verificateur verif;
	private int compteur = 0, dep, ar;
	private Random r;
	private boolean running;
	public RequestGenerator(Verificateur verif){
		this.verif = verif;
		r = new Random();
		running = true;
	}
	
	public void run(){
		while(true){
			if(running){
				compteur++;
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(compteur%2 == 0){
					dep = r.nextInt(5) + 1;
					ar = r.nextInt(5) + 1;
					while(dep == ar){
						ar = r.nextInt(5) + 1;
					}
					try {
						verif.newRequest(dep, ar);
					} catch (RequeteException | RDepartException e) {
						e.printStackTrace();
					}
				}
			}
			else{
				try {
					sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void reset(){
		compteur = 0;
	}
}
