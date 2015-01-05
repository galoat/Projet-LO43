package Modele;

import Exception.RDepartException;

/**
 * <b> RDepart est la classe permettant de demander au controleur une voiture
 * pour un trajet</b>
 * <p>
 * Un membre de RDepart est caracteriser par les information suivante :
 * <ul>
 * <li>Un entier permetant d'identifier le point de depart</li>
 * <li>Un entier permetant d'identifier le point d'arriver</li>
 * </ul>
 * 
 * @author florian + theo
 * @version 0.1
 */
public class RDepart {
	/**
	 * Un entier representant la place d'entree du vehicule (tel qu'affichee
	 * pour l'utilisateur, c'est a dire 1 a 6)
	 */
	private int debut;
	/**
	 * Un entier utilise pour la generation de requetes a partir d'un fichier
	 */
	private int temps;
	/**
	 * un entier representant la place de sortie du vehicule (tel qu'affichee
	 * pour l'utilisateur, c'est a dire 1 a 6)
	 */
	private int fin;

	/**
	 * Constructeur par default
	 * 
	 */
	RDepart() {

	}

	/**
	 * Constructeur avec parmaametre de RDepart
	 * 
	 * @param d
	 *            le point de depart de la voiture
	 * @param f
	 *            Le point d'arriver de la voiture
	 * @throws RDepartException
	 *             si l'entier f ou d est superieur a 6 ou inferieur a 1 ou
	 *             encore si on commence a la m�me place
	 */
	public RDepart(int d, int f) throws RDepartException {
		if (d > 6 || f > 6 || f < 1 || d < 1 || f == d) {
			new RDepartException();
		}
		debut = d;
		fin = f;
	}
	
	/**
	 * Constructeur avec parmaametre de RDepart
	 * 
	 * @param d
	 *            le point de depart de la voiture
	 * @param f
	 *            Le point d'arriver de la voiture
	 * @param t Le temps (en secondes) que la requete mettra a etre lancee
	 * @throws RDepartException
	 *             si l'entier f ou d est superieur a 6 ou inferieur a 1 ou
	 *             encore si on commence a la meme place
	 */
	public RDepart(int d, int f, int t) throws RDepartException {
		if (d > 6 || f > 6 || f < 1 || d < 1 || f == d) {
			new RDepartException();
		}
		debut = d;
		fin = f;
		temps = t;
	}

	/**
	 * Fonction permertant de connaitre le debut du trajet demander
	 * 
	 * @return le debut du trajet pout cette requete
	 */
	public int getDebut() {
		return debut;
	}

	/**
	 * Fonction permettant de conntaitre la fin du trajet demander
	 * 
	 * @return la fin du trajet demander
	 */
	public int getFin() {
		return fin;
	}

	public void clone(RDepart r) {
		this.debut = r.debut;
		this.fin = r.fin;
	}

	public int getTemps() {
		return temps;
	}

}
