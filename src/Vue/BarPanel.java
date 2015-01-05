package Vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * <b>Panel charge de remplacer le look & feel classique du système</b>
 * <p>
 * Il permet la fermeture et la reduction de la fenetre, et egalement son
 * deplacement
 * </p>
 * 
 * @author florian + theo
 * @version 1.4
 */
public class BarPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int x, y;
	private Fenetre mere;
	private JButton close, reduce;

	/**
	 * Constructeur du panel : on y initialise les boutons de controle, ainsi
	 * que les listeners associes
	 * 
	 * @param mere
	 *            La fenetre qui contient le panel
	 */
	public BarPanel(final Fenetre mere) {
		// On stocke la fenetre-mere
		this.mere = mere;

		// On cree ensuite les boutons de controle
		ImageIcon icon = creerImage("close.png", "");
		ImageIcon icon2 = creerImage("reduce.png", "");
		close = new JButton("");
		reduce = new JButton("");

		// On ajouteles mouse listeners
		addMouseListener(new Adapter());
		addMouseMotionListener(new MotionAdapter());

		// Puis on initialise le panel
		this.setBackground(new Color(98, 148, 49));// 150,50,50
		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		this.setPreferredSize(new Dimension(350, 25));

		// On les rend transparents
		close.setContentAreaFilled(false);
		reduce.setContentAreaFilled(false);
		close.setBorderPainted(false);
		reduce.setBorderPainted(false);
		close.setRolloverEnabled(false);
		reduce.setRolloverEnabled(false);
		close.setFocusPainted(false);
		reduce.setFocusPainted(false);

		// Et on leur attribue des images
		close.setIcon(icon);
		reduce.setIcon(icon2);

		// Enfin, on ajoute les action listeners
		close.addActionListener(new manageButtonListener());
		reduce.addActionListener(new manageButtonListener());

		this.add(reduce);
		this.add(close);
	}

	/**
	 * Creation d'une image (format Icon) a partir d'un fichier
	 * 
	 * @param path
	 *            Le chemin de l'image par rapport à la classe
	 * @param description
	 *            Sa description
	 */
	protected ImageIcon creerImage(String path, String description) {
		// On convertit le chemin en URL
		URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			// Et on construit l'image
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Impossible de trouver: " + path);
			return null;
		}
	}

	private class Adapter extends MouseAdapter {
		// Lors d'un clic sur le panel
		public void mousePressed(MouseEvent e) {
			// On recupere la position du curseur
			x = e.getX();
			y = e.getY();
		}
	}

	private class MotionAdapter extends MouseMotionAdapter {
		// Lors du deplacement de la souris, clic enfonce
		public void mouseDragged(MouseEvent e) {

			// On recupere les coordonnees de la fenetre
			int X = mere.getLocation().x;
			int Y = mere.getLocation().y;

			// On calcule la variation de positon
			int deltaX = (X + e.getX()) - (X + x);
			int deltaY = (Y + e.getY()) - (Y + y);

			// Et on deplace la fenetre a sa nouvelle place
			X = X + deltaX;
			Y = Y + deltaY;
			mere.setLocation(X, Y);
		}
	}

	private class manageButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == close) {
				System.exit(0);
			} else {
				mere.setState(JFrame.ICONIFIED);
			}
		}
	}
}
