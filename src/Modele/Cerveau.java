package Modele;

public class Cerveau extends Thread {
	private Vehicule corps;
	private RMap requestmap;
	private boolean start, maj, graphtop, fin;
	private int iDVehiculeGraphique;

	public Cerveau(Vehicule v) {
		corps = v;
		start = false; // Indique que le vehicule est autorise a partir
		maj = true; // Indique que la requete a ete traitee
		graphtop = true; // Indique que la voiture (partie graphique) est arrivee au point suivant
		fin = false; // Indique que la mission est terminee
	}

	/**
	 * Run du thread, qui permet de gerer les requetes du vehicule et son
	 * deplacement
	 */
	public void run() {
		int i=1;
		//On calcule le trajet
		corps.findPath(corps.passager);
		requestmap = corps.trajetToMap(corps.trajet);
		while (start != true) {
			if (maj) {
				corps.sendRMap(requestmap);
				maj = false;
			} else {
				try {
					sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//On se positionne au depart
		iDVehiculeGraphique = corps.notifyDebutMission(corps.trajet.get(0));
		//Et on va de points en points
		while (i <corps.trajet.size()) {
			//Si la voiture graphique a termine de bouger
			if(graphtop){
				graphtop=false;
				corps.sendRLib(corps.trajet.get(i-1));
				//Nouvelles coordonnees
				corps.notifyCoords(iDVehiculeGraphique, corps.trajet.get(i));
				i++;
			}else{
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}//Le trajet est termine
		while (fin == false) {
			//Si la voiture graphique a termine de bouger
			if(graphtop){
				graphtop=false;
				corps.notifyCoords(iDVehiculeGraphique, 99); //L'identifiant 99 correspond a la fin du trajet
				corps.sendRFinTrajet(corps.trajet.get(i-1));
				fin = true;
			}else{
				try {
					sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public int getiDVehiculeGraphique() {
		return iDVehiculeGraphique;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public void setMaj(boolean maj) {
		this.maj = maj;
	}

	public void setGraphtop(boolean graphtop) {
		this.graphtop = graphtop;
	}
}
