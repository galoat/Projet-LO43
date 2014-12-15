package Controleur;

import Exception.RDepartException;
import Exception.RequeteException;
import Modele.Controleur;
import Modele.RDepart;

public class Verificateur {
	protected Controleur model;

	public Verificateur(Controleur c) {
		model = c;
	}

	public void notifArrivee(int iD) {
		model.updateArriveeTemp(iD);
	}

	public void newRequest(int dep, int ar) throws RequeteException, RDepartException{
		if(dep != ar){
			RDepart m =new RDepart(dep, ar);
			model.getBoite().addRequete(m);
			System.out.println("sdgvfdgdg");
		}else{
			throw new RequeteException();
		}
		
	}

}
