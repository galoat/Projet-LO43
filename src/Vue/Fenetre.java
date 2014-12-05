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

public class Fenetre extends JFrame implements Observer {
	private Verificateur verif;
	private MainPan fenpanmain, fenpanopt, fenpancredits, fenpansimul, actual;
	private JPanel buttonpan;
	private JLabel titre;
	private JButton Bcredits, Bmanu, Bauto, Bopt, close, reduce;
	private BarPanel barpan;
	private String theme;
	private boolean sound;

	public Fenetre(Verificateur v) {
		this.verif = v;
		this.setUndecorated(true);
		this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		this.setSize(350, 400);
		this.theme = "Dark";
		this.sound=false;
		this.setTitle("Road Simulator 2014");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		createBarPanel();
		this.getContentPane().add(barpan);
		createFenPanMain();
		actual=fenpanmain;
		this.getContentPane().add(actual);
		/*createFenPanOpt();
		createFenPanCredits();
		createFenPanSimul();
		createCompos();*/
		
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
	 	
	private void createFenPanMain() {
		fenpanmain = new MainPan();
		fenpanmain.setPreferredSize(new Dimension(350,375));
		fenpanmain.setLayout(new BorderLayout());
		buttonpan = new JPanel();
		Icon icon = new ImageIcon(getClass().getResource("titre.png"));   
		titre = new JLabel(" ", icon, JLabel.CENTER);
		titre.setPreferredSize(new Dimension(30, 50));
		//titre.setBorder(BorderFactory.createLineBorder(Color.black));
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
		buttonpan.setPreferredSize(new Dimension(170, 200));
		buttonpan.setLayout(new BoxLayout(buttonpan, BoxLayout.PAGE_AXIS));
		buttonpan.setBackground(new Color(0,0,0,0));
		buttonpan.add(Bmanu);
		buttonpan.add(Box.createRigidArea(new Dimension(5,10)));
		buttonpan.add(Bauto);
		buttonpan.add(Box.createRigidArea(new Dimension(5,10)));
		buttonpan.add(Bopt);
		buttonpan.add(Box.createRigidArea(new Dimension(5,10)));
		buttonpan.add(Bcredits);
		//fenpanmain.add(barpan, BorderLayout.NORTH);
		fenpanmain.add(titre, BorderLayout.CENTER);
		fenpanmain.add(buttonpan, BorderLayout.SOUTH);
	}
	
	private void createFenPanOpt() {
		fenpanopt = new MainPan();
		fenpanopt.setPreferredSize(new Dimension(350,375));
		fenpanopt.setLayout(new BorderLayout());
		Icon icon = new ImageIcon(getClass().getResource("titre.png"));   
		titre = new JLabel(" ", icon, JLabel.CENTER);
		titre.setPreferredSize(new Dimension(30, 50));
		//titre.setBorder(BorderFactory.createLineBorder(Color.black));
		
		fenpanopt.add(titre, BorderLayout.CENTER);
		fenpanopt.add(buttonpan, BorderLayout.SOUTH);
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
