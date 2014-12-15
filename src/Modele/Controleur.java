package Modele;

import java.util.ArrayList;
import java.util.HashMap;

import Exception.FlotteException;

/**
 * <b> La classe qui regit toute la partie Objet </b>
 * <p>
 * Ses caracteristiques sont les suivantes :
 * <ul>
 * <li><b>HashMap<Integer, Boolean></b> une hashmap representant l'occupation
 * des places sur le plateau. Le premier parametre etant l'identifiant de la
 * place et le second son occupation</li>
 * <li><b>BoiteAuxLettres</b> elle contient les differentes requetes envoyees au
 * contreleur</li>
 * <li><b>FlotteVehicule</b> contient toutes les voitures auxquelles le
 * contreleur peut donner des ordres</li>
 * </ul>
 * 
 * @author florian + theo
 * @version 0.1
 */
public class Controleur implements Observable {

	/**
	 * La liste des points atteignables sou forme d'int comme suivant
	 * <ul>
	 * <li>les clees de 1 a 6 sont les clees des place d'entrees</li>
	 * <li>les clees de 11 a 16 sont les clees des routes</li>
	 * <li>les clees de 21 a 16 sont les clees des places de sorties</li>
	 * <li>La clee 30 est une clee en reference vers le centre.
	 * </ul>
	 * Le second parametre et la disponibilité de la map: true si la cette
	 * portion de map est libre false si elle est deja reserver.
	 * 
	 */
	private HashMap<Integer, Boolean> general;
	/**
	 * Une reference sur la boite contenant toute les requete adresser a
	 * l'utilisateur.
	 */
	private BoiteAuxLettres boite;
	private FlotteVehicules maFlotte;
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();

	public Controleur() {
		createHashMap();
		boite = new BoiteAuxLettres();
		maFlotte = new FlotteVehicules(3, boite);
	}

	/**
	 * Constructeur de la Hashmap
	 * 
	 * @return void
	 * */
	private void createHashMap() {
		general = new HashMap<Integer, Boolean>();
		for (int j = 1; j < 7; j++) {
			general.put(j, true);
		}
		for (int j = 11; j < 17; j++) {
			general.put(j, true);
		}
		for (int j = 21; j < 27; j++) {
			general.put(j, true);
		}
		general.put(30, true);
	}

	/**
	 * Fonction permetant de traiter la requete "general" celon un ordre de
	 * prioriter: elle traiteras dans un premier temp les requete de fin de
	 * mission apres quoi nous traiterons les requete de liberation de ressource puis les requete pour une nouvelle voiture et enfin les requete
	 * d'update de map
	 * @throws InterruptedException 
	 */
	public void traiteRequete()  {
		if (boite.getSizeRFintrajet() != 0) {
			traiteRequete(boite.getRFinTrajet());
			try {
				wait(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(boite.getSizeRLib()!=0){
			traiteRequete(boite.getRLib());
			try {
				wait(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (boite.getSizeRDepart() != 0) {
			traiteRequete(boite.getDepart());
			try {
				wait(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (boite.getSizeRMap() != 0) {
			traiteRequete(boite.getRMap());
			try {
				wait(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			try {
				wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * fonction permettant de traiter les requete de liberation de ressource
	 * @param r 
	 * 		une requete de liberation de vehicule
	 */
	private void traiteRequete(RLib r){
			general.put(r.getLib(), true);
	}
	/**
	 * fonction utiliser pour traiter les requete de depart
	 * @param r une requete de depart
	 */
	private void traiteRequete(RDepart r) {
		
			Passager p = new Passager(r.getDebut(),r.getFin());
			try {
				maFlotte.donnerPassager(p);
			} catch (FlotteException e) {
				//si il n'y a plus de place pour les vehicule
				// TODO Auto-generated catch block
				try {
					wait(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		
	}
	/**
	 * fonction utiliser pour traiter une requete de fin de trajet
	 * @param r une requete de fin de trajet
	 */
	private void traiteRequete(RFinTrajet r) {

	}
	/**
	 * 
	 * @param r
	 */
	private void traiteRequete(RMap r) {

	}

	public void updateArriveeTemp(int iD) {
		// La partie graphique a fini son chemin
	}

	public BoiteAuxLettres getBoite() {
		return boite;
	}

	/*
	 * 
	 * 
	 * Patern MVC
	 * 
	 * 
	 * (non-Javadoc)
	 * 
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

	@Override
	public void notifyCoords(int iD, int dep, int ar) {
		// TODO Auto-generated method stub
		
	}
}
