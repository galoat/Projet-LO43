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
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controleur.Verificateur;

public class Simulation extends MainPan {

	private static final long serialVersionUID = 1L;
	private JPanel buttonpan, missionpan, controlpan;
	private MapPan map;
	private JButton pause, reset, envoyer;
	private JComboBox depart, arrivee;
	private JLabel dep, ar;
	private String[] tab = { "1", "2", "3", "4", "5", "6" };
	private ArrayList<Place> places;
	private ArrayList<Vehicule> vehicules;
	private int centerx = 158, centery = 179, r = 25, dist = 132;
	private Verificateur verif;

	public Simulation(boolean auto, Fenetre mere, Verificateur verif) {
		super();
		this.verif = verif;
		vehicules = new ArrayList<Vehicule>();
		buildPlaces();
		mere.setSize(new Dimension(350, 475));
		this.setPreferredSize(new Dimension(350, 450));
		this.setLayout(new BorderLayout());
		Icon icon = new ImageIcon(getClass().getResource("Map_proj.png"));
		map = new MapPan();
		map.setPreferredSize(new Dimension(350, 350));
		buttonpan = new JPanel();
		missionpan = new JPanel();
		controlpan = new JPanel();
		buttonpan.setBackground(new Color(52, 52, 52));
		missionpan.setBackground(new Color(52, 52, 52));
		controlpan.setBackground(new Color(52, 52, 52));
		buttonpan.setLayout(new BorderLayout());
		buttonpan.setPreferredSize(new Dimension(350, 75));

		pause = new JButton("Pause");
		reset = new JButton("Reinitialiser");

		depart = new JComboBox(tab);
		arrivee = new JComboBox(tab);

		dep = new JLabel("Depart : ");
		ar = new JLabel("Arrivee : ");
		dep.setForeground(new Color(90, 150, 12));
		ar.setForeground(new Color(90, 150, 12));

		envoyer = new JButton("Envoyer");

		missionpan.add(dep);
		missionpan.add(depart);
		missionpan.add(ar);
		missionpan.add(arrivee);
		missionpan.add(envoyer);
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
		this.add(Box.createRigidArea(new Dimension(30, 20)), BorderLayout.NORTH);
		this.add(Box.createRigidArea(new Dimension(30, 20)), BorderLayout.EAST);
		this.add(Box.createRigidArea(new Dimension(30, 20)), BorderLayout.WEST);
		this.add(map, BorderLayout.CENTER);
		this.add(buttonpan, BorderLayout.SOUTH);
		
		//+ + + + + + + + + + + + + + + + + + + 
		//TEST
		//+ + + + + + + + + + + + + + + + + + + 
		
		Vehicule veh = new Simulation.Vehicule(0, 50, 400, 3, "toto");
		veh.xdest=250;
		veh.ydest=10;
		vehicules.add(veh);
		veh.start();
	}

	private void buildPlaces() {
		places = new ArrayList<Place>();
		places.add(new Place(0, centerx, centery));
		places.add(new Place(1, (int) (centerx - (dist / 2) * Math.sqrt(3)),
				(centery - (dist / 2))));
		places.add(new Place(2, centerx, centery - dist));
		places.add(new Place(3, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery - (dist / 2)));
		places.add(new Place(4, (int) (centerx + (dist / 2) * Math.sqrt(3)),
				centery + (dist / 2)));
		places.add(new Place(5, centerx, centery + dist));
		places.add(new Place(6,
				(int) (centerx - (dist / 2) * Math.sqrt(3)) - r, centery
						- (dist / 2)));
	}

	public void updatePath(int ID, int dep, int ar) {
		for (Vehicule v : vehicules) {
			if (v.iD == ID) {
				v.xdest = places.get(ID).x;
				v.xdest = places.get(ID).y;
			}
		}
	}

	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Private classes
	// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private class MapPan extends MainPan {
		/**
		 * Surcharge de la fonction paintComponent pour dessiner l'image en fond
		 */
		Image img, veh0, veh1, veh2, veh3, veh4;

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

		protected void paintComponent(Graphics g) {
			Image temp;
			Graphics2D g2d = (Graphics2D)g;
			g.setColor(new Color(52, 52, 52));
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			g.setColor(new Color(255, 255, 255));
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0,
					this.getWidth(), this.getHeight(), null);
			for (Vehicule v : vehicules) {
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
				AffineTransform rotation = new AffineTransform();
				double angle = Math.acos((v.xdest-v.xi)/Math.sqrt(Math.pow(v.xdest-v.xi,2)+Math.pow(v.ydest-v.yi,2)));
				if(v.xdest<v.xi || v.ydest<v.yi){
					angle = -angle;
				}
				//System.out.println(angle*57.295779513082);
				rotation.translate(v.x - temp.getWidth(null)/2, v.y - temp.getHeight(null)/2);
				rotation.rotate(angle,(int)(temp.getWidth(null)/2),(int)(temp.getHeight(null)/2));
				g2d.drawImage(temp, rotation, null);
				//g.drawImage(temp, v.x - temp.getWidth(null)/2, v.y - temp.getHeight(null)/2, null);
			}
		}
	}

	private class Place {

		int iD;
		int x;
		int y;

		public Place(int iD, int x, int y) {
			this.iD = iD;
			this.x = x;
			this.y = y;
		}
	}

	public class Vehicule extends Thread {

		int iD, x, y, xi, yi, xdest, ydest, type;

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
		
		public void run(){
		    float dX = (xdest - x)/(float)200;
		    float dY = (ydest - y)/(float)200;
		    float xt = x, yt = y;
			while(x != xdest || y != ydest){
		    	try {
					sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	xt += dX;
		    	yt += dY;
		    	x = Math.round(xt);
		    	y = Math.round(yt);
		    	map.repaint();
		    }
		    verif.notifArrivee(iD);
		}
	}
}
