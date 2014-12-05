package Vue;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controleur.Verificateur;
import Modele.Observer;

public class Fenetre extends JFrame implements Observer {
	private Verificateur verif;
	private MainPan mp;
	private JPanel secondpan;
	private JLabel titre;
	private JButton Bcredits, Bmanu, Bauto, Bopt, close, reduce;
	private BarPanel barpan;
	private String theme;
	private boolean sound;

	public Fenetre(Verificateur v) {
		this.verif = v;
		this.setUndecorated(true);
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		this.setSize(350, 500);
		this.theme = "Dark";
		this.sound=false;
		this.setTitle("Road Simulator 2014");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		createBarPanel();
		createCompos();
		this.setContentPane(mp);
		this.setVisible(true);
	}
	private void createBarPanel(){
		barpan = new BarPanel(this);
		barpan.setBackground(new Color(150, 50, 50));
		barpan.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		barpan.setPreferredSize(new Dimension(350, 25));
		ImageIcon icon = createImageIcon("close.png", "");
		ImageIcon icon2 = createImageIcon("reduce.png", "");
		close = new JButton("");
		reduce = new JButton("");
		close.setContentAreaFilled(false);
		reduce.setContentAreaFilled(false);
		close.setBorderPainted(false);
		reduce.setBorderPainted(false);
		close.setRolloverEnabled(false);
		reduce.setRolloverEnabled(false);
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
		mp = new MainPan("try1.jpg");
		mp.setLayout(new BorderLayout());
		secondpan = new JPanel();
		Icon icon = new ImageIcon(getClass().getResource("titre.png"));   
		titre = new JLabel(" ", icon, JLabel.CENTER);
		Bmanu = new JButton("Mode Manuel");
		Bauto = new JButton("Mode Automatique");
		Bopt = new JButton("Options");
		Bcredits = new JButton("Credits");
		secondpan.setPreferredSize(new Dimension(170, 200));
		secondpan.setBackground(new Color(0,0,0,0));
		secondpan.add(Bmanu);
		secondpan.add(Bauto);
		secondpan.add(Bopt);
		secondpan.add(Bcredits);
		mp.add(barpan, BorderLayout.NORTH);
		mp.add(titre, BorderLayout.CENTER);
		mp.add(secondpan, BorderLayout.SOUTH);
	}
	
	
	
	@Override
	public void update(String str) {
	}
	
	protected void paintComponent1(Graphics g) {
		try {
			//Painting the image
			g.drawImage(ImageIO.read(this.getClass().getResource("theme"+theme+".png")),
					0, 0, this.getWidth(), this.getHeight(), 0, 57,
				this.getWidth(), this.getHeight()+57, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
