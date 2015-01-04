package Controleur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import Exception.RDepartException;
import Modele.RDepart;


/**
 * 
 *
 * @author florian + theo
 */
public class Lecteur {

	protected String path;

	public Lecteur(final String path) {

		this.path = path;
	}

	// Lis le fichier, et stocke tout dans une liste
	public LinkedList<String> getReq() {
		final LinkedList<String> inside = new LinkedList<String>();
		try {
			final BufferedReader br = new BufferedReader(new FileReader(path));
			int i = 0;
			br.mark(100000);
			while (br.readLine() != null) {
				i++;
			}
			br.reset();
			while (inside.size() < i) {
				inside.add(br.readLine());
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inside;
	}

	// Convertis la liste de donnees en une liste de requetes de depart
	public ArrayList<RDepart> convert() {
		LinkedList<String> rtext = this.getReq();
		ArrayList<RDepart> rdep = new ArrayList<RDepart>();
		int dep, ar, temps;

		String temp;
		for (int i = 0; i < rtext.size(); i++) {
			if (!rtext.get(i).contains("@")) {
				dep = Integer.parseInt(rtext.get(i).substring(0,
						rtext.get(i).indexOf("\t")));
				temp = rtext.get(i).substring(rtext.get(i).indexOf("\t") + 1);
				ar = Integer.parseInt(temp.substring(0,
						rtext.get(i).indexOf("\t")));
				temp = temp.substring(rtext.get(i).indexOf("\t") + 1);
				temps = Integer.parseInt(temp);
				if (dep >= 1 && dep <= 6 && ar >= 1 && ar <= 6 && ar != dep) {
					try {
						rdep.add(new RDepart(dep, ar, temps));
					} catch (RDepartException e) {
						e.printStackTrace();
					}
				}
			}
		}
		rdep = tri(rdep);

		return rdep;
	}

	//Trie la liste de requetes, en premier celle qui sera lancee le plus tot
	ArrayList<RDepart> tri(ArrayList<RDepart> atrier) {
		boolean action = false;
		for (int i = 0; i < atrier.size(); i++) {
			for (int j = 0; j < atrier.size() - 1; j++) {
				if (atrier.get(j).getTemps() > atrier.get(j + 1).getTemps()) {
					Collections.swap(atrier, j, j + 1);
					action = true;
				}
			}
			if (!action) {
				break;
			}
			action = false;
		}
		return atrier;
	}
}
