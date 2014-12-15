package Modele;

public class Cerveau extends Thread{
	private Vehicule corps;
	private RMap requestmap;
	private boolean start;
	
	public Cerveau(Vehicule v){
		corps=v;
		start = false;
	}
	/**
	 * Run du thread, qui permet de gérer les requêtes du véhicule et son déplacement
	 * @param p Un passager, indiquant les places de d&eacute;part et d'arriv&eacute;e
	 */
	public void run(){
		corps.findPath(corps.passager);
		requestmap=corps.trajetToMap(corps.trajet);
		while(start != true){
			corps.sendRMap(requestmap);
			try {
				wait(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		while(true){
			
		}
	}
}
