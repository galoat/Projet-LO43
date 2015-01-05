package Controleur;

import Exception.RDepartException;
import Exception.RequeteException;
import Modele.Controleur;
import Modele.Observer;
import Modele.RDepart;

public class Verificateur {
	protected Controleur model;
	private RequestGenerator reqGen;
	private boolean auto;

	/**
	 * Constructeur du Verifcateur
	 * 
	 * @param c
	 *            Le Controleur qui recevra les donnees transmises
	 * 
	 */
	public Verificateur(Controleur c) {
		model = c;
	}

	public void notifArrivee(int iD) {
		model.updateArriveeTemp(iD);
	}

	public void obsVehicules(Observer obs) {
		model.updateObsVehicules(obs);
	}

	/**
	 * Fabrique une nouvelle requete a partir de donnees, et les place dns la
	 * boite aux lettres
	 * 
	 * @param dep
	 *            L'identifiant de la place de depart
	 * @param ar
	 *            L'identifiant de la place d'arrivee
	 */
	public void newRequest(int dep, int ar) throws RequeteException,
			RDepartException {
		if (dep != ar) {
			RDepart m = new RDepart(dep, ar);
			model.getBoite().addRequete(m);
		} else {
			throw new RequeteException();
		}

	}

	/**
	 * Initialise le generateur de requetes (ou non, en fontion des parametres),
	 * et ordonne au controleur de s'initialiser
	 * 
	 * @param tailleflotte Le nobre de vehicules compris dans la flotte de vehicules
	 * @param auto Indique si le mode automatique/fichier est actif
	 * @param fichier Indique si le mode fichier est actif
	 * 
	 */
	public void debutSim(int tailleflotte, boolean auto, boolean fichier) {
		//On initialise
		model.debutSim(tailleflotte);
		//Puis on lance le thread du controleur
		Thread t = new Thread(model);
		this.auto = auto;
		t.start();
		//Puis, si besoin, on initialise le generateur
		if (auto) {
			if (fichier) {
				reqGen = new RequestGenerator(this, true);
			} else {
				reqGen = new RequestGenerator(this, false);
			}
			reqGen.start();

		}
	}
	/**
	 *	Ordonne la reinitialisaton complete du systeme
	 * 
	 */
	public void resetAll() {
		model.resetAll();
		if (auto) {
			reqGen.reset();
			;
		}
	}

	public void pause(boolean running) {
		model.pauseAll(running);
		if (auto) {
			reqGen.setRunning(running);
		}
	}

}
