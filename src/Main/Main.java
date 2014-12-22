package Main;

import Controleur.Verificateur;
import Exception.RDepartException;
import Modele.Controleur;
import Modele.RDepart;
import Vue.Fenetre;
import Vue.Simulation;

public class Main {

	public static void main(String[] args) {

		Controleur c = new Controleur();
		Thread t=new Thread(c);
		Verificateur v = new Verificateur(c);
		Fenetre f = new Fenetre(v);
		//Ajout de la fen�tre comme observer de notre mod�le
		c.addObserver(f);
		RDepart r;
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		t.start();

	}

}
