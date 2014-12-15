package Modele;

import java.util.ArrayList;

/**
 * <b> Vehicule est la classe contenant toutes les voitures autonomes
 * <p>
 * Un membre de Vehicule est compos&eacute; des caract&eacute;ristiques
 * suivantes
 * <ul>
 * <li>Un tableau d'Integer permettant de savoir quel chemin le v&eacute;hicule
 * d&eacute;sire prendre</li>
 * <li>Un passager, qui demande &agrave; la voiture d'aller d'un point a
 * &agrave; un point b</li>
 * <li>Un identifiant unique pour ce V&eacute;hicule
 * </ul>
 * 
 * @author florian+theo
 * @version 0.1
 */
// ICI GESTION DE THREAD
public class Vehicule implements Observable {

	/**
	 * L'identifiant du v&eacute;hicule ne peut pas etre modifi&eacute; et est
	 * unique
	 */
	int ID;
	int graphID;

	/**
	 * Tableau d'Integer contenant le trajet que le v&eacute;hicule souhaite
	 * suivre, sous forme d'une suite d'identifiants. Celui-ci sera ensuite
	 * converti en une Request_Map, afin de demander au Controleur
	 * l'autorisation de l'emprunter.
	 */
	protected ArrayList<Integer> trajet;
	private Observer obs;
	private BoiteAuxLettres boite;
	public boolean dispo;
	/**
	 * Passager permettant de conna&icirc;tre le point de depart et
	 * d'arriv&eacute;e du v&eacute;hicule
	 */
	protected Passager passager;

	/**
	 * Constructeur permettant de cr&eacute;er un v&eacute;hicule et de lui
	 * assigner une mission et une identit&eacute;
	 * 
	 * @param p
	 *            Le passager
	 */
	Vehicule(int ID, BoiteAuxLettres boite) {
		this.ID = ID;
		this.dispo = true;
		this.trajet = new ArrayList<Integer>();
		// this.passager = p;
		this.boite = boite;
	}

	/**
	 * Fonction permettant de trouver un chemin de la place de d&eacute;part
	 * vers celle d'arriv&eacute;e
	 * 
	 * @param p
	 *            Un passager, indiquant les places de d&eacute;part et
	 *            d'arriv&eacute;e
	 */
	public void findPath(Passager p) {
		// On vide la liste
		trajet.clear();
		// On stocke les places de depart et de fin (on parle ici des places R,
		// pas des places d'entree ou de sortie)
		int dep = p.getDebut();
		int fin = p.getFin();
		int a, b;
		int count = -1;
		// On cree deux tableaux pour parcourir la carte dans les deux sens
		int[] l1 = new int[3];
		int[] l2 = new int[3];
		// On commence par la place de depart (son identifiant est egal a la
		// place R - 10)
		trajet.add(dep - 10);
		// Si la place d'arrivee est en face, on passe par le centre
		if (Math.abs(dep - fin) == 3) {
			trajet.add(dep);
			trajet.add(30);
		} else {
			// On commence a la place R de depart
			a = dep;
			b = dep;
			// Ensuite, on parcoure la carte dans les deux sens pour determiner
			// le chemin le plus rapide
			while (a != fin && b != fin) {
				count++;
				// On stocke le trajet
				l1[count] = a;
				l2[count] = b;
				// On se charge de faire un cycle (le 6 est suivi du 1)
				// S'agissant des places R, leur identifiant est décalé de 10
				if (a == 16) {
					a = 10;
				}
				if (b == 11) {
					b = 17;
				}
				// On se decale dans les deux sens
				a++;
				b--;
			}// Si le trajet b est le plus court
			if (b == fin) {
				// On le stocke dans le premier tableau
				l1 = l2;
			}// Enfin, on stocke le trajet dans la liste
			for (int i = 0; i < l1.length; i++) {
				if (l1[i] != 0) {
					trajet.add(l1[i]);
				}
			}
		}
		// On termine par la place R finale, puis la place d'arrivee (R + 10)
		trajet.add(fin);
		trajet.add(fin + 10);
	}

	/**
	 * Fonction permettant de convertir un trajet en une RMap
	 */
	public RMap trajetToMap(ArrayList<Integer> trajet) {
		RMap rq = new RMap(ID);
		for (int i : trajet) {
			if (i - 30 >= 0) {
				rq.getRequest_map().set(18, true);
			} else {
				if (i - 20 >= 0) {
					rq.getRequest_map().set(i - 9, true);
				} else {
					if (i - 10 >= 0) {
						rq.getRequest_map().set(i - 5, true);
					} else {
						rq.getRequest_map().set(i - 1, true);
					}
				}
			}
		}
		return rq;
	}

	// Fonction pour envoyer la requestmap a la boite aux lettres
	public void sendRMap(RMap rmap) {
		boite.addRequete(rmap);
	}

	@Override
	public void addObserver(Observer obs) {
		this.obs=obs;

	}

	@Override
	public void removeObserver() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyCoords(int iD, int dep, int ar) {
		obs.updateCoords(iD, dep, ar);
	}

	@Override
	public void notifyObserver(String str) {
		// TODO Auto-generated method stub
		
	}

}
