package Exception;

import javax.swing.JOptionPane;

public class RequeteException extends Exception {

	private static final long serialVersionUID = 1L;

	public RequeteException() {
		JOptionPane.showMessageDialog(null, "Désolé, \n"
				+ "le passager est déjà arrivé !\n", "Hint",
				JOptionPane.INFORMATION_MESSAGE);

	}
}
