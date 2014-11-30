package Vue;

import Controleur.Verificateur;
import Modele.Observer;

public class Fenetre implements Observer{
	private Verificateur verif;
	
	public Fenetre(Verificateur v){
		verif=v;
	}
	@Override
	public void update(String str) {
	}
	
}
