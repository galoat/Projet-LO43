package Controleur;

import java.util.ArrayList;
import java.util.Random;

import Exception.RDepartException;
import Exception.RequeteException;
import Modele.RDepart;

/**
 * <b>Generateur automatique de requetes</b> Cette classe permet de generer
 * automatiquement, ou a partir d'un fichier, des requetes et de les envoyer
 * dans la BoiteAuxLettres
 * 
 * @see BoiteAuxLettres
 * @author florian + theo
 * @version 1.0
 */
public class RequestGenerator extends Thread {
	private Verificateur verif;
	private int compteur = 0, dep, ar;
	private Random r;
	private boolean running, fichier;
	private ArrayList<RDepart> rdep;

	/**
	 * Le constructeur du RequestGenerator
	 * 
	 * @param verif
	 *            Le Verificateur associe
	 * @param fichier
	 *            Permet de savoir si le mode fichier a ete choisi
	 */
	public RequestGenerator(Verificateur verif, boolean fichier) {
		this.verif = verif;
		r = new Random();
		running = true;
		this.fichier = fichier;
	}

	/**
	 * Initialise le mode fichier ou le mode automatique, puis lance le
	 * compteur, pour generer des requetes de maniere temporelle
	 */
	public void run() {
		if (fichier) {
			//On cree le lecteur
			Lecteur l = new Lecteur(System.getProperty("user.dir")
					+ "/requetes.txt");
			rdep = l.convert();
			//Tant que toutes les requetes n'ont pas ete generees
			while (!rdep.isEmpty()) {
				//Si on est pas en pause
				if (running) {
					//Si le compteur de temps correspond au moment d'apparition de la requete
					while (!rdep.isEmpty()
							&& compteur == rdep.get(0).getTemps()) {
						try {
							verif.newRequest(rdep.get(0).getDebut(), rdep
									.get(0).getFin());
						} catch (RequeteException | RDepartException e) {
							e.printStackTrace();
						}
						rdep.remove(0);
					}
					compteur++;
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					try {
						sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			while (true) {
				if (running) {
					//On genere une requete aleatoire toutes les deux secondes
					if (compteur % 2 == 0) {
						dep = r.nextInt(5) + 1;
						ar = r.nextInt(5) + 1;
						while (dep == ar) {
							ar = r.nextInt(5) + 1;
						}
						try {
							verif.newRequest(dep, ar);
						} catch (RequeteException | RDepartException e) {
							e.printStackTrace();
						}
					}
					compteur++;
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void reset() {
		compteur = 0;
	}
}
