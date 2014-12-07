package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Simulation extends MainPan {

	private static final long serialVersionUID = 1L;
	private JPanel buttonpan, missionpan, controlpan;
	private MapPan map;
	private JButton pause, reset, envoyer;
	private JComboBox depart, arrivee;
	private JLabel dep, ar;
	private String[] tab = {"1", "2", "3", "4", "5", "6"};

	

	public Simulation(boolean auto, Fenetre mere) {
		super();
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
		reset = new JButton ("Reinitialiser");
		
		depart = new JComboBox(tab);
		arrivee = new JComboBox(tab);
		
		dep = new JLabel("Depart : ");
		ar = new JLabel ("Arrivee : ");
		dep.setForeground(new Color(90, 150, 12));
		ar.setForeground(new Color(90, 150, 12));
		
		envoyer = new JButton("Envoyer");
		
		missionpan.add(dep);
		missionpan.add(depart);
		missionpan.add(ar);
		missionpan.add(arrivee);
		missionpan.add(envoyer);
		if(auto){
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
	}

	private class MapPan extends MainPan {
		/**
		 * Surcharge de la fonction paintComponent pour dessiner l'image en fond
		 */

		protected void paintComponent(Graphics g) {
			try { // On dessine l'image(avec des couples de coordonnees pour
					// chaque coin)
				g.drawImage(ImageIO.read(this.getClass().getResource("Map_proj.png")), 0,
						0, this.getWidth(), this.getHeight(), 0, 0,
						this.getWidth(), this.getHeight(), null);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
