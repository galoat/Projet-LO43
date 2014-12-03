package Modele;

import java.util.ArrayList;
/**
 * <b> vehicule est la classe contenant toute les voiture autonome
 * <p>
 * Un membre de Vehicule est caracteriser par les information suivante
 * <ul>
 * <li>un tableau de coolean permetant de savoir quel chemin le vehicule desire prendre ( dans la fiche projet elle est nomer maprequest</li>
 * <li> un boolean permetant de savoir si la voiture peut partir.</li>
 * <li> un arraylist de int contenant la pririter des route ce tableau est utile pour la reservation </li>
 * <li>un passager qui demande a la voiture d'aller d'un point a a un point b </li>
 * <li> un identifiant permetant de savoir de quel vehicule il s'agit.
 * </ul>
 * 
 * @author florian+theo
 *@version 0.1
 */
//ICI GESTION DE THREAD
public class Vehicule {
	
	/**
	 * L'identidiant du vehicule ne peut pas etre modifier
	 */
		int ID;
	
	/**
	 * Tableau d'Integer contenant le trajet que le vehicule souhaite suivre,
	 * sous forme d'une suite d'identifiants. Celui-ci sera ensuite converti en
	 * une Request_Map, afin de demander au Controleur l'autorisation de
	 * l'emprunter.
	 */
	private ArrayList<Integer> trajet;

	/**
	 * passager permetant de connaitre le point de depart et d'arriver du
	 * vehicule
	 */
	private Passager passager;

	/**
	 * constructeur par default du vehicule.
	 */
	Vehicule() {

	}

	/**
	 * constructeur permetant de creer un vehicule et de lui assigner une
	 * mission
	 * 
	 * @param m
	 *            Une mission
	 */
	Vehicule(Passager m, int ID) {
		this.ID = ID;
		this.trajet = new ArrayList<Integer>();
		this.passager = m;
	}

	/**
	 * Fonction permettant de trouver un chemin de la place de départ vers celle d'arrivée
	 */
	public void findPath(Passager p){
		trajet.clear();
		int dep = p.getDebut();
		int fin = p.getFin();
		int a, b;
		int count = -1;
		int[] l1 = new int[3];
		int[] l2 = new int[3];
		
		trajet.add(dep-10);
		if(Math.abs(dep-fin)==3){
			trajet.add(dep);
			trajet.add(30);
		}else{
			a=dep;
			b=dep;
			while(a!=fin && b!=fin){
				count++;
				l1[count]=a;
				l2[count]=b;
				if(a==16){
					a=10;
				}if(b==11){
					b=17;
				}
				a++;
				b--;
			}if(b==fin){
				l1=l2;
			}for(int i=0; i<l1.length; i++){
				if(l1[i]!=0){
					trajet.add(l1[i]);
				}
			}
		}trajet.add(fin);
		trajet.add(fin+10);
	}
	
	/**
	 * Fonction permettant de convertir un trajet en une RMap
	 */
	public RMap trajetToMap(ArrayList<Integer> l){
		RMap rq = new RMap(ID,trajet);
		for(int i : l){
			if(i-30>=0){
				rq.getRequest_map().set(18, true);
			}else{
				if(i-20>=0){
					rq.getRequest_map().set(i-9, true);
				}else{
					if(i-10>=0){
						rq.getRequest_map().set(i-5, true);
					}else{
						rq.getRequest_map().set(i-1, true);
					}
				}
			}
		}
		return rq;
	}
	
}
