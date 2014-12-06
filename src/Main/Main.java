package Main;

import Controleur.Verificateur;
import Modele.Controleur;
import Vue.Fenetre;

public class Main {

	public static void main(String[] args) {

		Controleur c = new Controleur();
		System.out.println(c.getClass());
		Verificateur v = new Verificateur(c);
		Fenetre f = new Fenetre(v);
		// Ajout de la fen�tre comme observer de notre mod�le
		c.addObserver(f);

	}

}
