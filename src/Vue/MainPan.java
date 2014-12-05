package Vue;

import java.awt.Graphics;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
/*A panel to display the images used as a border to the timer*/
public class MainPan extends JPanel{
private String path;
	private static final long serialVersionUID = 1L;
	public MainPan(String s) {
		super();
		path=s;
		//Setting the theme, based on the main frame's one
	}
	
	/*
	 * Called when : the pane has to be repaint
	 * By : the ImagePanel
	 * Action : Repaints the panel
	 */
	protected void paintComponent(Graphics g) {
		try {
			//Painting the image
			g.drawImage(ImageIO.read(this.getClass().getResource("try1.jpg")),
					0, 0, this.getWidth(), this.getHeight(), 0, 0,
				this.getWidth()*2, this.getHeight()*2, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

