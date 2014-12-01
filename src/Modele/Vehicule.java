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
	 * tabelau de boolean permetant de savooir le chemin implmenter sous forme de maRequestpar exmemple si la voiture desire partir de 1 et arriver a 2
	 * I1 I2 I3 I4 I5 I6 R1 R2 R3 R4 R5 R6 O1 O2 O3 O4 O5 O6 30
	 * T  F  F  F  F  F  T  T  F  F  F  F  F  T  F  F  F  F  F
	 * T=true et F=false
	 * 
	 * Toute les route roule dans le sens inverse des aiguille d'une montre
	 */
private ArrayList <Boolean> trajet;
/**
 * boolean permetant de decider si la voiture peut partir ou non.
 * true si la voiture peut partir sinon false
 */
private boolean authoriser;
/**
 * contient la liste des prioriter afin que a partir d'un passager la voiture puisse creer sont trajet
 */
private  ArrayList <Integer> prioriter;
/**
 * passager permetant de connaitre le point de depart et d'arriver du vehicule
 */
private Passager passager;
/**
 * constructeur par default du vehicule.
 */
Vehicule(){
				
}
/**
 * constructeur permetant de creer un vehicule et de lui assigner une mission
 * @param m
 * 			Une mission 
 */
Vehicule(Passager m, int ID){
	this.ID=ID;
	this.passager=m;
	trajet=new ArrayList <Boolean>();
	authoriser=false;
	initTrajet();
	genererRequestMap();
}
/**
 * Fonction permettant d'initialiser le trajet avec des false partout
 */
private void initTrajet(){
	//le tableau a 6 route, 6entré,sortie est une place central soit 18 case
	//RISQUE ERREUR SUR LA TAILLE DU TABLEAU (qui se repercuterer sur genererRequestMAp
	for(int i=0;i<17;i++){
		trajet.add(false);
	}
}
/**
 * fonction permettant de generer une request map 
 * Atention par exemple la route entre I1 et I2 et la route R1
 */
/*
 * pour l'instant gestion des route en face+des route a coter
 * rester a generer les route a 2 d'intervalle
 */
public void genererRequestMap(){
	//Si la difference entre le premier est le dernier est egal a 1 alors on est en face est on a le droit de passer par le Millieux
	if(Math.abs(passager.getDebut()-passager.getFin())==3){
		//set de la place d'entré
		trajet.set(passager.getDebut()-1, true);
		//reservation de la derniére place
		trajet.set(12+passager.getFin()-1,true);
		//reservation de la palce central
		trajet.set(18, true);
	}
	//si ils sont a coter (sauf le cas 6-1 et 1-6
	if(Math.abs(passager.getDebut()-passager.getFin())==1){
		//set de la place d'entré
				trajet.set(passager.getDebut()-1, true);
		//reservation de la derniére place
				trajet.set(12+passager.getFin()-1,true);
		//reservation de la route
				trajet.set(6+passager.getDebut(), true);
	}
	//gestion de l'exception route depart 1 avec toute 6 arriver
	if(passager.getDebut()==1&& passager.getFin()==6){
		//set de la place d'entré
		trajet.set(passager.getDebut()-1, true);
		//reservation de la derniére place
		trajet.set(12+passager.getFin()-1,true);
		//reservation des route 1-2-3-4-5 car on troune dans l'odre inverse des aiguille d'une montre
		trajet.set(6, true);
		trajet.set(7, true);
		trajet.set(8, true);
		trajet.set(9, true);
		trajet.set(10, true);
		
	}
	//gestion de l'exception route 6 depart et route 1 arriver
	if(passager.getDebut()==6 && passager.getFin()==1){
		//set de la place d'entré
		trajet.set(passager.getDebut()-1, true);
		//reservation de la derniére place
		trajet.set(12+passager.getFin()-1,true);
		//reservation de la route 6(soit dans le tableau la 11eme entré.
		trajet.set(11, true);
	}
	
}
public void setTrajet(){
	
}
}
