package Vue;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 * <b>MainPan eest un panel avec une image de fond</b>
 * <p>
 * Cette classe permet de definir un panel personnalise, avec pour background l'image voulue
 * </p>
 * @author florian + theo
 *	@version 0.1
 */
public class MainPan extends JPanel{
	/**
	 * Nom de l'image de fond, ainsi que son format. Ex : monImage.jpg
	 */
	private String path = "try1.jpg";
	private static final long serialVersionUID = 1L;
	public MainPan() {
		super();
	}
	/**
	 * Surcharge de la fonction paintComponent pour dessiner l'image en fond
	 */
	protected void paintComponent(Graphics g) {
		try {
			//On dessine l'image (avec des couples de coordonnees pour chaque coin)
			g.drawImage(ImageIO.read(this.getClass().getResource(path)),
					0, 0, this.getWidth(), this.getHeight(), 0, 0,
				this.getWidth()*2, this.getHeight()*2, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

