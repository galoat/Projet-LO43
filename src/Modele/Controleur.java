package Modele;

import java.util.ArrayList;
import java.util.HashMap;

import Exception.FlotteException;
import Exception.RDepartException;

/**
 * <b> La classe qui regit toute la partie Objet </b>
 * <p>
 * Ses caracteristiques sont les suivantes :
 * <ul>
 * <li><b>HashMap<Integer, Boolean></b> une hashmap representant l'occupation
 * des places sur le plateau. Le premier parametre etant l'identifiant de la
 * place et le second son occupation</li>
 * <li><b>BoiteAuxLettres</b> elle contient les differentes requetes envoyees au
 * contreleur</li>
 * <li><b>FlotteVehicule</b> contient toutes les voitures auxquelles le
 * contreleur peut donner des ordres</li>
 * </ul>
 * 
 * @author florian + theo
 * @version 0.1
 */
public class Controleur implements Observable,Runnable {

	/**
	 * La liste des points atteignables sou forme d'int comme suivant
	 * <ul>
	 * <li>les clees de 1 a 6 sont les clees des place d'entrees</li>
	 * <li>les clees de 11 a 16 sont les clees des routes</li>
	 * <li>les clees de 21 a 16 sont les clees des places de sorties</li>
	 * <li>La clee 30 est une clee en reference vers le centre.
	 * </ul>
	 * Le second parametre et la disponibilité de la map: true si la cette
	 * portion de map est libre false si elle est deja reserver.
	 * 
	 */
	private HashMap<Integer, Boolean> general;
	/**
	 * Une reference sur la boite contenant toute les requete adresser a
	 * l'utilisateur.
	 */
	private BoiteAuxLettres boite;
	private FlotteVehicules maFlotte;
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();

	public Controleur() {
		createHashMap();
		boite = new BoiteAuxLettres();
	}
	/**
	 * fonction appeler lorsque on veut faire une simulation en automatique
	 */
	public void debutSimAuto(int taille){
		maFlotte = new FlotteVehicules(taille, boite);
		while(true){
			int a =generateur();
			int b=generateur();
			if(a!=b){
				RDepart r	=null;
				try {
				 r=new RDepart(a,b);
				} catch (RDepartException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boite.addRequete(r);
			}else
			{
				try {
					wait(100000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}	
	}
/**
 * FOnction demarant la simulation en mode manuel 
 * @param taille
 * 				le nombre devehicule au depart
 */
	public void debutSim(int taille){
		maFlotte = new FlotteVehicules(taille, boite);
		
	}
	/**
	 * Constructeur de la Hashmap
	 * 
	 * @return void
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
	 * Fonction permetant de traiter la requete "general" celon un ordre de
	 * prioriter: elle traiteras dans un premier temp les requete de fin de
	 * mission apres quoi nous traiterons les requete de liberation de ressource
	 * puis les requete pour une nouvelle voiture et enfin les requete d'update
	 * de map
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void traiteRequete() {
		if (boite.getSizeRFintrajet() != 0) {
			traiteRequete(boite.getRFinTrajet());
			System.out.println("fin trajet");
			//System.out.println(general);
			try {
				wait(10);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		} else if (boite.getSizeRLib() != 0) {
			traiteRequete(boite.getRLib());
			System.out.println("liberation");
			try {
				wait(10);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		} else if (boite.getSizeRDepart() != 0) {
			traiteRequete(boite.getDepart());
			System.out.println("depart");
			
			try {
				wait(10);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		} else if (boite.getSizeRMap() != 0) {
			
			traiteRequete(boite.getRMap());
			System.out.println("map");
			try {
				wait(10);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}

		} else {
			try {
			//	System.out.println("rien dans la boite");
				wait(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}

	}

	/**
	 * fonction permettant de traiter les requete de liberation de ressource
	 * 
	 * @param r
	 *            une requete de liberation de vehicule
	 */
	private synchronized void traiteRequete(RLib r) {
		general.put(r.getLib(), true);
		
	}

	/**
	 * fonction utiliser pour traiter les requete de depart
	 * 
	 * @param r
	 *            une requete de depart
	 */
	private synchronized void traiteRequete(RDepart r) {

		Passager p = new Passager(r.getDebut(), r.getFin());
		try {
			maFlotte.donnerPassager(p);
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
	 * fonction utiliser pour traiter une requete de fin de trajet
	 * 
	 * @param r
	 *            une requete de fin de trajet
	 */
	private  void traiteRequete(RFinTrajet r) {

		maFlotte.liberer(r.getIdentifiant());
	}

	/**
	 * Focntion qui vas comparer sa liste de requet avec la liste du controleur
	 * si toute les ressource sont disponnible alors elle reserveras sont chemin
	 * dans la liste principal et changeras le boolean du vehicule en true.
	 * 
	 * @param r
	 *            une request map
	 */
	private  void traiteRequete(RMap r) {
		System.out.println(r.getIdentifiant());
		ArrayList<Boolean> m = r.getRequest_map();
	//	System.out.println(m);
		// compteur pour savoir ou on en est dans les boucles
		int i = 1;
		// il y aura au maximum 5 routes de reserver+ l'ietreateur sur ce
		// tableau
		ArrayList<Integer> tab = new ArrayList<Integer>();
		
		// entier pour savoir combien de true il y a dans la requestMapr e
		// general

		int trueInRequete = 0;
		// entie pour connaitre le nombre de true dans la liste general;
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
			//changement de la valeur de i a cause des clée de la hasmap
			if(i==7){
				i=11;
			}
			if(i==17){
				i=21;
			}
			if(i==27){
				i=30;
			}
		}
		// si le nombre true dans la request map et dans la hasmap general sont
		// egaux alors les ressource sont libre et on peut les reserver+passer le boolean de la voiture en true
		if(trueInRequete==trueInGeneral){
			//fonction permettant de de metre les ressource en indisponibilité
		
			for(int j: tab){
				general.put(j, false);
			}
			maFlotte.lancerVehicule(r.getIdentifiant(), true);	
		}
		//System.out.println("au depart");
		//System.out.println(general);
		maFlotte.setMaj(r.getIdentifiant());
	}
/**
 * fonction servat a generer un nombre alleatoire entre 1 et 6
 * @param 
 */
	private int generateur(){
		return 1 + (int)(Math.random() * 5 + 1);
	}
	public void updateArriveeTemp(int iD) {
		for (Vehicule v : maFlotte.getVehicules()) {
			// On se place sur le vehicule auquel est lie le vehicule graphique
			if (v.getCerveau() != null && v.getCerveau().getiDVehiculeGraphique() == iD) {
				v.getCerveau().setGraphtop(true);
			}
		}
	}

	public void updatePassagersEnAttente(){
		int attdepart = 0;
		System.out.println(maFlotte.listeAttente.isEmpty());
		
		for(Vehicule p: maFlotte.getVehicules()){
			if(p.isDispo()==false){
				attdepart++;
			}
			}
	
		for (Observer obs : listObserver)
			obs.updatePassagers(attdepart, maFlotte.listeAttente.size());
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
	
	public void updateObsVehicules(Observer obs){
		maFlotte.setObsVehicules(obs);
	}

	@Override
	public void notifyCoords(int iD, int suivant) {
	

	}

	@Override
	public int notifyDebutMission(int dep) {
		return 0;
	}

	
	@Override
	public void run() {
		
		while(true){
			maFlotte.checkAttente();
			updatePassagersEnAttente();
			traiteRequete();
		}
	}
}
