package Exception;

import javax.swing.JOptionPane;

public class RequeteException extends Exception {

	private static final long serialVersionUID = 1L;

	public RequeteException() {
		JOptionPane.showMessageDialog(null, "D�sol�, \n"
				+ "le passager est d�j� arriv� !\n", "Hint",
				JOptionPane.INFORMATION_MESSAGE);

	}
}
