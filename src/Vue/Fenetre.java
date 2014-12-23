package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import Controleur.Verificateur;
import Modele.Observer;

/**
 * <b>Classe principale de la vue</b>
 * <p>
 * Cette classe est celle de la fenetre, chargee d'afficher la totalite du
 * programme. Elle dispose elle meme de plusieurs interfaces :
 * <ul>
 * <li>La vue principale, affichee au lancement</li>
 * <li>Les options</li>
 * <li>Les credits</li>
 * <li>La simulation en elle-meme</li>
 * </ul>
 * </p>
 * 
 * @author florian + theo
 * @version 2.5
 */
public class Fenetre extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;
	private Verificateur verif;
	private MainPan fenpanmain, fenpanopt, fenpancredits, actual;
	private JPanel buttonpan, optpan, controlpan;
	private JLabel titre, vitesseLabel, flotteLabel;
	private JButton Bcredits, Bmanu, Bauto, Bopt, Bvalider, Bannuler;
	private BarPanel barpan;
	private Simulation s;
	private JSlider vitesse, flotte;
	private int tailleflotte, valvitesse;

	/**
	 * Constructeur de la fenetre : on y initialise les differents panels et
	 * composants
	 * 
	 * @param v
	 *            Le verificateur associe a la fenetre dans le cadre du pattern
	 *            MVC
	 * @see Verificateur
	 */
	public Fenetre(Verificateur v) {
		// Initialisation
		this.verif = v;
		valvitesse = 20;
		tailleflotte = 5;

		// On change l'icône du programme
		try {
			this.setIconImage(ImageIO.read(this.getClass().getResource(
					"Hexa.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// On retire le look&feel classique
		this.setUndecorated(true);

		// On initialise la fenetre
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		this.setSize(350, 400);
		this.setTitle("Road");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		// On initialise le panel qui remplace le look&fell
		barpan = new BarPanel(this);
		this.getContentPane().add(barpan);

		// On cree le panel principal
		createFenPanMain();

		// On demarre sur le panel principal
		actual = fenpanmain;
		this.getContentPane().add(actual);
		this.setVisible(true);
	}

	/**
	 * Fonction chargee de la creation du panel de demarrage
	 */
	private void createFenPanMain() {
		// Initialisation
		fenpanmain = new MainPan();
		buttonpan = new JPanel();
		Icon icon = new ImageIcon(getClass().getResource("titre.png"));
		titre = new JLabel(" ", icon, JLabel.CENTER);

		// On initialise les panels
		fenpanmain.setPreferredSize(new Dimension(350, 375));
		fenpanmain.setLayout(new BorderLayout());
		titre.setPreferredSize(new Dimension(30, 50));
		buttonpan.setPreferredSize(new Dimension(170, 200));
		buttonpan.setLayout(new BoxLayout(buttonpan, BoxLayout.PAGE_AXIS));
		buttonpan.setBackground(new Color(0, 0, 0, 0));

		// On cree les boutons
		Bmanu = prepButton("Mode Manuel");
		Bauto = prepButton("Mode Automatique");
		Bopt = prepButton("Options");
		Bcredits = prepButton("Credits");

		// On leur attribue des listeners
		Bmanu.addActionListener(new changeButtonListener());
		Bauto.addActionListener(new changeButtonListener());
		Bopt.addActionListener(new changeButtonListener());
		Bcredits.addActionListener(new changeButtonListener());

		// Et on assemble
		buttonpan.add(Bmanu);
		buttonpan.add(Box.createRigidArea(new Dimension(5, 10)));
		buttonpan.add(Bauto);
		buttonpan.add(Box.createRigidArea(new Dimension(5, 10)));
		buttonpan.add(Bopt);
		buttonpan.add(Box.createRigidArea(new Dimension(5, 10)));
		buttonpan.add(Bcredits);
		fenpanmain.add(titre, BorderLayout.CENTER);
		fenpanmain.add(buttonpan, BorderLayout.SOUTH);
	}

	/**
	 * Fonction chargee de la creation du panel des options
	 */
	public void createOptPan() {
		// Initialisation
		fenpanopt = new MainPan();
		optpan = new JPanel();
		controlpan = new JPanel();
		vitesseLabel = new JLabel("Vitesse des vehicules");
		flotteLabel = new JLabel("Taille de la flotte");
		flotte = new JSlider();
		vitesse = new JSlider();

		// On initialise les panels
		fenpanopt.setPreferredSize(new Dimension(350, 375));
		fenpanopt.setLayout(new BorderLayout());
		optpan.setPreferredSize(new Dimension(170, 200));
		optpan.setLayout(new BoxLayout(optpan, BoxLayout.PAGE_AXIS));
		optpan.setBackground(new Color(0, 0, 0, 0));
		controlpan.setBackground(new Color(0, 0, 0, 0));

		// Puis les deux labels
		vitesseLabel.setForeground(new Color(98, 148, 49));
		flotteLabel.setForeground(new Color(98, 148, 49));

		// Enfin les boutons
		Bvalider = prepButton("Valider");
		Bannuler = prepButton("Annuler");

		// Et les listeners
		Bvalider.addActionListener(new optListener());
		Bannuler.addActionListener(new optListener());

		// On configure les deux sliders
		vitesse.setBackground(new Color(52, 52, 52));
		vitesse.setForeground(new Color(98, 148, 49));
		vitesse.setMaximum(100);
		vitesse.setMinimum(1);
		vitesse.setValue(20);
		vitesse.setPaintTicks(true);
		vitesse.setPaintLabels(true);
		vitesse.setMinorTickSpacing(5);
		vitesse.setMajorTickSpacing(25);
		vitesse.setSnapToTicks(true);

		flotte.setBackground(new Color(52, 52, 52));
		flotte.setForeground(new Color(98, 148, 49));
		flotte.setMaximum(20);
		flotte.setMinimum(1);
		flotte.setValue(5);
		flotte.setPaintTicks(true);
		flotte.setPaintLabels(true);
		flotte.setMinorTickSpacing(1);
		flotte.setMajorTickSpacing(5);
		flotte.setSnapToTicks(true);

		// Puis on assemble
		controlpan.add(Bvalider);
		controlpan.add(Bannuler);
		optpan.add(Box.createRigidArea(new Dimension(30, 30)));
		optpan.add(flotteLabel);
		optpan.add(Box.createRigidArea(new Dimension(5, 10)));
		optpan.add(flotte);
		optpan.add(Box.createRigidArea(new Dimension(20, 20)));
		optpan.add(vitesseLabel);
		optpan.add(Box.createRigidArea(new Dimension(5, 10)));
		optpan.add(vitesse);
		fenpanopt.add(optpan, BorderLayout.CENTER);
		fenpanopt.add(controlpan, BorderLayout.SOUTH);
	}

	/**
	 * Fonction chargee de la creation du panel des credits
	 */
	public void createCreditsPan() {

	}

	/**
	 * Fonction chargee de la creation et du parametrage des boutons
	 * 
	 * @param s
	 *            Le nom du bouton
	 */
	public JButton prepButton(String s) {
		// On construit le bouton, avec son apparence personnalisee
		JButton b = new JButton(s, new ImageIcon(getClass().getResource(
				"b1.png")));
		b.setHorizontalTextPosition(SwingConstants.CENTER);
		b.setAlignmentX(CENTER_ALIGNMENT);
		// On force la taille du bouton
		b.setMaximumSize(new Dimension(150, 30));
		b.setMinimumSize(new Dimension(150, 30));
		b.setPreferredSize(new Dimension(150, 30));
		// On choisit l'apparence du bouton lors d'un clic
		b.setPressedIcon(new ImageIcon(getClass().getResource("b2.png")));
		b.setRolloverEnabled(false);
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		return b;
	}

	@Override
	public void update(String str) {
	}

	/**
	 * Fonction chargee du changement de panel (passage du principal aux
	 * options, etc...)
	 * 
	 * @param menuSelectionne
	 *            L'identifiant du panel a afficher
	 */
	public void changerPan(int menuSelectionne) {
		switch (menuSelectionne) {
		case 0:
			// On fait disparaitre l'actuel
			this.getContentPane().remove(actual);
			// On definit le nouveau panel par defaut
			actual = fenpanmain;
			this.getContentPane().add(actual);
			// Et on repeint
			this.repaint();
			this.revalidate();
			break;
		case 1:
			this.getContentPane().remove(actual);
			actual = fenpanopt;
			this.getContentPane().add(actual);
			this.repaint();
			this.revalidate();
			break;
		case 2:
			this.getContentPane().remove(actual);
			actual = s;
			this.getContentPane().add(actual);
			this.repaint();
			this.revalidate();
			break;
		case 3:
			this.getContentPane().remove(actual);
			actual = fenpancredits;
			this.getContentPane().add(actual);
			this.repaint();
			this.revalidate();
			break;
		default:
			System.err.println("Menu selectionne non valide");
			break;
		}
	}

	/**
	 * <b>Listener du chagement de panel</b>
	 * <p>
	 * Cette classe interne est destinee a actionner le changement de panel lors
	 * de la pression sur un bouton... Le panel a afficher depend alors du
	 * bouton appuye.
	 * </p>
	 * 
	 * @author florian + theo
	 * @version 1.0
	 */
	private class changeButtonListener implements ActionListener {
		/**
		 * Surcharge de la fonction actionnee lors d'un clic
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// Si on veut acceder aux options
			if (e.getSource() == Bopt) {
				createOptPan();
				changerPan(1);
			}
			// Si on veut acceder au mode automatique
			if (e.getSource() == Bauto) {
				// On lance la simulation
				verif.debutSim(tailleflotte);
				s = new Simulation(true, Fenetre.this, verif, valvitesse,
						tailleflotte);
				changerPan(2);
			}
			// Si on veut acceder au mode manuel
			if (e.getSource() == Bmanu) {
				// On lance la simulation
				verif.debutSim(tailleflotte);
				s = new Simulation(false, Fenetre.this, verif, valvitesse,
						tailleflotte);
				changerPan(2);
			}
			// Si on veut acceder aux credits
			if (e.getSource() == Bcredits) {
				createCreditsPan();
				changerPan(3);
			}
		}
	}

	/**
	 * <b>Listener des options</b>
	 * <p>
	 * Cette classe interne est destinee a appliquer (ou non) les paramtres
	 * definis dans les options lors de la pression sur un bouton...
	 * </p>
	 * 
	 * @author florian + theo
	 * @version 1.0
	 */
	private class optListener implements ActionListener {

		/**
		 * Surcharge de la fonction actionnee lors d'un clic
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// Si on a valide les parametres
			if (e.getSource() == Bvalider) {
				// On redefinis les variables
				valvitesse = vitesse.getValue();
				tailleflotte = flotte.getValue();
			}
			// Et on revient sur le panel principal
			changerPan(0);
		}
	}

	@Override
	public void updateCoords(int iD, int suivant) {
		// TODO Auto-generated method stub

	}

	@Override
	public int updateDebutMission(int dep) {
		return 0;

	}

	/**
	 * Fonction chargee de transmettre a la simulation le nombre de passagers en
	 * attente d'une voiture, ou en instance de depart...
	 * 
	 * @param attdepart
	 *            Nombre de passagers en instance de depart
	 * @param attvoiture
	 *            Nombre de passagers en attente d'une voiture
	 */
	@Override
	public void updatePassagers(int attdepart, int attvoiture) {
		s.updatePassagers(attdepart, attvoiture);
	}
}
