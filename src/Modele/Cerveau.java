package Modele;

public class Cerveau extends Thread {
	private Vehicule corps;
	private RMap requestmap;
	private boolean start, maj, graphtop;
	private int iDVehiculeGraphique;

	public Cerveau(Vehicule v) {
		corps = v;
		start = false; // Indique que le vehicule est autorise a partir
		maj = true; // Indique que la requete a ete traitee
		graphtop = true; // Indique que la voiture (partie graphique) est arrivee au point suivant
	}

	/**
	 * Run du thread, qui permet de gerer les requetes du vehicule et son
	 * deplacement
	 * 
	 * @param p
	 *            Un passager, indiquant les places de d&eacute;part et
	 *            d'arriv&eacute;e
	 */
	public void run() {
		int i=0;
		corps.findPath(corps.passager);
		requestmap = corps.trajetToMap(corps.trajet);
		while (start != true) {
			if (maj) {
				corps.sendRMap(requestmap);
				maj = false;
			} else {
				try {
					wait(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//On se positionne au depart
		iDVehiculeGraphique = corps.notifyDebutMission(corps.trajet.get(0));
		
		//Et on va de points en points
		while (i < corps.trajet.size()-1) {
			//Si la voiture graphique a termine de bouger
			if(graphtop){
				graphtop=false;
				i++;
				//Nouvelles coordonnees
				corps.notifyCoords(iDVehiculeGraphique, corps.trajet.get(i));
			}else{
				try {
					wait(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}//Le trajet est termine
		corps.notifyCoords(iDVehiculeGraphique, 99); //L'identifiant 99 correspond e la fin du trajet
		corps.sendRFinTrajet();
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
