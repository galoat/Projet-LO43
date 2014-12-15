package Modele;

public class Cerveau extends Thread {
	private Vehicule corps;
	private RMap requestmap;
	private boolean start, maj, graphtop;

	public Cerveau(Vehicule v) {
		corps = v;
		start = false; // Indique que le vehicule est autorise a partir
		maj = true; // Indique que la requete a ete traitee
		graphtop = true; // Indique que la voiture (partie graphique) est arrivee au point suivant
	}

	/**
	 * Run du thread, qui permet de gérer les requêtes du véhicule et son
	 * déplacement
	 * 
	 * @param p
	 *            Un passager, indiquant les places de d&eacute;part et
	 *            d'arriv&eacute;e
	 */
	public void run() {
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
		while (true) {
			//Trajet
			
		}
	}
}
