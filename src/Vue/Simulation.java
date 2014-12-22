package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controleur.Verificateur;
import Exception.RDepartException;
import Exception.RequeteException;
import Modele.Observer;

public class Simulation extends MainPan implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel buttonpan, missionpan, controlpan;
	private MapPan map;
	private JButton pause, reset, envoyer;
	private JComboBox depart, arrivee;
	private JLabel dep, ar;
	private String[] tab = { "1", "2", "3", "4", "5", "6" };
	private ArrayList<Place> places;
	private ArrayList<Vehicule> vehicules;
	private int centerx = 169, centery = 178, r = 25, dist = 132, iD = 0,
			tailleflotte, vitesse;
	private Verificateur verif;

	/**
	 * Constructeur de la simulation : initialisation des composants
	 * 
	 * @param auto
	 *            Indique si le mode automatique est actif
	 * @param mere
	 *            La fenetre contenant la simulation
	 * @param verif
	 *            Le Verificateur de la fenetre
	 * @see Verificateur
	 * @see Fenetre
	 */
	public Simulation(boolean auto, Fenetre mere, Verificateur verif,
			int vitesse, int tailleflotte) {
		super();
		// On stocke le verificateur
		this.verif = verif;
		this.vitesse = vitesse;
		this.tailleflotte = tailleflotte;
		// On initialise la liste des vehicules affiches
		vehicules = new ArrayList<Vehicule>();
		// On initialise la liste des places et de leurs coordonnees
		buildPlaces();
		// On rediemnsionne la fenetre
		mere.setSize(new Dimension(350, 475));
		// Initialisaion de la simulation
		this.setPreferredSize(new Dimension(350, 450));
		this.setLayout(new BorderLayout());
		map = new MapPan();
		map.setPreferredSize(new Dimension(350, 350));
		buttonpan = new JPanel();
		// Panel permettant de generer des requetes
		missionpan = new JPanel();
		// Panel contenant pause et reset
		controlpan = new JPanel();

		pause = mere.prepButton("Pause");
		reset = mere.prepButton("Reinitialiser");
		envoyer = mere.prepButton("Envoyer");
		envoyer.addActionListener(new SendListener());

		depart = new JComboBox(tab);
		arrivee = new JComboBox(tab);
		depart.setEditable(true);
		depart.setRenderer(new CustomRend());
		depart.setEditor(new CustomEditor());
		arrivee.setEditable(true);
		arrivee.setRenderer(new CustomRend());
		arrivee.setEditor(new CustomEditor());

		dep = new JLabel("Depart : ");
		ar = new JLabel("Arrivee : ");

		buttonpan.setBackground(new Color(52, 52, 52));
		missionpan.setBackground(new Color(52, 52, 52));
		controlpan.setBackground(new Color(52, 52, 52));

		buttonpan.setLayout(new BorderLayout());
		buttonpan.setPreferredSize(new Dimension(350, 75));

		dep.setForeground(new Color(90, 150, 12));
		ar.setForeground(new Color(90, 150, 12));
		// Finalisation du missionpan
		missionpan.add(dep);
		missionpan.add(depart);
		missionpan.add(ar);
		missionpan.add(arrivee);
		missionpan.add(envoyer);

		// Le missionpan ne sera modifiable que si le mode manuel est actif
		if (auto) {
			missionpan.setEnabled(false);
			depart.setEnabled(false);
			dep.setEnabled(false);
			ar.setEnabled(false);
			arrivee.setEnabled(false);
			envoyer.setEnabled(false);
		}

		controlpan.add(pause);
		controlpan.add(reset);

		buttonpan.add(missionpan, BorderLayout.NORTH);
		buttonpan.add(controlpan, BorderLayout.SOUTH);

		// On centre le panel de la map, puis on l'ajoute
		this.add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.NORTH);
		this.add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.EAST);
		this.add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.WEST);
		this.add(map, BorderLayout.CENTER);
		this.add(buttonpan, BorderLayout.SOUTH);
		verif.obsVehicules(this);
		/*
		 * //TEST ++++++++++++++++++++++++++++++++++++++++++ Vehicule veh = new
		 * Vehicule(0, places.get(18).x, places.get(18).y, 3, "nunu");
		 * veh.xdest=places.get(9).x; veh.ydest=places.get(9).y;
		 * vehicules.add(veh); veh.start();
		 */
	}

	/**
	 * Fonction construisant la liste des differentes places de la map, ainsi
	 * que leurs coordonnees
	 */
	private void buildPlaces() {
		/*
		 * On calcule les coordonnees des places a l'aide de la trigonometrie
		 * L'heaxagone de la map peut etre inscrit dans un cercle... Ainsi,
		 * l'angle de C-3 est de PI/6 => Les coordonnees de 3 (par raport a C)
		 * sont (sqrt(3)/2, 1/2) On effectue ensuite un changement de repere
		 * pour otenir les coordonnees absolues du point
		 */

		// PENSER A AJOUTER LES AUTRES PLACES
		places = new ArrayList<Place>();

		// A REMPLACER PAR LES VRAIES COORDONNEES
		places.add(new Place(1,(int) (centerx - (dist / 2) * Math.sqrt(3) + 42*Math.cos(3.14159265359)),(int)(centery - (dist / 2) - 42*Math.sin(3.14159265359))));// I1
		places.add(new Place(2, (int)(centerx + 42*Math.cos(2.09439510239)), (int) (centery - dist - 42*Math.sin(2.09439510239))));// I2
		places.add(new Place(3,(int) (centerx + (dist / 2) * Math.sqrt(3) + 42*Math.cos(1.0471975512)), (int) (centery	- (dist / 2) - 42*Math.sin(1.0471975512))));// I3
		places.add(new Place(4,(int) (centerx + (dist / 2) * Math.sqrt(3) + 42*Math.cos(-0.01)), (int) (centery+ (dist / 2) - 42*Math.sin(-0.01))));// I4
		places.add(new Place(5, (int) (centerx + 42*Math.cos(-2.09439510239)), (int)(centery + dist - 42*Math.sin(-2.09439510239))));// I5
		places.add(new Place(6,(int) ((centerx - (dist / 2) * Math.sqrt(3)) + 42*Math.cos(-2.09439510239)), (int) (centery + (dist / 2) - 42*Math.sin(-2.09439510239))));// I6


		places.add(new Place(11, (int) (centerx - (dist / 2) * Math.sqrt(3)),
				(centery - (dist / 2))));// R1
		places.add(new Place(12, centerx, centery - dist));// R2
		places.add(new Place(13, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery - (dist / 2)));// R3
		places.add(new Place(14, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery + (dist / 2)));// R4
		places.add(new Place(15, centerx, centery + dist));// R5
		places.add(new Place(16, (int) (centerx - (dist / 2) * Math.sqrt(3)),
				centery + (dist / 2)));// R6

		// A REMPLACER PAR LES VRAIES COORDONNEES
		places.add(new Place(21,(int) ((centerx - (dist / 2) * Math.sqrt(3)) + 42*Math.cos(2.09439510239)) ,(int)((centery - (dist / 2))-42*Math.sin(2.09439510239))));// O1
		places.add(new Place(22, (int)(centerx + 42*Math.cos(1.0471975512)), (int)(centery - dist - 42*Math.sin(1.0471975512))));// O2
		places.add(new Place(23,(int) ((centerx + (dist / 2) * Math.sqrt(3)) + 42*Math.cos(0)), (int)(centery - (dist / 2) - 42*Math.sin(0))));// O3
		places.add(new Place(24,(int) ((centerx + (dist / 2) * Math.sqrt(3)) + 42*Math.cos(-1.0471975512)), (int)(centery + (dist / 2) - 42*Math.sin(-1.0471975512))));// O4
		places.add(new Place(25, (int) (centerx - 42*Math.cos(-2.09439510239)), (int)(centery + dist - 42*Math.sin(-2.09439510239))));// O5
		places.add(new Place(26,(int) (centerx - (dist / 2) * Math.sqrt(3) + 42*Math.cos(3.14159265359)), (int)(centery+ (dist / 2) - 42*Math.sin(3.14159265359))));// O6
		
		places.add(new Place(30, centerx, centery));// C
	}

	/**
	 * Fonction mettant a jour la destination d'un vehicule donne
	 * 
	 * @param ID
	 *            L'identifiant du vehicule concerne
	 * @param dep
	 *            Son pointde depart
	 * @param ar
	 *            Son point d'arrivee
	 */
	public void updateCoords(int ID, int suivant) {
		int i = 0;
		// Si c'est la fin du trajet
		if (suivant == 99) {
			for (int j=0; j<vehicules.size(); j++) {
				// On enleve le vehicule graphique
				if (vehicules.get(j).iD == ID) {
					vehicules.remove(vehicules.get(j));
					repaint();
				}
			}

		} else {
			while (places.get(i).iD != suivant) {
				i++;
			}
			for (Vehicule v : vehicules) {
				if (v.iD == ID) {
					v.xdest = places.get(i).x;
					v.ydest = places.get(i).y;
				}
			}
		}
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Private classes
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * Listener du bouton d'envoi d'une nouvelle requete
	 * 
	 * @author Hellong
	 */
	private class SendListener implements ActionListener {
		int dep, ar;

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dep = Integer.parseInt((String) depart.getSelectedItem());
			ar = Integer.parseInt((String) arrivee.getSelectedItem());
			try {
				verif.newRequest(dep, ar);
			} catch (RequeteException | RDepartException e1) {

			}
		}

	}

	/**
	 * Le MapPan contient la map, et donc les differents vehicules qui se
	 * deplacent
	 * 
	 * @author Hellong
	 *
	 */
	private class MapPan extends MainPan {
		Image img, veh0, veh1, veh2, veh3, veh4;

		/**
		 * Constructeur par defaut, qui charge les differentes images des
		 * vehicules
		 */
		public MapPan() {
			try {
				img = ImageIO
						.read(this.getClass().getResource("Map_proj2try.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Surcharge de la fonction paintComponent, pour peindre la map et les
		 * vehicules
		 */
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g.setColor(new Color(52, 52, 52));
			// On redessine le fond
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			// On dessine la map
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0,
					this.getWidth(), this.getHeight(), null);
			// On dessine les vehicules
			/*for(Place p : places){
				g.setColor(new Color(255, 255, 255));
				g.fillRect(p.x, p.y, 2, 2);
			//	System.out.println("Place " + p.iD + " : X : " + p.x + " Y : " + p.y);
				
			}*/
			for (Vehicule v : vehicules) {
				// A chaque type de vehicule correspond une image differente
				/*
				 * On tourne l'image pour qu'elle s'aligne sur la trajectoire du
				 * vehiculeEnsuite on la deplace aux coordonnees voulues
				 */
				AffineTransform rotation = new AffineTransform();
				int coef = 1;

				rotation.translate(v.x - v.apparence.getWidth(null) / 2
						- v.decalx, v.y - v.apparence.getHeight(null) / 2
						+ v.decaly);
				rotation.rotate(v.angle,
						(int) (v.apparence.getWidth(null) / 2),
						(int) (v.apparence.getHeight(null) / 2));
				if (v.decalx != 0) {
					rotation.scale(1.0, -1.0);
				}
				g2d.drawImage(v.apparence, rotation, null);
			}
		}
	}

	/**
	 * La classe Place est seulement destinee a stocker un identifiant et les
	 * coordonnees associees
	 * 
	 * @author Hellong
	 *
	 */
	private class Place {

		int iD;
		int x;
		int y;

		/**
		 * Constructeur par defaut de la classe
		 * 
		 * @param iD
		 *            Son identifiant
		 * @param x
		 *            Abscisse de la place
		 * @param y
		 *            Ordonnee de la place
		 */
		public Place(int iD, int x, int y) {
			this.iD = iD;
			this.x = x;
			this.y = y;
		}

	}

	/**
	 * Le vehicule est la version graphique de celui du modele : il est charge
	 * d'afficher le deplacement de celui-ci
	 * 
	 * @author Hellong
	 *
	 */
	public class Vehicule extends Thread {

		int iD, x, y, xi, yi, xdest, ydest, type, coef, decalx, decaly;
		double angle;
		Image apparence;

		/**
		 * Constructeur du vehicule
		 * 
		 * @param iD
		 *            Identifiant du vehicule
		 * @param x
		 *            Abscisse de depart
		 * @param y
		 *            Ordonnee de depart
		 * @param type
		 *            Type du vehicule (influe sur so apparence)
		 * @param name
		 *            Nom du thread
		 * @see Thread
		 */
		public Vehicule(int iD, int x, int y, int type, String name) {
			super(name);
			this.iD = iD;
			this.x = x;
			this.y = y;
			this.type = type;
			this.xdest = x;
			this.ydest = y;
			this.xi = x;
			this.yi = x;
			try {
				apparence = ImageIO.read(this.getClass().getResource(
						"veh" + type + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * Fonction run() du thread
		 */
		public void run() {
			// map.repaint();
		//	System.out.println("Da");
			while (true) {
				// Si la destination a ete modifiee
				if (x != xdest || y != ydest) {
				//	System.out.println("Da2");
					this.xi = x;
					this.yi = y;
					decalx = 0;
					decaly = 0;
					coef = 1;
					angle = Math.acos((xdest - xi)
							/ Math.sqrt(Math.pow(xdest - xi, 2)
									+ Math.pow(ydest - yi, 2)));
					if (xdest < xi || ydest < yi) {
						if (xdest < xi && ydest > yi) {
							decalx = (int) (apparence.getHeight(null) * Math
									.cos(1.57 - angle));
							decaly = (int) (apparence.getHeight(null) * Math
									.sin(1.57 - angle));
						} else {
							if (xdest < xi) {
								decalx = (int) -(apparence.getHeight(null) * Math
										.cos(1.57 - angle));
								decaly = (int) (apparence.getHeight(null) * Math
										.sin(1.57 - angle));
							}
							coef = -coef;
						}
					}
					angle = angle * coef;
					// On calcule les deltas necessaires aux deplacements
					float dX = ((xdest - x) * vitesse / (float) 1000);
					float dY = ((ydest - y) * vitesse / (float) 1000);
					float xt = x, yt = y;
					// Tant qu'on est pas arrive a la destination
					while (x != xdest || y != ydest) {
						try {
							sleep(25);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// on se deplace
						xt += dX;
						yt += dY;
						x = Math.round(xt);
						y = Math.round(yt);
						// et on relance le paint
						map.repaint();
					}
					// Lorsqu'on est arrive, on le notifie
					verif.notifArrivee(iD);
				}
				// Sinon on attend une modification
				else {
					try {
						sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public void update(String str) {
		// TODO Auto-generated method stub

	}

	// appellee lorsqu'un nouveau vehicule graphique doit etre cree (debut d'un
	// trajet)
	@Override
	public int updateDebutMission(int dep) {
		// ATTENTION ! FAUX ! IL FAUT READAPTER L'IDENTIFIANT DU DEPART AVEC LES
		// PLACES D'ENTREE
		int i = 0;
		while (places.get(i).iD != dep) {
			i++;
		}
		Vehicule veh = new Simulation.Vehicule(iD, places.get(i).x,
				places.get(i).y, 3, "toto");
		iD++;
		vehicules.add(veh);
		veh.start();
		return iD - 1;
	}
}
