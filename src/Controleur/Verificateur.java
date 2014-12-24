package Controleur;

import Exception.RDepartException;
import Exception.RequeteException;
import Modele.Controleur;
import Modele.Observer;
import Modele.RDepart;

public class Verificateur {
	protected Controleur model;
	private RequestGenerator reqGen;
	private boolean auto;

	public Verificateur(Controleur c) {
		model = c;
	}

	public void notifArrivee(int iD) {
		model.updateArriveeTemp(iD);
	}

	public void obsVehicules(Observer obs){
		model.updateObsVehicules(obs);
	}
	public void newRequest(int dep, int ar) throws RequeteException, RDepartException{
		if(dep != ar){
			RDepart m =new RDepart(dep, ar);
			model.getBoite().addRequete(m);
		//	System.out.println("sdgvfdgdg");
		}else{
			throw new RequeteException();
		}
		
	}
	public void debutSim(int tailleflotte, boolean auto, boolean fichier){
		model.debutSim(tailleflotte);
		Thread t=new Thread(model);
		this.auto = auto;
		t.start();
		if(auto){
			if(fichier){
				reqGen = new RequestGenerator(this, true);
			}else{
				reqGen = new RequestGenerator(this, false);
			}
			reqGen.start();
			
		}
	}
	
	public void resetAll(){
		model.resetAll();
		if(auto){
			reqGen.reset();;
		}
	}
	
	public void pause(boolean running){
		model.pauseAll(running);
		if(auto){
			reqGen.setRunning(running);
		}
	}

}
