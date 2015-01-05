package Vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 * <b>Renderer personnalise de JComboBox</b>
 * <p>
 * Classe permettant de modifier l'apparence des JComboBox comme on le souhaite
 * </p>
 * 
 * @author florian + theo
 * @version 1.0
 */
public class CustomRend extends JPanel implements ListCellRenderer<Object> {
	private static final long serialVersionUID = 1L;
	private JLabel labelItem = new JLabel();

	/**
	 * Constructeur de la classe
	 */
	public CustomRend() {
		// Initialisation
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		// On definit la taille de la "boite" contenant la liste
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(0, 0, 0, 0);

		labelItem.setOpaque(true);
		labelItem.setHorizontalAlignment(JLabel.LEFT);

		// On termine
		add(labelItem, constraints);
		setBackground(Color.LIGHT_GRAY);
	}

	/**
	 * Rendu d'un item qu'on affiche lorsqu'il est selectionne, etc...
	 */
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		labelItem.setText((String) value);

		if (isSelected) {
			labelItem.setBackground(new Color(98, 148, 49));
			labelItem.setForeground(new Color(52, 52, 52));
		} else {
			labelItem.setForeground(new Color(98, 148, 49));
			labelItem.setBackground(new Color(52, 52, 52));
		}
		return this;
	}

}