package Modele;

import java.util.LinkedList;

import Exception.BoiteAuxLettresException;
/**
 * <b>BoiteAuxLettres est la classe contenant toutes les requ&ecirc;tes adress&eacute;es au contr&ocirc;leur</b>
 * <p>
 * Un membre de BoiteAuxLettres est caractériser par les information suivante :
 * <ul>
 * <li> Une liste de requ&ecirc;tes</li>
 * <li> Un entier repres&eacute;ntant le d&eacute;but des requ&ecirc;tes de d&eacute;but de trajet</li>
 * </ul>
 * @author florian + theo
 *	@version 0.1
 */
public class BoiteAuxLettres {
	/**
	 * La liste des requete. Elle est modifier par le vehicule ou la vue.
	 * Les requete ont un ordre:
	 * <ul>
	 * <li> En premier les requete de fin de trajet.</li>
	 * <li> Les requete de mise a jour des disponibilite</li>
	 * <li> Les requete pour embaucher un nouveau vehicule</li>
	 * </ul>
	 * @see BoiteAuxLettres#addRequete(RDepart)
	 */
	private LinkedList<Requete> boite;
	/**
	 *Un entier representant la position des requete de debut de trajet dans la liste des requete de la boite. 
	 * 
	 */
	private int posDebutTrajet;
	/**
	 * Contructeur par default de BoiteAuxLettres
	 * <p>
	 * Lors de la construction la boite aux lettre ne possede aucune requete.
	 * 
	 */
	BoiteAuxLettres(){
		posDebutTrajet=0;
		boite=new LinkedList<Requete>();
	}
	/**
	 * fonction servant a ajouter une requete de depart
	 * On ajoute cette requete a la suite des autre requete de depart.
	 * @param req 
	 * 			La requete de depart.
	 *  
	 */
	public void addRequete(RDepart req){
		boite.add(posDebutTrajet,req);
		posDebutTrajet++;
	}
	/**
	 * fonction permetant d'ajouter une requete de fin de trajet cette requet etant prioritaire elle se trouve au debut de la liste.
	 * @param req	
	 * 			La requete de fin de trajet;
	 */
	public void addRequete(RFinTrajet req){
		boite.add(0,req);
	}
	/**
	 * fonction permetant d'ajouter une requete d'upload de map
	 * @param req 
	 * 				La requete de mise a jour de la carte
	 */
	public void addRequete(RMap req){
		boite.add(req);
		
	}
	
	/**
	 * Fonction permettant de recuperer le premier contenant de la boite au lettre
	 * 
	 * @return requete
	 * 				La premiére requete de la liste.
	 *@throws BoiteAuxLettresException 
	 *				if the boite is empty
	 */
	
	public Requete getFirst() throws BoiteAuxLettresException{
		if(boite.size()==0){
			throw new BoiteAuxLettresException();
		}
		else{
		Requete sortie=boite.get(0);	
		boite.remove(0);
		return sortie;}
	}
}
