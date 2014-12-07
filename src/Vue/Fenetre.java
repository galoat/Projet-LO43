package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private JPanel buttonpan;
	private JLabel titre;
	private JButton Bcredits, Bmanu, Bauto, Bopt;
	private BarPanel barpan;
	private Simulation s;

	/**
	 * Constructeur de la fenetre : on y initialise les differents panels et
	 * comosants
	 * 
	 * @param v
	 *            Le verificateur associe a la fenetre dans le cadre du pattern
	 *            MVC
	 * @see Verificateur
	 */
	public Fenetre(Verificateur v) {
		//this.setIconImage(new ImageIcon("voiture.gif").getImage());
		this.verif = v;
		// On retire le look&feel classique
		this.setUndecorated(true);
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		this.setSize(350, 400);
		// ++++++++++++++++++++++++++++++++++++++++++
		// Pas encore implemente
		// ++++++++++++++++++++++++++++++++++++++++++
		this.setTitle("Road Simulator 2014");
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

	private void createFenPanMain() {
		fenpanmain = new MainPan();
		fenpanmain.setPreferredSize(new Dimension(350, 375));
		fenpanmain.setLayout(new BorderLayout());
		buttonpan = new JPanel();
		Icon icon = new ImageIcon(getClass().getResource("titre.png"));
		titre = new JLabel(" ", icon, JLabel.CENTER);
		titre.setPreferredSize(new Dimension(30, 50));
		// titre.setBorder(BorderFactory.createLineBorder(Color.black));
		Bmanu = new JButton("Mode Manuel");
		Bmanu.setAlignmentX(CENTER_ALIGNMENT);
		Bmanu.setMaximumSize(new Dimension(150, 30));
		Bmanu.setMinimumSize(new Dimension(150, 30));
		Bmanu.setPreferredSize(new Dimension(150, 30));
		Bauto = new JButton("Mode Automatique");
		Bauto.setAlignmentX(CENTER_ALIGNMENT);
		Bauto.setMaximumSize(new Dimension(150, 30));
		Bauto.setMinimumSize(new Dimension(150, 30));
		Bauto.setPreferredSize(new Dimension(150, 30));
		Bopt = new JButton("Options");
		Bopt.setAlignmentX(CENTER_ALIGNMENT);
		Bopt.setMaximumSize(new Dimension(150, 30));
		Bopt.setMinimumSize(new Dimension(150, 30));
		Bopt.setPreferredSize(new Dimension(150, 30));
		Bcredits = new JButton("Credits");
		Bcredits.setMaximumSize(new Dimension(150, 30));
		Bcredits.setMinimumSize(new Dimension(150, 30));
		Bcredits.setPreferredSize(new Dimension(150, 30));
		Bcredits.setAlignmentX(CENTER_ALIGNMENT);
		Bopt.addActionListener(new changeButtonListener());
		Bauto.addActionListener(new changeButtonListener());
		Bmanu.addActionListener(new changeButtonListener());
		Bcredits.addActionListener(new changeButtonListener());
		buttonpan.setPreferredSize(new Dimension(170, 200));
		buttonpan.setLayout(new BoxLayout(buttonpan, BoxLayout.PAGE_AXIS));
		buttonpan.setBackground(new Color(0, 0, 0, 0));
		buttonpan.add(Bmanu);
		buttonpan.add(Box.createRigidArea(new Dimension(5, 10)));
		buttonpan.add(Bauto);
		buttonpan.add(Box.createRigidArea(new Dimension(5, 10)));
		buttonpan.add(Bopt);
		buttonpan.add(Box.createRigidArea(new Dimension(5, 10)));
		buttonpan.add(Bcredits);
		// fenpanmain.add(barpan, BorderLayout.NORTH);
		fenpanmain.add(titre, BorderLayout.CENTER);
		fenpanmain.add(buttonpan, BorderLayout.SOUTH);
	}

	@Override
	public void update(String str) {
	}

	public void changerPan(int menuSelectionne) {
		switch (menuSelectionne) {
		case 0:
			this.getContentPane().remove(actual);
			actual = fenpanmain;
			this.getContentPane().add(actual);
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

	private class changeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == Bopt) {
				fenpanopt = new PanOpt();
				changerPan(1);
			}
			if (e.getSource() == Bauto) {
				s = new Simulation(true, Fenetre.this);
				changerPan(2);
			}
			if (e.getSource() == Bmanu) {
				s = new Simulation(false, Fenetre.this);
				changerPan(2);
			}
			if (e.getSource() == Bcredits) {
				fenpancredits = new PanCredits();
				changerPan(3);
			}
		}
	}
}
