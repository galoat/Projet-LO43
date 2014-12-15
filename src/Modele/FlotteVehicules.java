package Modele;

import java.util.ArrayList;
import java.util.HashMap;

import Exception.FlotteException;
/**
 * <b>FlotteVehicules est la classe representant tous les vehicules sur la plateau</b>
 * <p>
 * Un membre de FlotteVehicules est caracteriser par :
 * <ul>
 * <li> un integer representant le nombre total de vehicule present sur le plateau </li>
 * <li>une Hasmap representant les identifiant des vehicule ainsi que la possibilité de l'embaucher.</li>
 *</ul>
 * @author flacour+theo
 *@version 0.1
 */
public class FlotteVehicules {
	/**
	 * Le nombre de vehicule libre sur la map, cette entier n'est pas modifiable par une autre classe
	 */
	private int capaciter;
	/**
	 * Une Hashmap contenant :
	 *<ul>
	 *<li> un Integer qui est l'identifiant du vehicule est qui est aussi la clée de cette hasmap </li>
	 *<li>un boolean permetant de savoir si  le vehicule est true si le vehicule est libre false sinon</li>
	 *</ul>
	 *
	 */
	//private HashMap<Integer, Boolean> vehicule;
	private ArrayList<Vehicule> vehicules;
	
	
	
	/**
	 * Constructeur de FlotteVehicules
	 * @param c
	 * 		Entier contenant la capaciter en voiture du plateau
	 */
	
	FlotteVehicules(int c, BoiteAuxLettres boite){
		capaciter=c;
		//vehicule=new HashMap<Integer, Boolean>();
		vehicules = new ArrayList<Vehicule>();
		for(int i =0;i<c;i++){
			//vehicule.put(i,true);
			vehicules.add(new Vehicule(c, boite));
		}
	}
	
	
	
	/**
	 * Fonction permetant de donner un passager a une voiture 
	 * @param m
	 *		Un passager
	 */
	//exception si la liste des vehicule est compléte ?
	public void donnerPassager(Passager m)throws FlotteException {
		
		
	}

	/**
	 * Retourn la capaciter en nombre de vehicule du plateau
	 * @return la capaciter en nombre de vehicule du plateau
	 *@see capaciter
	 */

	public int getCapaciter() {
		return capaciter;
	}


}
