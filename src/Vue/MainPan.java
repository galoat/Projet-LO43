package Vue;

import java.awt.Color;
import javax.swing.JPanel;

/**
 * <b>MainPan est le constituant principal des differents panels de la Vue</b>
 * <p>
 * Cette classe permet de definir un panel personnalise, avec pour background la
 * couleur voulue
 * </p>
 * 
 * @author florian + theo
 * @version 1.0
 */
public class MainPan extends JPanel {
	private static final long serialVersionUID = 1L;

	public MainPan() {
		super();
		this.setBackground(new Color(52, 52, 52));
	}
}
