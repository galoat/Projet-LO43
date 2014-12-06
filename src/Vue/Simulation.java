package Vue;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Simulation extends MainPan {

	private static final long serialVersionUID = 1L;
	private JLabel maplabel;

	public Simulation(boolean auto, Fenetre mere) {
		super();
		mere.setSize(new Dimension(350, 550));
		this.setPreferredSize(new Dimension(350, 525));
		this.setLayout(new BorderLayout());
		Icon icon = new ImageIcon(getClass().getResource("Map_proj.png"));
		maplabel = new JLabel(" ", icon, JLabel.CENTER);
		// maplabel.setPreferredSize(new Dimension(30, 50));
		// titre.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(maplabel, BorderLayout.NORTH);
	}
}
