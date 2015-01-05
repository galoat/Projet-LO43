package Modele;
/**
 * <b> Passager est la classe qui donne les points de depart et d'arrivee du vehicule</b>
 * Les parametres sont les suivants
 * <ul>
 * <li> Un integer indiquant le point de depart de la course</li>
 * <li> Un integer indiquant le point d'arrivee de la course </li>
 * </ul>
 * @author florian +theo
 *
 */
public class Passager {
/**
 * Un entier representant le debut du trajet
 */
	int debut;
	/**
	 * Un entier representant la fin du trajet
	 */
	int fin;
	
	/**
	 * Un booleen indiquant que le passager est dans un vehicule ou non
	 */
	boolean embarque;
	
	/**	
	 * Constructeur par defaut de Passager
	 * @param debut
	 * 				Le point de depart de la course
	 * @param fin
	 * 				Le point de fin de la course
	 */
	Passager(int debut, int fin){
		this.debut=debut;
		this.fin=fin;
		this.embarque = false;
	}
	public int getDebut() {
		return debut;
	}
	public int getFin() {
		return fin;
	}
	public boolean isEmbarque() {
		return embarque;
	}
	public void setEmbarque(boolean embarque) {
		this.embarque = embarque;
	}
	

	
}
