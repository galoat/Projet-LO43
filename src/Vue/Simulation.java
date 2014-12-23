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
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controleur.Verificateur;
import Exception.RDepartException;
import Exception.RequeteException;
import Modele.Observer;

public class Simulation extends MainPan implements Observer {

	private static final long serialVersionUID = 1L;
	private JPanel buttonpan, missionpan, controlpan, passagerpan;
	private MapPan map;
	private JButton pause, reset, envoyer;
	@SuppressWarnings("rawtypes")
	private JComboBox depart, arrivee;
	private JLabel dep, ar, passattvoiture, passattdepart, p1, p2;
	private String[] tab = { "1", "2", "3", "4", "5", "6" };
	private ArrayList<Place> places;
	private ArrayList<Vehicule> vehicules;
	@SuppressWarnings("unused")
	private int centerx = 169, centery = 178, dist = 132, iD = 0, tailleflotte,
			vitesse;
	private Verificateur verif;
	private boolean running;

	/**
	 * Constructeur de la simulation : initialisation des composants
	 * 
	 * @param auto
	 *            Indique si le mode automatique est actif
	 * @param mere
	 *            La fenetre contenant la simulation
	 * @param verif
	 *            Le Verificateur de la fenetre
	 * @param vitesse
	 *            Vitesse des vehicules (coefficient)
	 * @param tailleflotte
	 *            Nombre de voitures dans la flotte
	 * @see Verificateur
	 * @see Fenetre
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Simulation(boolean auto, Fenetre mere, Verificateur verif,
			int vitesse, int tailleflotte) {
		super();
		// On stocke le verificateur
		this.verif = verif;
		this.vitesse = vitesse;
		this.tailleflotte = tailleflotte;
		running = true;
		// On initialise la liste des vehicules affiches
		vehicules = new ArrayList<Vehicule>();
		// On initialise la liste des places et de leurs coordonnees
		buildPlaces();
		// On redimensionne la fenetre
		mere.setSize(new Dimension(350, 515));
		// Initialisation de la simulation
		this.setPreferredSize(new Dimension(350, 490));
		this.setLayout(new BorderLayout());
		map = new MapPan();
		map.setPreferredSize(new Dimension(350, 380));
		buttonpan = new JPanel();
		// Panel permettant de generer des requetes
		missionpan = new JPanel();
		// Panel contenant pause et reset
		controlpan = new JPanel();
		// Panel contenant les infos sur les passagers en attente
		passagerpan = new JPanel();

		// On initialise les autre composants
		depart = new JComboBox(tab);
		arrivee = new JComboBox(tab);
		dep = new JLabel("Depart : ");
		ar = new JLabel("Arrivee : ");
		passattvoiture = new JLabel("            En attente d'un voiture : ");
		passattdepart = new JLabel("En instance de depart : ");
		p1 = new JLabel("0");
		p2 = new JLabel("0");

		// On s'occupe des boutons
		pause = mere.prepButton("Pause");
		reset = mere.prepButton("Reinitialiser");
		envoyer = mere.prepButton("Envoyer");
		pause.addActionListener(new ControlListener());
		reset.addActionListener(new ControlListener());
		envoyer.addActionListener(new SendListener());

		// On configure les labels des requetes
		depart.setEditable(true);
		depart.setRenderer(new CustomRend());
		depart.setEditor(new CustomEditor());
		arrivee.setEditable(true);
		arrivee.setRenderer(new CustomRend());
		arrivee.setEditor(new CustomEditor());

		// On configure les labels des passagers
		dep.setForeground(new Color(90, 150, 12));
		ar.setForeground(new Color(90, 150, 12));
		passattdepart.setForeground(new Color(90, 150, 12));
		passattvoiture.setForeground(new Color(90, 150, 12));
		p1.setForeground(new Color(200, 200, 200));
		p2.setForeground(new Color(200, 200, 200));

		// On configure les differents panels
		buttonpan.setBackground(new Color(52, 52, 52));
		missionpan.setBackground(new Color(52, 52, 52));
		controlpan.setBackground(new Color(52, 52, 52));
		passagerpan.setBackground(new Color(52, 52, 52));
		buttonpan.setLayout(new BorderLayout());
		buttonpan.setPreferredSize(new Dimension(350, 110));
		passagerpan.setPreferredSize(new Dimension(350, 35));

		// Finalisation du panel "passager"
		passagerpan.add(passattdepart);
		passagerpan.add(p1);
		passagerpan.add(passattvoiture);
		passagerpan.add(p2);

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

		// On assemble
		controlpan.add(pause);
		controlpan.add(reset);
		buttonpan.add(passagerpan, BorderLayout.NORTH);
		buttonpan.add(missionpan, BorderLayout.CENTER);
		buttonpan.add(controlpan, BorderLayout.SOUTH);

		// On centre le panel de la map, puis on l'ajoute
		this.add(Box.createRigidArea(new Dimension(10, 10)), BorderLayout.NORTH);
		this.add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.EAST);
		this.add(Box.createRigidArea(new Dimension(5, 5)), BorderLayout.WEST);
		this.add(map, BorderLayout.CENTER);
		this.add(buttonpan, BorderLayout.SOUTH);
		verif.obsVehicules(this);
	}

	/**
	 * Fonction construisant la liste des differentes places de la map, ainsi
	 * que leurs coordonnees
	 */
	private void buildPlaces() {
		/*
		 * On calcule les coordonnees des places a l'aide de la trigonometrie
		 * L'hexagone de la map peut etre inscrit dans un cercle... Ainsi,
		 * l'angle de C-R3 est de PI/6 => Les coordonnees de R3 (par raport a C)
		 * sont (sqrt(3)/2, 1/2) On effectue ensuite un changement de repere
		 * pour otenir les coordonnees absolues du point...
		 * 
		 * Pour obtenir ensuite les coordonnees des places de depart et
		 * d'arrivee, on se sert de celles des places R. Les places de depart
		 * sont decalees de PI/6 par rapport a leur place R, et celles
		 * d'arrivee, de -PI/6. On effectue alors un second changement de repere
		 * pour se placer avec la place R concernee pour centre. On decale
		 * ensuite de +/- PI/6 !
		 * 
		 * /!\ Les nombres en parametres ci-dessous sont en radians... Ils
		 * correspondent a PI/3, PI/6, etc... Pour plus de comprehension : PI/6
		 * = 0.52359877559 PI/3 = 1.0471975512 2PI/3 = 2.09439510239 PI =
		 * 3.14159265359
		 */

		places = new ArrayList<Place>();

		// Places de departs
		// I1
		places.add(new Place(1,
				(int) (centerx - (dist / 2) * Math.sqrt(3) + 42 * Math
						.cos(3.14159265359)),
				(int) (centery - (dist / 2) - 42 * Math.sin(3.14159265359))));
		// I2
		places.add(new Place(2, (int) (centerx + 42 * Math.cos(2.09439510239)),
				(int) (centery - dist - 42 * Math.sin(2.09439510239))));
		// I3
		places.add(new Place(3,
				(int) (centerx + (dist / 2) * Math.sqrt(3) + 42 * Math
						.cos(1.0471975512)),
				(int) (centery - (dist / 2) - 42 * Math.sin(1.0471975512))));
		// I4
		places.add(new Place(4,
				(int) (centerx + (dist / 2) * Math.sqrt(3) + 42 * Math
						.cos(-0.01)), (int) (centery + (dist / 2) - 42 * Math
						.sin(-0.01))));
		// I5
		places.add(new Place(5,
				(int) (centerx + 42 * Math.cos(-2.09439510239)), (int) (centery
						+ dist - 42 * Math.sin(-2.09439510239))));
		// I6
		places.add(new Place(6,
				(int) ((centerx - (dist / 2) * Math.sqrt(3)) + 42 * Math
						.cos(-2.09439510239)),
				(int) (centery + (dist / 2) - 42 * Math.sin(-2.09439510239))));

		// Places R
		// R1
		places.add(new Place(11, (int) (centerx - (dist / 2) * Math.sqrt(3)),
				(centery - (dist / 2))));
		// R2
		places.add(new Place(12, centerx, centery - dist));
		// R3
		places.add(new Place(13, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery - (dist / 2)));
		// R4
		places.add(new Place(14, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery + (dist / 2)));
		// R5
		places.add(new Place(15, centerx, centery + dist));
		// R6
		places.add(new Place(16, (int) (centerx - (dist / 2) * Math.sqrt(3)),
				centery + (dist / 2)));

		// Places d'arrivee
		// O1
		places.add(new Place(21,
				(int) ((centerx - (dist / 2) * Math.sqrt(3)) + 42 * Math
						.cos(2.09439510239)),
				(int) ((centery - (dist / 2)) - 42 * Math.sin(2.09439510239))));
		// O2
		places.add(new Place(22, (int) (centerx + 42 * Math.cos(1.0471975512)),
				(int) (centery - dist - 42 * Math.sin(1.0471975512))));
		// O3
		places.add(new Place(
				23,
				(int) ((centerx + (dist / 2) * Math.sqrt(3)) + 42 * Math.cos(0)),
				(int) (centery - (dist / 2) - 42 * Math.sin(0))));
		// O4
		places.add(new Place(24,
				(int) ((centerx + (dist / 2) * Math.sqrt(3)) + 42 * Math
						.cos(-1.0471975512)),
				(int) (centery + (dist / 2) - 42 * Math.sin(-1.0471975512))));
		// O5
		places.add(new Place(25,
				(int) (centerx - 42 * Math.cos(-2.09439510239)), (int) (centery
						+ dist - 42 * Math.sin(-2.09439510239))));
		// O6
		places.add(new Place(26,
				(int) (centerx - (dist / 2) * Math.sqrt(3) + 42 * Math
						.cos(3.14159265359)),
				(int) (centery + (dist / 2) - 42 * Math.sin(3.14159265359))));

		// Centre
		places.add(new Place(30, centerx, centery));// C
	}

	/**
	 * Fonction mettant a jour la destination d'un vehicule graphique donne
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
			for (int j = 0; j < vehicules.size(); j++) {
				// On enleve le vehicule graphique
				if (vehicules.get(j).iD == ID) {
					vehicules.remove(vehicules.get(j));
					repaint();
				}
			}

		} else {
			// On se place au niveau du vehicule graphique correspondant
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

	/**
	 * Listener du bouton d'envoi d'une nouvelle requete
	 * 
	 * @author florian + theo
	 */
	private class SendListener implements ActionListener {
		int dep, ar;

		/**
		 * Surcharge de la fonction appelee lors d'un clic
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// On recupere les places choisies par l'utilisateur
			dep = Integer.parseInt((String) depart.getSelectedItem());
			ar = Integer.parseInt((String) arrivee.getSelectedItem());
			// Et on envoie une requete de depart
			try {
				verif.newRequest(dep, ar);
			} catch (RequeteException | RDepartException e1) {

			}
		}
	}
	/**
	 * Listener des boutons de pause et reset
	 * 
	 * @author florian + theo
	 */
	private class ControlListener implements ActionListener {

		/**
		 * Surcharge de la fonction appelee lors d'un clic
		 */
		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent e) {
			//Si on veut reinitialiser
			if (e.getSource() == reset) {
				//On arrete les Threads des vehicules affiches
				for (Vehicule v : vehicules) {
					v.stop();
				}
				//On les detruit
				vehicules.clear();
				//On repeint
				map.repaint();
				//Puis on reinitialise le modele
				verif.resetAll();
			} else {
				//Si on veut mettre en pause, on change juste le booleen correspondant
				running = !running;
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
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unused")
		Image img, veh0, veh1, veh2, veh3, veh4;

		/**
		 * Constructeur par defaut, qui charge les differentes images des
		 * vehicules
		 */
		public MapPan() {
			try {
				img = ImageIO.read(this.getClass().getResource(
						"Map_proj2try.png"));
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
			for (Vehicule v : vehicules) {
				// A chaque type de vehicule correspond une image differente
				/*
				 * On tourne l'image pour qu'elle s'aligne sur la trajectoire du
				 * vehicule. Ensuite on la deplace aux coordonnees voulues
				 */
				AffineTransform rotation = new AffineTransform();
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
	 * @author florian + theo
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
	 * @author florian + theo
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
			// System.out.println("Da");
			while (true) {
				// Si la destination a ete modifiee
				if (running) {
					if (x != xdest || y != ydest) {
						// System.out.println("Da2");
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
							if (running) {
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
							} else {
								try {
									sleep(25);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
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
				} else {
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
		int type, r;
		Random rand = new Random();
		// ATTENTION ! FAUX ! IL FAUT READAPTER L'IDENTIFIANT DU DEPART AVEC LES
		// PLACES D'ENTREE
		int i = 0;
		while (places.get(i).iD != dep) {
			i++;
		}
		r = rand.nextInt(100);
		if (r < 80) {
			type = 0;
		} else if (r < 85) {
			type = 1;
		} else if (r < 90) {
			type = 2;
		} else if (r < 95) {
			type = 3;
		} else {
			type = 4;
		}
		Vehicule veh = new Simulation.Vehicule(iD, places.get(i).x,
				places.get(i).y, type, "toto");
		iD++;
		vehicules.add(veh);
		veh.start();
		return iD - 1;
	}

	@Override
	public void updatePassagers(int attdepart, int attvoiture) {
		p1.setText(Integer.toString(attdepart));
		p2.setText(Integer.toString(attvoiture));
		System.out.println("attdepart : " + attdepart + " attvoiture : "
				+ attvoiture);
	}
}
