package Modele;

import java.util.ArrayList;
/**
 * <b>  RMap  est la classe permettant de demander au controleur si la voiture peut partir</b>
 * <p>
 * Un membre de RMap est caracteriser par les information suivante :
 * <ul>
 * <li>Un entier permetant d'identifier la voiture</li>
 * <li> Un tableau permetant de connaitre les places que la voiture veut reserver</li>
 * </ul>
 * @author florian + theo
 *	@version 0.1
 */
public class RMap extends Requete {
	/**
	 * L'identifiant du vehicule demandant si il peut partir ou non 
	 * Cette identifiant ne peut etre  changer
	 */
	private int identifiant;
	
	/**
	 * Request_map, transmise au controleur pour demander d'emprunter un trajet
	 * Elle est constituee ainsi :
	 * Index : 
	 * de 0 a 5 : Places d'entree
	 * de 6 a 11 : Place classique
	 * de 12 a 17 : Place de sortie
	 * 30 : centre
	 * A chaque index se trouve un booleen, qui indique si on veut reserver la place ou non :
	 *
	 * I1 I2 I3 I4 I5 I6 R1 R2 R3 R4 R5 R6 O1 O2 O3 O4 O5 O6 C
	 * T  F  F  F  F  F  T  T  F  F  F  F  F  T  F  F  F  F  F
	 * 
	 * T=true et F=false
	 * Cette Request ne peut pas etre changer
	 */
	private ArrayList<Boolean> request_map;
	/**
	 * Le constructeur de Rmap.
	 * Afin de generer la request map nous devont savoir par quel chemin la voiture desire passer
	 * @param id
	 * 			L'identfiant unnique du vehicule
	 * @param trajet
	 *				Le trajet effectuer par le vehicule.
	 */
	public RMap(int id, ArrayList<Integer> trajet) {
		identifiant = id;
		for(int i=0; i<19; i++){
			request_map.add(false);
		}
	}

	public int getIdentifiant() {
		return identifiant;
	}

	

	public ArrayList<Boolean> getRequest_map() {
		return request_map;
	}

	
}
