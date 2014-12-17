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

public class Simulation extends MainPan implements Observer{

	private static final long serialVersionUID = 1L;
	private JPanel buttonpan, missionpan, controlpan;
	private MapPan map;
	private JButton pause, reset, envoyer;
	private JComboBox depart, arrivee;
	private JLabel dep, ar;
	private String[] tab = { "1", "2", "3", "4", "5", "6" };
	private ArrayList<Place> places;
	private ArrayList<Vehicule> vehicules;
	private int centerx = 145, centery = 166, r = 25, dist = 132, iD = 0;
	private Verificateur verif;

	/**
	 * Constructeur de la simulation : initialisation des composants
	 * 
	 * @param auto Indique si le mode automatique est actif
	 * @param mere La fenetre contenant la simulation
	 * @param verif Le Verificateur de la fenetre
	 * @see Verificateur
	 * @see	Fenetre
	 */
	public Simulation(boolean auto, Fenetre mere, Verificateur verif) {
		super();
		//On stocke le verificateur
		this.verif = verif;
		//On initialise la liste des vehicules affiches
		vehicules = new ArrayList<Vehicule>();
		//On initialise la liste des places et de leurs coordonnees
		buildPlaces();
		//On rediemnsionne la fenetre
		mere.setSize(new Dimension(350, 475));
		//Initialisaion de la simulation
		this.setPreferredSize(new Dimension(350, 450));
		this.setLayout(new BorderLayout());
		map = new MapPan();
		map.setPreferredSize(new Dimension(350, 350));
		buttonpan = new JPanel();
		//Panel permettant de generer des requetes
		missionpan = new JPanel();
		//Panel contenant pause et reset
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
		//Finalisation du missionpan
		missionpan.add(dep);
		missionpan.add(depart);
		missionpan.add(ar);
		missionpan.add(arrivee);
		missionpan.add(envoyer);
		
		//Le missionpan ne sera modifiable que si le mode manuel est actif
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
		
		//On centre le panel de la map, puis on l'ajoute
		this.add(Box.createRigidArea(new Dimension(30, 20)), BorderLayout.NORTH);
		this.add(Box.createRigidArea(new Dimension(30, 20)), BorderLayout.EAST);
		this.add(Box.createRigidArea(new Dimension(30, 20)), BorderLayout.WEST);
		this.add(map, BorderLayout.CENTER);
		this.add(buttonpan, BorderLayout.SOUTH);
		
	}
	/**
	 * Fonction construisant la liste des differentes places de la map, ainsi que leurs coordonnees
	 */
	private void buildPlaces() {
		/*
		 * On calcule les coordonnees des places a l'aide de la trigonometrie
		 * L'heaxagone de la map peut etre inscrit dans un cercle...
		 * Ainsi, l'angle de C-3 est de PI/6 => Les coordonnees de 3 (par raport a C) sont (sqrt(3)/2, 1/2)
		 * On effectue ensuite un changement de repere pour otenir les coordonnees absolues du point
		 */
		
		//PENSER A AJOUTER LES AUTRES PLACES
		places = new ArrayList<Place>();
		
		//A REMPLACER PAR LES VRAIES COORDONNEES
		places.add(new Place(1, (int) (centerx - (dist / 2) * Math.sqrt(3)),
				(centery - (dist / 2))));//1
		places.add(new Place(2, centerx, centery - dist));//2
		places.add(new Place(3, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery - (dist / 2)));//3
		places.add(new Place(4, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery + (dist / 2)));//4
		places.add(new Place(5, centerx, centery + dist));//5
		places.add(new Place(6, (int) (centerx - (dist / 2) * Math.sqrt(3)),
				centery + (dist / 2)));//6
		
		//CES PLACES SONT JUSTES
		places.add(new Place(11, (int) (centerx - (dist / 2) * Math.sqrt(3)),
				(centery - (dist / 2))));//1
		places.add(new Place(12, centerx, centery - dist));//2
		places.add(new Place(13, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery - (dist / 2)));//3
		places.add(new Place(14, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery + (dist / 2)));//4
		places.add(new Place(15, centerx, centery + dist));//5
		places.add(new Place(16, (int) (centerx - (dist / 2) * Math.sqrt(3)),
				centery + (dist / 2)));//6
		
		//A REMPLACER PAR LES VRAIES COORDONNEES
		places.add(new Place(21, (int) (centerx - (dist / 2) * Math.sqrt(3)),
				(centery - (dist / 2))));//1
		places.add(new Place(22, centerx, centery - dist));//2
		places.add(new Place(23, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery - (dist / 2)));//3
		places.add(new Place(24, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery + (dist / 2)));//4
		places.add(new Place(25, centerx, centery + dist));//5
		places.add(new Place(26, (int) (centerx - (dist / 2) * Math.sqrt(3)),
				centery + (dist / 2)));//6
		places.add(new Place(30, centerx, centery));//C
	}
	/**
	 * Fonction mettant a jour la destination d'un vehicule donne
	 * @param ID L'identifiant du vehicule concerne
	 * @param dep Son pointde depart
	 * @param ar Son point d'arrivee
	 */
	public void updateCoords(int ID, int suivant) {
		//Si c'est la fin du trajet
		if(suivant == 99){
			for (Vehicule v : vehicules) {
				//On enleve le vehicule graphique
				if (v.iD == ID) {
					vehicules.remove(v);
				}
			}
		}else{
			for (Vehicule v : vehicules) {
				if (v.iD == ID) {
					//ATTENTION ! FAUX ! Il faut readapter avec les bons identifiants
					v.xdest = places.get(suivant).x;
					v.xdest = places.get(suivant).y;
				}
			}
		}
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Private classes
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * Listener du bouton d'envoi d'une nouvelle requete
	 * @author Hellong
	 */
	private class SendListener implements ActionListener{
		int dep, ar;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			dep=Integer.parseInt((String) depart.getSelectedItem());
			ar=Integer.parseInt((String) arrivee.getSelectedItem());
			try {
				verif.newRequest(dep, ar);
			} catch (RequeteException | RDepartException e1) {

			}
		}
		
	}
	/**
	 * Le MapPan contient la map, et donc les differents vehicules qui se deplacent
	 * @author Hellong
	 *
	 */
	private class MapPan extends MainPan {
		Image img, veh0, veh1, veh2, veh3, veh4;
		/**
		 * Constructeur par defaut, qui charge les differentes images des vehicules
		 */
		public MapPan() {
			try {
				img = ImageIO
						.read(this.getClass().getResource("Map_proj2.png"));
				veh0 = ImageIO.read(this.getClass().getResource("veh0.png"));
				veh1 = ImageIO.read(this.getClass().getResource("veh1.png"));
				veh2 = ImageIO.read(this.getClass().getResource("veh2.png"));
				veh3 = ImageIO.read(this.getClass().getResource("veh3.png"));
				veh4 = ImageIO.read(this.getClass().getResource("veh4.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/**
		 * Surcharge de la fonction paintComponent, pour peindre la map et les vehicules
		 */
		protected void paintComponent(Graphics g) {
			Image temp;
			int decalx=0, decaly=0;
			Graphics2D g2d = (Graphics2D)g;
			g.setColor(new Color(52, 52, 52));
			//On redessine le fond
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			//On dessine la map
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0,
					this.getWidth(), this.getHeight(), null);
			//On dessine les vehicules
			for (Vehicule v : vehicules) {
				//A chaque type de vehicule correspond une image differente
				switch (v.type) {
				case 1:
					temp=veh1;
					break;
				case 2:
					temp=veh2;
					break;
				case 3:
					temp=veh3;
					break;
				case 4:
					temp=veh4;
					break;
				default:
					temp=veh0;
					break;
				}
				/*
				 *On tourne l'image pour qu'elle s'aligne sur la trajectoire du vehicule
				 *Ensuite on la deplace aux coordonnees voulues 
				 */
				AffineTransform rotation = new AffineTransform();
				double angle = Math.acos((v.xdest-v.xi)/Math.sqrt(Math.pow(v.xdest-v.xi,2)+Math.pow(v.ydest-v.yi,2)));
				if(v.xdest<v.xi){
					decalx=(int) (temp.getHeight(null)*Math.cos(1.57-angle));
					decaly=(int) (temp.getHeight(null)*Math.sin(1.57-angle));
				}
				if(v.xdest<v.xi || v.ydest<v.yi){
					if(v.xdest<v.xi && v.ydest>v.yi){
						
					}else{
						angle = -angle;
					}
				}
				
				rotation.translate(v.x-temp.getWidth(null)/2 + decalx, v.y-temp.getHeight(null)/2  + decaly);
				rotation.rotate(angle,(int)(temp.getWidth(null)/2),(int)(temp.getHeight(null)/2));
				if(decalx != 0){
					rotation.scale(1.0, -1.0);
				}
				g2d.drawImage(temp, rotation, null);
			}
		}
	}
	/**
	 * La classe Place est seulement destinee a stocker un identifiant et les coordonnees associees
	 * @author Hellong
	 *
	 */
	private class Place {

		int iD;
		int x;
		int y;
		/**
		 * Constructeur par defaut de la classe
		 * @param iD Son identifiant
		 * @param x Abscisse de la place
		 * @param y Ordonnee de la place
		 */
		public Place(int iD, int x, int y) {
			this.iD = iD;
			this.x = x;
			this.y = y;
		}
	}
	/**
	 * Le vehicule est la version graphique de celui du modele : il est charge d'afficher le deplacement de celui-ci
	 * @author Hellong
	 *
	 */
	public class Vehicule extends Thread {

		int iD, x, y, xi, yi, xdest, ydest, type;
		/**
		 * Constructeur du vehicule
		 * @param iD Identifiant du vehicule
		 * @param x Abscisse de depart
		 * @param y Ordonnee de depart
		 * @param type Type du vehicule (influe sur so apparence)
		 * @param name Nom du thread
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
			this.xi=x;
			this.yi=y;
		}
		/**
		 * Fonction run() du thread
		 */
		public void run(){
			while(true){
				//Si la destination a ete modifiee
				if(x != xdest || y != ydest){
					//On calcule les deltas necessaires aux deplacements
					float dX = (xdest - x)/(float)200;
				    float dY = (ydest - y)/(float)200;
				    float xt = x, yt = y;
					//Tant qu'on est pas arrive a la destination
				    while(x != xdest || y != ydest){
				    	try {
							sleep(25);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	//on se deplace
				    	xt += dX;
				    	yt += dY;
				    	x = Math.round(xt);
				    	y = Math.round(yt);
				    	//et on relance le paint
				    	map.repaint();
				    }
				    //Lorsqu'on est arrive, on le notifie
				    verif.notifArrivee(iD);
				}
				//Sinon on attend une modification
				else{
					try {
						sleep(200);
					} catch (InterruptedException e){
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

	
	//appellee lorsqu'un nouveau vehicule graphique doit etre cree (debut d'un trajet)
	@Override
	public int updateDebutMission(int dep) {
		//ATTENTION ! FAUX ! IL FAUT READAPTER L'IDENTIFIANT DU DEPART AVEC LES PLACES D'ENTREE
		Vehicule veh = new Simulation.Vehicule(iD, places.get(dep).x, places.get(dep).y, 3, "toto");
		iD++;
		vehicules.add(veh);
		veh.start();
		return iD-1;
	}
}
