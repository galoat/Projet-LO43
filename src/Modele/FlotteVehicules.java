package Modele;

import java.util.ArrayList;

import Exception.FlotteException;

/**
 * <b>FlotteVehicules est la classe representant tous les vehicules sur la
 * plateau</b>
 * <p>
 * Un membre de FlotteVehicules est caracteriser par :
 * <ul>
 * <li>un integer representant le nombre total de vehicule present sur le
 * plateau</li>
 * <li>une Hasmap representant les identifiant des vehicule ainsi que la
 * possibilité de l'embaucher.</li>
 * </ul>
 * 
 * @author flacour+theo
 * @version 0.1
 */
public class FlotteVehicules {
	/**
	 * Le nombre de vehicule libre sur la map, cette entier n'est pas modifiable
	 * par une autre classe
	 */
	ArrayList<Passager> listeAttente;

	private int capacite;
	/**
	 * Une Hashmap contenant :
	 * <ul>
	 * <li>un Integer qui est l'identifiant du vehicule est qui est aussi la
	 * clée de cette hasmap</li>
	 * <li>un boolean permetant de savoir si le vehicule est true si le vehicule
	 * est libre false sinon</li>
	 * </ul>
	 *
	 */
	// private HashMap<Integer, Boolean> vehicule;
	private ArrayList<Vehicule> vehicules;

	public synchronized void setMaj(int ID) {

		int i = 0;
		while (vehicules.get(i).getID() != ID) {
			i++;
		}
		vehicules.get(i).getCerveau().setMaj(true);
	}

	public ArrayList<Vehicule> getVehicules() {
		return vehicules;
	}

	/**
	 * 
	 */
	public void ajoutListeAttente(Passager m) {
		listeAttente.add(m);
	}

	/**
	 * Constructeur de FlotteVehicules
	 * 
	 * @param c
	 *            Entier contenant la capaciter en voiture du plateau
	 */

	FlotteVehicules(int c, BoiteAuxLettres boite) {
		capacite = c;
		// vehicule=new HashMap<Integer, Boolean>();
		vehicules = new ArrayList<Vehicule>();
		for (int i = 0; i < c; i++) {
			// vehicule.put(i,true);
			vehicules.add(new Vehicule(i, boite));
		}
		listeAttente = new ArrayList<Passager>();
	}

	/**
	 * Fonction permetant de donner un passager a une voiture
	 * 
	 * @param m
	 *            Un passager
	 */
	// exception si la liste des vehicule est compléte ?
	public synchronized void donnerPassager(Passager m) throws FlotteException {
		listeAttente.add(m);
	}

	
	public void resetAll(){
		listeAttente.clear();
		for (Vehicule v : vehicules){
			if(!v.isDispo()){
				v.setDispo(true);
				v.getCerveau().stop();
			}
		}
	}
	public synchronized void checkAttente() {

		// System.out.println("verification liste attente vehicule");
		boolean allUse = true;
		for (Vehicule v : vehicules) {
			if (v.isDispo() == true) {
				allUse = false;
			}
		}
		if (listeAttente.size() == 0 || allUse == true) {

			try {
				wait(500);	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			int j = 0;
			while ( j<listeAttente.size()) {
				Passager p = listeAttente.get(j);

				boolean dispo = false;
			
				// verification qu'il reste des vehicule libre
				for (Vehicule c : vehicules) {
					if (c.isDispo()) {
						dispo = true;
					}
				}
				if (dispo == true) {

					System.out.println(" traite demande de " + p.getDebut()
							+ " a" + p.getFin());

						int i = 0;
					//on va cherche le vehicule dispo
					while (i < vehicules.size()-1 && !vehicules.get(i).isDispo()) {
						i++;
					}
						p.setEmbarque(true);
						vehicules.get(i).setDispo(false);
						vehicules.get(i).setPassager(p);
						vehicules.get(i).setCerveau(new Cerveau(vehicules.get(i)));
						vehicules.get(i).getCerveau().start();
						listeAttente.remove(p);
						j--;
				} else {
					System.out.println("liste en attente");
				}
				j++;
			}
		}
	}

	public synchronized void liberer(int iD) {
		int i = 0;
		while (vehicules.get(i).getID() != iD) {
			i++;
		}
		vehicules.get(i).getCerveau().interrupt();
		vehicules.get(i).setDispo(true);
		vehicules.get(i).setPassager(null);
		vehicules.get(i).setCerveau(null);
	}

	public synchronized void lancerVehicule(int iD, boolean start) {
		int i = 0;
		
		while (vehicules.get(i).getID() != iD) {
			i++;
		}

		vehicules.get(i).getCerveau().setMaj(true);
		vehicules.get(i).getCerveau().setStart(start);
	}

	public void setObsVehicules(Observer obs) {
		for (Vehicule v : vehicules) {
			v.addObserver(obs);
		}
	}

	/**
	 * Retourn la capaciter en nombre de vehicule du plateau
	 * 
	 * @return la capaciter en nombre de vehicule du plateau
	 * @see capaciter
	 */

	public int getCapacite() {
		return capacite;
	}

}
