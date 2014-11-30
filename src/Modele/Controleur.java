package Modele;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <b> La classe qui régit toute la partie Objet </b>
 * <p>
 * Ses caractéristiques sont les suivantes :
 * <ul>
 * <li><b>HashMap<Integer, Boolean></b> une hashmap représentant l'occupation des
 * places sur le plateau. Le premier paramètre étant l'identifiant de la place et
 * le second son occupation</li>
 * <li><b>BoiteAuxLettres</b> elle contient les différentes requêtes envoyées au
 * contrôleur</li>
 * <li><b>FlotteVehicule</b> contient toutes les voitures auxquelles le contrôleur
 * peut donner des ordres</li>
 * </ul>
 * 
 * @author florian + theo
 * @version 0.1
 */
public class Controleur implements Observable {

	private HashMap<Integer, Boolean> general;
	private BoiteAuxLettres boite;
	private FlotteVehicules maFlotte;
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();

	public Controleur() {

	}

	public void traiteRequete(RDepart r) {

	}

	public void traiteRequete(RFinTrajet r) {

	}

	public void traiteRequete(RMap r) {

	}

	
	/*
	 * 
	 * 
	 * Patern MVC
	 * 
	 * 
	 * (non-Javadoc)
	 * @see Modele.Observable#addObserver(Modele.Observer)
	 */
	public void addObserver(Observer obs) {
		this.listObserver.add(obs);
	}

	public void notifyObserver(String str) {
		// Commande a insérer
		for (Observer obs : listObserver)
			obs.update(str);
	}

	public void removeObserver() {
		listObserver = new ArrayList<Observer>();
	}
}
