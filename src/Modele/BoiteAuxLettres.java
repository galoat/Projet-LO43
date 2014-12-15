package Modele;

import java.util.LinkedList;

/**
 * <b>BoiteAuxLettres est la classe contenant toutes les requ&ecirc;tes
 * adress&eacute;es au contr&ocirc;leur</b>
 * <p>
 * Un membre de BoiteAuxLettres est caractériser par les information suivante :
 * <ul>
 * <li>Une liste de requete conentant les requete de fin de rajet</li>
 * <li>Une liste de requete contenant les requete de debut de trajet</li>
 * <li>Une liste de requete conetant les requete d'update de la map d'un
 * vehicule</li>
 * 
 * </ul>
 * 
 * @author florian + theo
 * @version 0.1
 */
public class BoiteAuxLettres {
	/**
	 * La liste des requete. Elle est modifier par le vehicule ou la vue.
	 * Requete de fin. <
	 * 
	 * @see BoiteAuxLettres#addRequete(RFin)
	 * @see getRequete
	 */
	private LinkedList<RFinTrajet> rFin;
	/**
	 * La liste des requete. Elle est modifier par le vehicule ou la vue.
	 * Requete de depart. <
	 * 
	 * @see BoiteAuxLettres#addRequete(RDepart)
	 * @see BoiteAuxLettres#getRequete
	 */
	private LinkedList<RDepart> rDepart;

	private LinkedList<RMap> rMap;
	/**
	 * liste contenant toute les requetes qui sont utiliser pour librere les ressource
	 */
	private LinkedList<RLib> rLib;

	/**
	 * Contructeur par default de BoiteAuxLettres
	 * <p>
	 * Lors de la construction la boite aux lettre ne possede aucune requete.
	 * 
	 */
	BoiteAuxLettres() {
		rLib=new LinkedList<RLib>();
		rFin = new LinkedList<RFinTrajet>();
		rDepart = new LinkedList<RDepart>();
		rMap = new LinkedList<RMap>();
	}
	/**
	 * *
	 * fonction servant a ajouter une requete de liberation  On ajoute cette requete
	 * a la suite des autre requete de liberation.
	 * 
	 * @param req
	 *            La requete de liberation.
	 * 
	 */
	 public void addRequete(RLib req){
		 rLib.add(req);
	 }
	/**
	 * fonction servant a ajouter une requete de depart On ajoute cette requete
	 * a la suite des autre requete de depart.
	 * 
	 * @param req
	 *            La requete de depart.
	 * 
	 */
	public void addRequete(RDepart req) {
		rDepart.add(req);
	}

	/**
	 * fonction permetant d'ajouter une requete de fin de trajet cette requet
	 * etant prioritaire elle se trouve au debut de la liste.
	 * 
	 * @param req
	 *            La requete de fin de trajet;
	 */
	public void addRequete(RFinTrajet req) {
		rFin.add(req);
	}

	/**
	 * fonction permetant d'ajouter une requete d'upload de map
	 * 
	 * @param req
	 *            La requete de mise a jour de la carte
	 */
	public void addRequete(RMap req) {
		rMap.add(req);
	}

	/**
	 * Fonction permettant de recuperer les Requete de fin de trajet contenant
	 * de la boite au lettre
	 * 
	 *
	 */

	public RFinTrajet getRFinTrajet() {
		RFinTrajet r = new RFinTrajet();
		// on recupere le sernier element
		r.clone(rFin.get(rFin.size() - 1));
		rFin.remove(getSizeRFintrajet() - 1);
		return r;

	}

	/**
	 * Fonction permettant de recuperer les Requete de fin de trajet contenant
	 * de la boite au lettre
	 * 
	 */

	public RDepart getDepart() {
		RDepart r = new RDepart();
		// on recupere le sernier element
		r.clone(rDepart.get(rDepart.size() - 1));
		rDepart.remove(getSizeRDepart() - 1);
		return r;

	}
	/**
	 * fonction permettant de recuperer la requete de liberation dees ressource
	 */
	public RLib getRLib(){
		RLib r= new RLib();
		r.clone(rLib.get(rLib.size()-1));
		rLib.remove(rLib.size()-1);
		return r;
	}
	/**
	 * Fonction premetant de recuperer les requete d'update de map
	 */
	public RMap getRMap() {
		RMap r = new RMap();
		r.clone(rMap.get(rMap.size() - 1));
		rMap.remove(getSizeRMap() - 1);
		return r;
	}

	/**
	 * 
	 * fonction premertant de savoir la taille de la liste de requete de fin de
	 * trajet
	 */
	public int getSizeRFintrajet() {
		return rFin.size();
	}

	/**
	 * 
	 * fonction premertant de savoir la taille de la liste de requete de debut
	 * de trajet
	 */
	public int getSizeRDepart() {
		return rDepart.size();
	}

	/**
	 * 
	 * fonction premertant de savoir la taille de la liste de requete de mise a
	 * jour de la carte d'une voiture
	 */
	public int getSizeRMap() {
		return rMap.size();
	}
	/**
	 * 
	 * fonction premertant de savoir la taille de la liste de requete de liberation de ressource
	 * 
	 **/
	public int getSizeRLib(){
		return rLib.size();
	}
}
