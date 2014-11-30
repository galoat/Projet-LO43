package Main;

import Controleur.Verificateur;
import Modele.Controleur;
import Vue.Fenetre;

public class Main {

	public static void main(String[] args) {

	    Controleur c = new Controleur();
	    Verificateur v = new Verificateur(c);
	    Fenetre f = new Fenetre(v);
	    //Ajout de la fenêtre comme observer de notre modèle
	    c.addObserver(f);
	}

}
