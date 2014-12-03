package Modele;
/**
 * <b> RFinTrajet est la classe permettant de demander au controleur de suprimer un vehicule</b>
 * <p>
 * Un membre de RFinTrajet est caracteriser par les information suivante :
 * <ul>
 * <li>Un entier permetant d'identifier la voiture</li>
 * 
 * </ul>
 * @author florian + theo
 *	@version 0.1
 */
public class RFinTrajet extends Requete {
	/**
	 * Un entier permetant d'identifier la voiture fesant la demande
	 * Il n'est pas modifiable mais on peut le lire
	 */
 private int identifiant;
 RFinTrajet(int idendifiant){
	 this.identifiant=idendifiant;
 }
 
 /**
  * fonction premetant de connaitre l'identifiant de la voiture
  * @return identifiant
  * 					un entier representant l'identifiant de la voiture
  */
public int getIdentifiant() {
	return identifiant;
}

}
