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
	 * mission apres quoi nous traiterons les requete de liberation de ressource
	 * puis les requete pour une nouvelle voiture et enfin les requete d'update
	 * de map
	 * 
	 * @throws InterruptedException
	 */
	public void traiteRequete() {
		if (boite.getSizeRFintrajet() != 0) {
			traiteRequete(boite.getRFinTrajet());
			try {
				wait(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (boite.getSizeRLib() != 0) {
			traiteRequete(boite.getRLib());
			try {
				wait(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (boite.getSizeRDepart() != 0) {
			traiteRequete(boite.getDepart());
			try {
				wait(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (boite.getSizeRMap() != 0) {
			traiteRequete(boite.getRMap());
			try {
				wait(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
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
	 * 
	 * @param r
	 *            une requete de liberation de vehicule
	 */
	private void traiteRequete(RLib r) {
		general.put(r.getLib(), true);
	}

	/**
	 * fonction utiliser pour traiter les requete de depart
	 * 
	 * @param r
	 *            une requete de depart
	 */
	//A CHANGER EN PRIVATE
	public void traiteRequete(RDepart r) {

		Passager p = new Passager(r.getDebut(), r.getFin());
		try {
			maFlotte.donnerPassager(p);
		} catch (FlotteException e) {
			// si il n'y a plus de place pour les vehicule
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
	 * 
	 * @param r
	 *            une requete de fin de trajet
	 */
	private void traiteRequete(RFinTrajet r) {
		maFlotte.liberer(r.getIdentifiant());
	}

	/**
	 * Focntion qui vas comparer sa liste de requet avec la liste du controleur
	 * si toute les ressource sont disponnible alors elle reserveras sont chemin
	 * dans la liste principal et changeras le boolean du vehicule en true.
	 * 
	 * @param r
	 *            une request map
	 */
	private void traiteRequete(RMap r) {
		ArrayList<Boolean> m = r.getRequest_map();
		// compteur pour savoir ou on en est dans les boucles
		int i = 0;
		// il y aura au maximum 5 routes de reserver+ l'ietreateur sur ce
		// tableau
		int[] tab = new int[5];
		int iterateurTab = 0;
		// entier pour savoir combien de true il y a dans la requestMapr e
		// general

		int trueInRequete = 0;
		// entie pour connaitre le nombre de true dans la liste general;
		int trueInGeneral = 0;
		for (Boolean b : m) {
			if (b == true) {
				trueInRequete++;
				// si la place est libre
				if (general.get(i)) {
					trueInGeneral++;
					tab[iterateurTab] = i;
					iterateurTab++;
				}
			}
			i++;
		}
		// si le nombre true dans la request map et dans la hasmap general sont
		// egaux alors les ressource sont libre et on peut les reserver+passer le boolean de la voiture en true
		if(trueInRequete==trueInGeneral){
			//fonction permettant de de metre les ressource en indisponibilité
			for(int j=0;j<i;j++){
				general.put(tab[i], false);
			}
			maFlotte.lancerVehicule(r.getIdentifiant(), true);
		}else{
			maFlotte.lancerVehicule(r.getIdentifiant(), true);
		}
	}

	public void updateArriveeTemp(int iD) {
		for (Vehicule v : maFlotte.getVehicules()) {
			// On se place sur le vehicule auquel est lie le vehicule graphique
			if (v.getCerveau() != null && v.getCerveau().getiDVehiculeGraphique() == iD) {
				v.getCerveau().setGraphtop(true);
			}
		}
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
	
	public void updateObsVehicules(Observer obs){
		maFlotte.setObsVehicules(obs);
	}

	@Override
	public void notifyCoords(int iD, int suivant) {
		// TODO Auto-generated method stub

	}

	@Override
	public int notifyDebutMission(int dep) {
		return 0;
	}
}
