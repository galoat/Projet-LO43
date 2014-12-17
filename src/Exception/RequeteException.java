package Exception;

import javax.swing.JOptionPane;

public class RequeteException extends Exception {

	private static final long serialVersionUID = 1L;

	public RequeteException() {
		JOptionPane.showMessageDialog(null, "Desole, \n"
				+ "le passager est deje arrive !\n", "Hint",
				JOptionPane.INFORMATION_MESSAGE);

	}
}
