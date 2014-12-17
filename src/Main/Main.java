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
		try {
			r = new RDepart(1, 4);
			c.traiteRequete(r);
		} catch (RDepartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
