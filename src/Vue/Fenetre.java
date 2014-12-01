package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
	private JButton Bcredits, Bmanu, Bauto, Bopt, close, reduce;
	private BarPanel barpan;
	private String theme;
	private boolean sound;

	public Fenetre(Verificateur v) {
		this.verif = v;
		this.setUndecorated(true);
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		this.getContentPane().setLayout(new BorderLayout());
		this.setSize(350, 500);
		this.theme = "Dark";
		this.sound=false;
		this.setTitle("Road Simulator 2014");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		createBarPanel();
		createCompos();
		this.setVisible(true);
	}
	private void createBarPanel(){
		barpan = new BarPanel(this);
		barpan.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		barpan.setPreferredSize(new Dimension(350, 25));
		barpan.setBorder(BorderFactory.createLineBorder(Color.black));
		ImageIcon icon = createImageIcon("close.png", "");
		ImageIcon icon2 = createImageIcon("reduce.png", "");
		close = new JButton("");
		reduce = new JButton("");
		close.setIcon(icon);
		reduce.setIcon(icon2);
		barpan.add(reduce);
		barpan.add(close);
		
		
	}
	

	protected ImageIcon createImageIcon(String path, String description) {
	 	URL imgURL = getClass().getResource(path);
	 	if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
	 		System.err.println("Impossible de trouver: " + path);
			return null;
	 	}
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
		this.getContentPane().add(barpan, BorderLayout.NORTH);
		this.getContentPane().add(titre, BorderLayout.CENTER);
		this.getContentPane().add(secondpan, BorderLayout.SOUTH);
		
		
	}

	@Override
	public void update(String str) {
	}

}
