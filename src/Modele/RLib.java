package Modele;
/**
 * Classe servant a liberer au fur et a mesure toute les ressource utiliser par la voiture
 * @author flacour
 *
 */
public class RLib {
	/**un int designant la ressource a librer
	 * * <ul>
	 *	 <li>les clées de 1 a 6 sont les clées des place d'entrées</li>
	 * <li>les clées de 11 a 16 sont les clées des routes</li>
	 * <li>les clées de 21 a 26 sont les clées des places de sorties</li>
	 * <li>La clée 30 est une clée en reference vers le centre.
	 * </ul>
	 * cette entier n'est pas modifiable
	 **/
		private int lib;
		/**
		 * Constructeur par default
		 */
		public RLib(){
			
		}
		/**
		 * constructeur avec prametre
		 * @param i
		 * 			l'identifiant de la route a libérer
		 */
		public RLib(int i) {
			lib=i;
		}
		public int getLib(){
			return lib;
		}
		public void clone(RLib r){
			this.lib=r.lib;
		}
}
