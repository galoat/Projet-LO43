package Modele;

import java.util.LinkedList;

import Exception.BoiteAuxLettresException;
/**
 * <b>BoiteAuxLettres est la classe contenant toutes les requ&ecirc;tes adress&eacute;es au contr&ocirc;leur</b>
 * <p>
 * Un membre de BoiteAuxLettres est caractériser par les information suivante :
 * <ul>
 * <li> Une liste de requete conentant les requete de fin de rajet</li>
 * <li> Une liste de requete contenant les requete de debut de trajet</li>
 * <li> Une liste de requete conetant les requete d'update de la map d'un vehicule </li>
 * 
 * </ul>
 * @author florian + theo
 *	@version 0.1
 */
public class BoiteAuxLettres {
	/**
	 * La liste des requete. Elle est modifier par le vehicule ou la vue.
	 * Requete de fin.
	 * <
	 * @see BoiteAuxLettres#addRequete(RFin)
	 * @see getRequete
	 */
	private LinkedList<Requete> rFin;
	/**
	 * La liste des requete. Elle est modifier par le vehicule ou la vue.
	 * Requete de depart.
	 * <
	 * @see BoiteAuxLettres#addRequete(RDepart)
	 * @see BoiteAuxLettres#getRequete
	 */
	private LinkedList<Requete> rDepart;
	

	private LinkedList<Requete> rMap;
	
	/**
	 * Contructeur par default de BoiteAuxLettres
	 * <p>
	 * Lors de la construction la boite aux lettre ne possede aucune requete.
	 * 
	 */
	BoiteAuxLettres(){
		
		rFin=new LinkedList<Requete>();
		rDepart=new LinkedList<Requete>();
		rMap=new LinkedList<Requete>();
	}
	/**
	 * fonction servant a ajouter une requete de depart
	 * On ajoute cette requete a la suite des autre requete de depart.
	 * @param req 
	 * 			La requete de depart.
	 *  
	 */
	public void addRequete(RDepart req){
		rDepart.add(req);
	}
	/**
	 * fonction permetant d'ajouter une requete de fin de trajet cette requet etant prioritaire elle se trouve au debut de la liste.
	 * @param req	
	 * 			La requete de fin de trajet;
	 */
	public void addRequete(RFinTrajet req){
		rFin.add(req);
	}
	/**
	 * fonction permetant d'ajouter une requete d'upload de map
	 * @param req 
	 * 				La requete de mise a jour de la carte
	 */
	public void addRequete(RMap req){
		rMap.add(req);
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
		if(rFin.size()==0&& rDepart.size()==0&&rMap.size()==0){
			throw new BoiteAuxLettresException();
		}
		else{
			Requete sortie;
		if(rFin.size()!=0){
			
			sortie =rFin.get(0);
			rFin.removeFirst();
			return sortie;
		}
		else if(rDepart.size()!=0){
			sortie =rDepart.get(0);
			rDepart.removeFirst();
			return sortie;
		}
		else{
			sortie =rMap.get(0);
			rMap.removeFirst();
			return sortie;
		}
		
	}
}
}