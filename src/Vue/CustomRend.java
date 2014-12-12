package Vue;
 
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
 

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
 
/**
 * Customer renderer for JComboBox
 * @author www.codejava.net
 *
 */
public class CustomRend	 extends JPanel implements ListCellRenderer {
    private JLabel labelItem = new JLabel();
     
    public CustomRend() {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.insets = new Insets(0, 0, 0, 0);
         
        labelItem.setOpaque(true);
        labelItem.setHorizontalAlignment(JLabel.LEFT);
         
        add(labelItem, constraints);
        setBackground(Color.LIGHT_GRAY);
    }
     
    @Override
    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        String countryItem = (String) value;
 
        // set country name
        labelItem.setText(countryItem);
         
         
        if (isSelected) {
        	labelItem.setBackground(new Color(98, 148, 49));
        	//labelItem.setIcon(new ImageIcon(getClass().getResource( "b1.png" )));
        	//labelItem.setHorizontalTextPosition(SwingConstants.CENTER);
        	labelItem.setForeground(new Color(52,52,52));
        } else {
            labelItem.setForeground(new Color(98, 148, 49));
            labelItem.setBackground(new Color(52,52,52));
        }
         
        return this;
    }
 
}