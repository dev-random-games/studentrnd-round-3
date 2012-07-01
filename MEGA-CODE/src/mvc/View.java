package mvc;

import game.EntMonster;
import game.ThornMonster;
import game.TreeMonster;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
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
//import org.newdawn.slick.Color;
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
	public static final int Z = 1000;
	public static final float FOV = 45;
	
	public static final int MAXZ = 2000;
	public static final int MINZ = 200;

	public Vector3D viewTranslation;// Vector specifying the translation of the
	// view in 2D space.
//	public Vector3D newViewTranslation;
	
	Vector3D viewVelocity;

	public static int frameCount = 0;

	TextureLoader textureLoader;
	
	TextureSprite hud;
	TextureSprite plantOverlay;
	TextureSprite energyBar;
	TextureSprite energyBarBackdrop;
	TextureSprite plantMenu;
	RectSprite energy;

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
//		viewTranslation = new Vector3D((Model.WIDTH * Model.TILEW) / 2, (Model.HEIGHT * Model.TILEH) / 2, HEIGHT);
		viewTranslation = new Vector3D(0, 0, Z);

		textureLoader = new TextureLoader(); 
		viewVelocity = new Vector3D();
	
	}

	/*
	 * Set up the camera position for a new frame
	 */
	public void setCamera(){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		float whRatio = (float) WIDTH / (float) HEIGHT;
		GLU.gluPerspective(FOV, whRatio, 1, 10000);
		GLU.gluLookAt(viewTranslation.getX(), viewTranslation.getY(), viewTranslation.getZ(),
				viewTranslation.getX(), viewTranslation.getY(), 0, 0, 1, 0);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	public Point pickPointOnScreen(Point screenPixel, float pickHeight){
		Vector3D cameraForwards = new Vector3D(0, 0, -1);
		Vector3D cameraRight = new Vector3D(1, 0, 0);
		Vector3D cameraUp = new Vector3D(0, 1, 0);
		
		float screenX = screenPixel.x - WIDTH / 2;
		float screenY = screenPixel.y - HEIGHT / 2;
		
		Vector3D screenVector = cameraForwards.scale((float) (WIDTH / (2 * Math.tan(FOV / 360 * Math.PI)))).add(cameraRight.scale(screenX))
											  		  	 .add(cameraUp.scale(screenY));
		
		float distScale = (viewTranslation.getZ()) / screenVector.getZ();
		Vector3D planeIntersection = viewTranslation.add(screenVector.scale(-distScale));
		
		return new Point((int) (planeIntersection.getX()), (int) planeIntersection.getY());

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
			
			plantOverlay = new TextureSprite(0, 0, 100, 100, 0, "src/data/plantOverlay.png");
			hud = new TextureSprite(0, 0, 100, 100, 0, "src/data/HUD.png");
			energyBar = new TextureSprite(0, 0, 100, 100, 0, "src/data/energyBar.png");
			energyBarBackdrop = new TextureSprite(0, 0, 100, 100, 0, "src/data/energyBarBackdrop.png");
			plantMenu = new TextureSprite(0, 0, 100, 100, 0, "src/data/plantMenu.png");
			EntMonster demoEnt = new EntMonster(0, 0, 50, 50);
			TreeMonster demoTree = new TreeMonster(0, 0, 50, 50);
			ThornMonster demoThorn = new ThornMonster(0, 0, 50, 50);
			energy = new RectSprite(0, 0, 10, 10, 0, Color.BLUE);
			
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
				
//				if (newViewTranslation != null){
//					viewTranslation = newViewTranslation;
//					newViewTranslation = null;
//				}
				
				viewTranslation = viewTranslation.add(viewVelocity);
				viewVelocity = viewVelocity.scale(.95f);
				
				if (viewTranslation.getZ() > MAXZ){
					viewTranslation.setZ(MAXZ);
					viewVelocity.setZ(0);
				} else if (viewTranslation.getZ() < MINZ){
					viewTranslation.setZ(MINZ);
					viewVelocity.setZ(0);
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
				
				
				GL11.glDepthMask(false);  // disable writes to Z-Buffer
				GL11.glDisable(GL11.GL_DEPTH_TEST);  // disable depth-testing
				
				Point corner1 = pickPointOnScreen(new Point(0, 0), 0);
				Point corner2 = pickPointOnScreen(new Point(700, 700), 0);
				
				if (model.plantMode){
					plantMenu.x = corner1.x;
					plantMenu.y = corner1.y;
					plantMenu.w = corner2.x - corner1.x;
					plantMenu.h = corner2.y - corner1.y;
					
					plantMenu.draw();
					
					Point demoEntCorner1 = pickPointOnScreen(new Point(0, 0), 0);
					Point demoEntCorner2 = pickPointOnScreen(new Point(700, 700), 0);
					
					demoEnt.x = demoEntCorner1.x;
					demoEnt.y = demoEntCorner1.y;
					demoEnt.w = demoEntCorner2.x - demoEntCorner1.x;
					demoEnt.h = demoEntCorner2.y - demoEntCorner1.y;
					
					demoEnt.evolution = EntMonster.baseEvolution;
					
					demoEnt.draw();
					
					Point demoTreeCorner1 = pickPointOnScreen(new Point(0, 0), 0);
					Point demoTreeCorner2 = pickPointOnScreen(new Point(700, 700), 0);
					
					demoTree.x = demoTreeCorner1.x;
					demoTree.y = demoTreeCorner1.y;
					demoTree.w = demoTreeCorner2.x - demoTreeCorner1.x;
					demoTree.h = demoTreeCorner2.y - demoTreeCorner1.y;
					
					demoTree.evolution = TreeMonster.baseEvolution;
					
					demoTree.draw();
					
					Point demoThornCorner1 = pickPointOnScreen(new Point(0, 0), 0);
					Point demoThornCorner2 = pickPointOnScreen(new Point(700, 700), 0);
					
					demoThorn.x = demoThornCorner1.x;
					demoThorn.y = demoThornCorner1.y;
					demoThorn.w = demoThornCorner2.x - demoThornCorner1.x;
					demoThorn.h = demoThornCorner2.y - demoThornCorner1.y;
					
					demoThorn.evolution = ThornMonster.baseEvolution;
					
					demoThorn.draw();
				}
				
				hud.x = corner1.x;
				hud.y = corner1.y;
				hud.w = corner2.x - corner1.x;
				hud.h = corner2.y - corner1.y;
				
				hud.draw();
				
				energyBar.x = corner1.x;
				energyBar.y = corner1.y;
				energyBar.w = corner2.x - corner1.x;
				energyBar.h = corner2.y - corner1.y;
				
				energyBar.draw();
				
				energyBarBackdrop.x = corner1.x;
				energyBarBackdrop.y = corner1.y;
				energyBarBackdrop.w = corner2.x - corner1.x;
				energyBarBackdrop.h = corner2.y - corner1.y;
				
				energyBarBackdrop.draw();
				
				if (model.plantMode){
					plantOverlay.x = corner1.x;
					plantOverlay.y = corner1.y;
					plantOverlay.w = corner2.x - corner1.x;
					plantOverlay.h = corner2.y - corner1.y;
					
					plantOverlay.draw();
				}
				
				GL11.glDepthMask(true);  // disable writes to Z-Buffer
				GL11.glEnable(GL11.GL_DEPTH_TEST);  // disable depth-testing
				
//				GL11.glDisable(GL11.GL_DEPTH_TEST);

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
