package Modele;
/**
 * <b> Passager est la classe qui donne les point oiu doit aller el vehicule
 * <p> Les parametre sont les suivant
 * <ul>
 * <li>un interger indiquant le point de depart de la course</li>
 * <li> un integer indiquant le point d'arriver de la course </li>
 * </ul>
 * @author flacour +theo
 *
 */
public class Passager {
/**
 * un entier representant le debut du trajet
 */
	int debut;
	/**
	 * UN entier representant la fin du trajetn
	 */
	int fin;
	/**	
	 * Constructeur par default de Passager
	 * @param debut
	 * 				Le point de depart de la course
	 * @param fin
	 * 				Le point de fin de la course
	 */
	Passager(int debut, int fin){
		this.debut=debut;
		this.fin=fin;
	}
	/**
	 * retourne le debut de la course
	 * @return retourne le debut de la course
	 */
	public int getDebut() {
		return debut;
	}
	/**
	 * retourne la fin de la course
	 * @return retourne la fin de la course
	 */
	public int getFin() {
		return fin;
	}
	

	
}
