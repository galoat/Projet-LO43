package Vue;
 
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
 
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxEditor;
 

public class CustomEditor extends BasicComboBoxEditor {
    private JPanel pan = new JPanel();
    private JLabel labelItem = new JLabel();
    private String valeurSelect;
     
    public CustomEditor() {
        pan.setLayout(new GridBagLayout());
        GridBagConstraints contraintes = new GridBagConstraints();
        contraintes.fill = GridBagConstraints.HORIZONTAL;
        contraintes.weightx = 1.0;
        contraintes.insets = new Insets(3, 3, 3, 0);
        labelItem.setOpaque(false);
        labelItem.setHorizontalAlignment(JLabel.LEFT);
    	labelItem.setForeground(new Color(52,52,52));
        pan.add(labelItem, contraintes);
        pan.setBackground(new Color(98, 148, 49));       
    }
     
    public Component getEditorComponent() {
        return this.pan;
    }
     
    public Object getItem() {
        return this.valeurSelect;
    }
     
    public void setItem(Object item) {
        if (item == null) {
            return;
        }
        String defItem = (String) item;
        valeurSelect = defItem;
        labelItem.setText(valeurSelect);   
    }
}