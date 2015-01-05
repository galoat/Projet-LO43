package Vue;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 * <b>Editeur personnalise de JComboBox</b>
 * <p>
 * Classe permettant de modifier l'apparence des JComboBox comme on le souhaite
 * </p>
 * 
 * @author florian + theo
 * @version 1.0
 */
public class CustomEditor extends BasicComboBoxEditor {
	private JPanel pan = new JPanel();
	private JLabel labelItem = new JLabel();
	private String valeurSelect;

	/**
	 * Constructeur de la classe
	 */
	public CustomEditor() {
		// Initialisation
		pan.setLayout(new GridBagLayout());
		GridBagConstraints contraintes = new GridBagConstraints();

		// On definit la taille du "menu deroulant"
		contraintes.fill = GridBagConstraints.HORIZONTAL;
		contraintes.weightx = 1.0;
		contraintes.insets = new Insets(3, 3, 3, 0);

		// On definit l'apparence des lables a l'interieur
		labelItem.setOpaque(false);
		labelItem.setHorizontalAlignment(JLabel.LEFT);
		labelItem.setForeground(new Color(52, 52, 52));

		// On termine
		pan.add(labelItem, contraintes);
		pan.setBackground(new Color(98, 148, 49));
	}

	public Component getEditorComponent() {
		return this.pan;
	}

	public Object getItem() {
		return this.valeurSelect;
	}

	/**
	 * Surcharge de la fonction permettant d'ajouter une entree a la liste
	 * deroulante
	 * 
	 * @param item
	 *            L'item a ajouter a la liste deroulante
	 */
	public void setItem(Object item) {
		if (item == null) {
			return;
		}
		String defItem = (String) item;
		valeurSelect = defItem;
		labelItem.setText(valeurSelect);
	}
}