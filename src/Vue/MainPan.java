package Vue;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * <b>MainPan eest un panel avec une image de fond</b>
 * <p>
 * Cette classe permet de definir un panel personnalise, avec pour background
 * l'image voulue
 * </p>
 * 
 * @author florian + theo
 * @version 0.1
 */
public class MainPan extends JPanel {
	/**
	 * Nom de l'image de fond, ainsi que son format. Ex : monImage.jpg
	 */
	private String path = "try2.jpg";
	private static final long serialVersionUID = 1L;

	public MainPan() {
		super();
		this.setBackground(new Color(52, 52, 52));
	}
}
