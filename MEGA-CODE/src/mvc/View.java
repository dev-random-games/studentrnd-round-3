package mvc;

import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * 
 * View component of the MVC system. This handles the entire graphical
 * front-end. The main window should ALWAYS be resizable, so keep this in mind
 * whenever writing code for this class.
 * 
 * @author Dylan Swiggett
 * 
 */
public class View extends Thread {
	Model model;	//Has one way access to the Model
	JPanel panel;	//Core panel

	public static final int WIDTH = 700;
	public static final int HEIGHT = 700;

	public Vector3D viewTranslation;// Vector specifying the translation of the
	// view in 2D space.

	public static int frameCount = 0;

	TextureLoader textureLoader;

//	@SuppressWarnings("deprecation")
//	TrueTypeFont font;
//	
//	@SuppressWarnings("deprecation")
	public View(Model model){
		this.model = model;
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		viewTranslation = new Vector3D((Model.WIDTH * Model.TILEW) / 2, (Model.HEIGHT * Model.TILEH) / 2, HEIGHT);

		textureLoader = new TextureLoader(); 

	}

	/*
	 * Set up the camera position for a new frame
	 */
	public void setCamera(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		float whRatio = (float) WIDTH / (float) HEIGHT;
		GLU.gluPerspective(45, whRatio, 1, 1000);
		GLU.gluLookAt(viewTranslation.getX(), viewTranslation.getY(), viewTranslation.getZ(),
				viewTranslation.getX(), viewTranslation.getY(), 0, 0, 1, 0);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}

	/*
	 * Update the display, then push the changes to the screen
	 */
	@SuppressWarnings("deprecation")
	public void run(){
		try {
			/*
			 * Initialize display
			 */
			Display.create();

			GL11.glViewport(0, 0, WIDTH, HEIGHT);

			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
			GL11.glShadeModel(GL11.GL_SMOOTH); // Enables Smooth Shading
			/*
			 * Enable masking transparency.
			 */
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);	
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glAlphaFunc(GL11.GL_GREATER,0.1f);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
			GL11.glClearColor(0f, 0f, 0f, 1f);

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			
			//Fonts?
			//NOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOPE!
			// load font from a .ttf file
//			try {
//				InputStream inputStream = ResourceLoader.getResourceAsStream("data/SansitaOne.ttf");
//
//				Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
//				awtFont = awtFont.deriveFont(24f); // set font size
//				font = new TrueTypeFont(awtFont, false);
//
//			} catch (Exception e) {
//				System.err.println("font did not load correctly");
//				e.printStackTrace();
//				System.exit(0);
//			}  

			//			try {
			//				TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("/data/alot.png"));
			//			} catch (IOException e1) {
			//				// TODO Auto-generated catch block
			//				e1.printStackTrace();
			//			}

			while (!Display.isCloseRequested()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				setCamera(); // *DO NOT CHANGE THIS*

				/*
				 * All OpenGL Display code goes here!
				 */

//				Color.white.bind();
//				font.drawString(100, 50, "THE LIGHTWEIGHT JAVA GAMES LIBRARY", Color.yellow);
				

				for (Sprite sprite : model.sprites){
					sprite.draw();
				}

				/*
				 * End of stuff you can change without breaking everything.
				 */

				Display.update();
			}
		} catch (LWJGLException e1) {
			e1.printStackTrace();
		}
		Display.destroy();
		System.exit(0);
	}
}
