package Modele;

import java.util.ArrayList;
import java.util.HashMap;

import Exception.BoiteAuxLettresException;

/**
 * <b> La classe qui regit toute la partie Objet </b>
 * <p>
 * Ses caracteristiques sont les suivantes :
 * <ul>
 * <li><b>HashMap<Integer, Boolean></b> une hashmap representant l'occupation des
 * places sur le plateau. Le premier parametre etant l'identifiant de la place et
 * le second son occupation</li>
 * <li><b>BoiteAuxLettres</b> elle contient les differentes requetes envoyees au
 * contreleur</li>
 * <li><b>FlotteVehicule</b> contient toutes les voitures auxquelles le contreleur
 * peut donner des ordres</li>
 * </ul>
 * 
 * @author florian + theo
 * @version 0.1
 */
public class Controleur implements Observable {

	/**
	 * La liste des points atteignables sou forme d'int comme suivant
	 * <ul>
	 * <li> les clées de  1 a 6 sont les clées des place d'entrées</li>
	 * <li> les clées de 11 a 16 sont les clées des routes</li>
	 * <li> les clées de 21 a 16 sont les clées des places de sorties</li>
	 * <li> La clée 30 est une clée en reference vers le centre.
	 * </ul>
	 * Le segond parametre et la disponibilité de la map: true si la cette portion de map est libre false si elle est deja reserver.
	 * 
	 */
	private HashMap<Integer, Boolean> general;
	/**
	 * Une reference sur la boite contenant toute les requete adresser a l'utilisateur.
	 */
	private BoiteAuxLettres boite;
	private FlotteVehicules maFlotte;
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	
	public Controleur() {
		createHashMap();
		boite=new BoiteAuxLettres();
	}
	/**
	 * Constructeur de la Hashmap
	 * 
	 * @return void
	 * 	 */
	private void createHashMap(){
		general=new HashMap<Integer, Boolean>();
			for(int j=1;j<7;j++){
				general.put(j,true);
			}
			for(int j=11;j<17;j++){
				general.put(j,true);
			}
			for(int j=17;j<27;j++){
				general.put(j,true);
			}
			general.put(30,true);
	}
	
	/**
	 * Fonction permetant de traiter la requete "general"
	 * 
	 */
	public void traiteRequete(){
		
		try {
			Requete r= boite.getFirst();
			traiteRequete(r);
		} catch (BoiteAuxLettresException e) {
			// TODO Auto-generated catch block
			try {
				wait(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	private void traiteRequete(Requete r) {
			
		
	}

	public void updateArriveeTemp(int iD){
		//La partie graphique a fini son chemin
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
		// Commande a ins�rer
		for (Observer obs : listObserver)
			obs.update(str);
	}

	public void removeObserver() {
		listObserver = new ArrayList<Observer>();
	}
}
