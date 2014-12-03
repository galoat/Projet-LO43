package Modele;

import java.util.ArrayList;
/**
 * <b> Vehicule est la classe contenant toutes les voitures autonomes
 * <p>
 * Un membre de Vehicule est compos&eacute; des caract&eacute;ristiques suivantes
 * <ul>
 * <li>Un tableau d'Integer permettant de savoir quel chemin le v&eacute;hicule d&eacute;sire prendre</li>
 * <li>Un passager, qui demande &agrave; la voiture d'aller d'un point a &agrave; un point b </li>
 * <li>Un identifiant unique pour ce V&eacute;hicule
 * </ul>
 * 
 * @author florian+theo
 * @version 0.1
 */
//ICI GESTION DE THREAD
public class Vehicule {
	
	/**
	 * L'identifiant du v&eacute;hicule ne peut pas etre modifi&eacute; et est unique
	 */
		int ID;
	
	/**
	 * Tableau d'Integer contenant le trajet que le v&eacute;hicule souhaite suivre,
	 * sous forme d'une suite d'identifiants. Celui-ci sera ensuite converti en
	 * une Request_Map, afin de demander au Controleur l'autorisation de
	 * l'emprunter.
	 */
	private ArrayList<Integer> trajet;

	/**
	 * Passager permettant de conna&icirc;tre le point de depart et d'arriv&eacute;e du
	 * v&eacute;hicule
	 */
	private Passager passager;

	/**
	 * Constructeur permettant de cr&eacute;er un v&eacute;hicule et de lui assigner une
	 * mission et une identit&eacute;
	 * 
	 * @param p
	 *            Le passager
	 */
	Vehicule(Passager p, int ID) {
		this.ID = ID;
		this.trajet = new ArrayList<Integer>();
		this.passager = p;
	}

	/**
	 * Fonction permettant de trouver un chemin de la place de d&eacute;part vers celle d'arriv&eacute;e
	 * @param p Un passager, indiquant les places de d&eacute;part et d'arriv&eacute;e
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
		RMap rq = new RMap(ID);
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
