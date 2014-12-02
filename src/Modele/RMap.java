package Modele;

import java.util.ArrayList;

public class RMap implements Requete {
	private int identifiant;
	
	/**
	 * Request_map, transmise au controleur pour demander d'emprunter un trajet
	 * Elle est constituee ainsi :
	 * Index : 
	 * de 0 a 5 : Places d'entree
	 * de 6 a 11 : Place classique
	 * de 12 a 17 : Place de sortie
	 * 30 : centre
	 * A chaque index se trouve un booleen, qui indique si on veut reserver la place ou non
	 */
	private ArrayList<Boolean> request_map;

	public RMap(int id) {
		identifiant = id;
		for(int i=0; i<19; i++){
			request_map.add(false);
		}
	}

	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	public ArrayList<Boolean> getRequest_map() {
		return request_map;
	}

	public void setRequest_map(ArrayList<Boolean> request_map) {
		this.request_map = request_map;
	}
}
