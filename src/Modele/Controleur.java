package Modele;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <b> C'est la classe qui dirige la partie objet </b>
 * <p>
 * les caractéristique sont les suivante:
 * <ul>
 * <li><b>HashMap<Integer, Boolean></b> une hasmap representant l'occupation des
 * places sur le plateau le premier parametre etant l'identifiant de la place et
 * le segond sont occupation</li>
 * <li><b>BoiteAuxLettres</b> elle contient les différente requete envoyer au
 * controleur</li>
 * <li><b>FlotteVehicule</b> contient toute les voitures ausquel le controleur
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

	Controleur() {

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
