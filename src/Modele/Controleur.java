package Modele;

import java.util.ArrayList;
import java.util.HashMap;

import Exception.FlotteException;
import Exception.RDepartException;

/**
 * <b> La classe qui regit toute la partie Objet </b>
 * 
 * Ses caracteristiques sont les suivantes :
 * <ul>
 * <li><b>HashMap&lsaquo;Integer, Boolean&rsaquo;</b> une hashmap representant
 * l'occupation des places sur le plateau. Le premier parametre etant
 * l'identifiant de la place et le second son occupation</li>
 * <li><b>BoiteAuxLettres</b> elle contient les differentes requetes envoyees au
 * contreleur</li>
 * <li><b>FlotteVehicule</b> contient toutes les voitures auxquelles le
 * contreleur peut donner des ordres</li>
 * </ul>
 * 
 * @author florian + theo
 * @version 0.1
 */
public class Controleur implements Observable, Runnable {

	/**
	 * La liste des points atteignables sou forme d'int comme suivant
	 * <ul>
	 * <li>les cles de 1 a 6 sont les cles des place d'entrees</li>
	 * <li>les cles de 11 a 16 sont les cles des routes</li>
	 * <li>les cles de 21 a 16 sont les cles des places de sorties</li>
	 * <li>La cle 30 est une cle en reference vers le centre.
	 * </ul>
	 * Le second parametre et la disponibilite de la map: true si cette portion
	 * de map est libre false si elle est deja reservee.
	 * 
	 */
	private HashMap<Integer, Boolean> general;
	/**
	 * Une reference sur la boite contenant toute les requete adressees a
	 * l'utilisateur.
	 */
	private BoiteAuxLettres boite;
	private FlotteVehicules maFlotte;
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	private boolean running;

	public Controleur() {
		createHashMap();
		boite = new BoiteAuxLettres();
		running = true;
	}

	/**
	 * Fonction demarrant la simulation en mode manuel
	 * 
	 * @param taille
	 *            le nombre de vehicules au depart
	 */
	public void debutSim(int taille) {
		maFlotte = new FlotteVehicules(taille, boite);

	}

	/**
	 * Constructeur de la Hashmap
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
	 * Fonction permettant de traiter les requetes "en general" selon un ordre
	 * de priorite : elle traitera dans un premier temps les requetes de fin de
	 * mission apres quoi nous traiterons les requetes de liberation de
	 * ressource puis les requetes pour une nouvelle voiture et enfin les
	 * requetes d'update de map
	 */
	public synchronized void traiteRequete() {
		if (boite.getSizeRFintrajet() != 0) {
			traiteRequete(boite.getRFinTrajet());
			try {
				wait(10);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		} else if (boite.getSizeRLib() != 0) {
			traiteRequete(boite.getRLib());
			try {
				wait(10);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		} else if (boite.getSizeRDepart() != 0) {
			traiteRequete(boite.getDepart());

			try {
				wait(10);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		} else if (boite.getSizeRMap() != 0) {

			traiteRequete(boite.getRMap());
			try {
				wait(10);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		} else {
			try {
				wait(1000);
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * Fonction permettant de traiter les requetes de liberation de ressource
	 * 
	 * @param r
	 *            Une requete de liberation de vehicule
	 */
	private synchronized void traiteRequete(RLib r) {
		general.put(r.getLib(), true);

	}

	/**
	 * Fonction utilisee pour traiter les requetes de depart
	 * 
	 * @param r
	 *            Une requete de depart
	 */
	private synchronized void traiteRequete(RDepart r) {

		Passager p = new Passager(r.getDebut(), r.getFin());
		try {
			maFlotte.ajoutListeAttente(p);
		} catch (FlotteException e) {
			// si il n'y a plus de place pour les vehicule

			try {
				wait(1000);
			} catch (InterruptedException e1) {

				e1.printStackTrace();
			}
		}

	}

	/**
	 * Fonction utilisee pour traiter une requete de fin de trajet
	 * 
	 * @param r
	 *            Une requete de fin de trajet
	 */
	private void traiteRequete(RFinTrajet r) {

		maFlotte.liberer(r.getIdentifiant());
	}

	/**
	 * Fonction qui vas comparer sa liste de requetes avec la liste du
	 * controleur. Si toutes les ressources sont disponibles alors elle
	 * reservera les places correspondantes et autorisera le vehicule a partir
	 * 
	 * @param r
	 *            Une request map
	 */
	private void traiteRequete(RMap r) {
		ArrayList<Boolean> m = r.getRequest_map();
		// compteur pour savoir ou on en est dans les boucles
		int i = 1;
		// il y aura au maximum 5 routes reservees + l'iterateur sur ce
		// tableau
		ArrayList<Integer> tab = new ArrayList<Integer>();

		// entier pour savoir combien de true il y a dans la requestMap en
		// general

		int trueInRequete = 0;
		// entier pour connaitre le nombre de true dans la liste general;
		int trueInGeneral = 0;
		for (Boolean b : m) {
			if (b == true) {
				trueInRequete++;
				// si la place est libre
				if (general.get(i)) {
					trueInGeneral++;
					tab.add(i);

				}
			}

			i++;
			// changement de la valeur de i a cause des cles de la hashmap
			if (i == 7) {
				i = 11;
			}
			if (i == 17) {
				i = 21;
			}
			if (i == 27) {
				i = 30;
			}
		}
		// Si le nombre de "true" dans la request map et dans la hashmap general
		// sont
		// egaux alors les ressources sont libres et on peut les reserver +
		// passer
		// le boolean de la voiture en true
		if (trueInRequete == trueInGeneral) {
			// fonction permettant de de metre les ressource en indisponibilite

			for (int j : tab) {
				general.put(j, false);
			}
			maFlotte.lancerVehicule(r.getIdentifiant(), true);
		}
		maFlotte.setMaj(r.getIdentifiant());
	}

	/**
	 * Fonction notifiant a un vehicule que son homologue graphique a termine
	 * son deplacement
	 * 
	 * @param iD
	 *            L'identifiant du vehicule a prevenir
	 */
	public void updateArriveeTemp(int iD) {
		for (Vehicule v : maFlotte.getVehicules()) {
			// On se place sur le vehicule auquel est lie le vehicule graphique
			if (v.getCerveau() != null
					&& v.getCerveau().getiDVehiculeGraphique() == iD) {
				v.getCerveau().setGraphtop(true);
			}
		}
	}

	public void updatePassagersEnAttente() {
		int attdepart = 0;

		for (Vehicule p : maFlotte.getVehicules()) {
			if (p.isDispo() == false) {
				attdepart++;
			}
		}

		for (Observer obs : listObserver)
			obs.updatePassagers(attdepart, maFlotte.listeAttente.size());
	}

	/**
	 * Fonction effectuant une reinitialisation complete du systeme
	 */
	public void resetAll() {
		boite.resetAll();
		maFlotte.resetAll();
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

	public void pauseAll(boolean running) {
		this.running = running;
	}

	public BoiteAuxLettres getBoite() {
		return boite;
	}

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

	public void updateObsVehicules(Observer obs) {
		maFlotte.setObsVehicules(obs);
	}

	@Override
	public void notifyCoords(int iD, int suivant) {

	}

	@Override
	public int notifyDebutMission(int dep) {
		return 0;
	}

	/**
	 * Fonction run() du controleur, lancant regulierement la verification de la
	 * liste d'attente, une mise a jour grphique et le traitement d'une requete
	 */
	@Override
	public void run() {

		while (true) {
			if (running) {
				maFlotte.checkAttente();
				updatePassagersEnAttente();
				traiteRequete();
			} else {
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}
}
