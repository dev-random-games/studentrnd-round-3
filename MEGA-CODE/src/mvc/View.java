package mvc;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * View component of the MVC system. This handles the entire graphical
 * front-end. The main window should ALWAYS be resizable, so keep this in mind
 * whenever writing code for this class.
 * 
 * @author Dylan Swiggett
 * 
 */
public class View extends JFrame {
	Model model;	//Has one way access to the Model
	JPanel panel;	//Core panel

	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;

	public static int frameCount = 0;
	
	public View(Model model){
		this.model = model;
		
		/*
		 * Set up the panel which should contain all core interface elements
		 */
		{
			panel = new JPanel();
			panel.setSize(WIDTH, HEIGHT);
			panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			panel.setVisible(true);
		}
		
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}
	
	/*
	 * Update the display, then push the changes to the screen
	 */
	public void repaintCycle(){
		while(true){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			repaint();
			
			frameCount++;
		}
	}
}
