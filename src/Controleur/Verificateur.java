package Controleur;

import Modele.Controleur;

public class Verificateur {
	protected Controleur model;

	public Verificateur(Controleur c) {
		model = c;
	}
	public void notifArrivee(int iD){
		model.updateArriveeTemp(iD);
	}
	public void newRequest(int dep, int ar){
		model.getBoite().newRequest(dep, ar);
	}

}
