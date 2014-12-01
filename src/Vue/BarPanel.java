package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


import javax.swing.JPanel;
/*This Panel allows the user to move the main frame, even if it has no decoration*/
public class BarPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private int x, y;
    //Storing mum
	private Fenetre mere;

	/*The constructor of the class*/
    public BarPanel(final Fenetre mere){
    	//Storing the frame to move
    	this.mere = mere;
    	//Adding the mouse listeners
	    addMouseListener(new Adapter());
	    addMouseMotionListener(new MotionAdapter());
    }
    
    /*The listener looks for an action from the mouse*/
    private class Adapter extends MouseAdapter{
    	/*
    	 * Called when : the user presses the panel
    	 * By : the adapter
    	 * Action : Stores the coords of the mouse
    	 */
    	public void mousePressed(MouseEvent e) {
        	//Getting the initial position of the mouse
        	x = e.getX();
            y = e.getY();
        }
    }
    /*This listener looks for a dragging movement of the mouse*/
    private class MotionAdapter extends MouseMotionAdapter{
    	/*
    	 * Called when : the user, after pressing the panel, moves the mouse
    	 * By : the Motion Adapter
    	 * Action : Moves the main frame with the mouse
    	 */
    	public void mouseDragged(MouseEvent e) {

            //Analyse the actual location of the main frame
            int X = mere.getLocation().x;
            int Y = mere.getLocation().y;

            // Compute the position variation from the initial point
            int deltaX = (X + e.getX()) - (X + x);
            int deltaY = (Y + e.getY()) - (Y + y);

            // And move the window to the new location
            X = X + deltaX;
            Y = Y + deltaY;
            mere.setLocation(X, Y);
        }
    }
}

