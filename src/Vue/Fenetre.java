package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controleur.Verificateur;
import Modele.Observer;

public class Fenetre extends JFrame implements Observer {
	private Verificateur verif;
	private JPanel mainpan, secondpan;
	private JLabel titre;
	private JButton Bcredits, Bmanu, Bauto, Bopt;

	public Fenetre(Verificateur v) {
		this.verif = v;
		this.setSize(350, 500);
		this.setTitle("Road Simulator 2014");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		createCompos();
		this.setContentPane(mainpan);
		this.setVisible(true);
	}

	private void createCompos() {
		mainpan = new JPanel();
		secondpan = new JPanel();
		titre = new JLabel("Road Simulator 2014");
		Bmanu = new JButton("Mode Manuel");
		Bauto = new JButton("Mode Automatique");
		Bopt = new JButton("Options");
		Bcredits = new JButton("Credits");
		secondpan.setPreferredSize(new Dimension(170, 200));
		secondpan.add(Bmanu);
		secondpan.add(Bauto);
		secondpan.add(Bopt);
		secondpan.add(Bcredits);
		mainpan.setLayout(new BorderLayout());
		mainpan.add(titre, BorderLayout.NORTH);
		mainpan.add(secondpan, BorderLayout.CENTER);
		
		
	}

	@Override
	public void update(String str) {
	}

}
