package mvc;

import game.Tower;

import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import server.MessageType;

/**
 * 
 * Controller component of the MVC system. This handles all user input, and
 * transmits it to the engine. The primary functionality should be setting up
 * all event listeners.
 * 
 * @author Dylan Swiggett
 * 
 */
public class Controller extends Thread {
	Model model;	//Has one way access to the Model
	View view;		//Has one way access to the View
	
	KeyboardFocusManager keyManager;
	
	boolean mouseDown;
	
	boolean[] keysPressed;
	
	public Controller(Model model, View view){
		this.model = model;
		this.view = view;
		/*
		 * A Key Dispatcher doesn't rely on the focus.
		 * All key events will be captured.
		 */
		keyManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		
		keysPressed = new boolean[255];
		
		mouseDown = false;
	}
	
	public void run(){
		while (true){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			/*
			 * Catch all keyboard events
			 */
			while (Keyboard.next()){
				if (Keyboard.getEventKeyState()){
					keyPressed(Keyboard.getEventKey());
				} else {
					keyReleased(Keyboard.getEventKey());
				}
			}
			
			Point mousePos = view.pickPointOnScreen(new Point(Mouse.getX(), Mouse.getY()), 0);

			for (Tower tower : model.map.towers){
				tower.mouseHovering = false;
			}
			
			int mapX = (int) (mousePos.x / model.map.tileWidth);
			int mapY = (int) (mousePos.y / model.map.tileHeight);
			
			if (mapX >= 0 && mapY >= 0 && mapX < model.map.width && mapY < model.map.height){
				if (model.map.tiles[mapX][mapY].tower != null && !model.plantMode){
					model.map.tiles[mapX][mapY].tower.mouseHovering = true;
				}
			}
			
			view.pickPointOnScreen(mousePos, 0);
			
			/*
			 * Catch mouse events
			 */
			if (Mouse.isButtonDown(0)){
				if (!mouseDown){
					mouseDown = true;
					mousePressed((int) mousePos.getX(), (int) mousePos.getY());
				}
			} else if (mouseDown){
				mouseDown = false;
				mouseReleased((int) mousePos.getX(), (int) mousePos.getY());
			}
			
			try {
				/* DON'T REMOVE THIS LINE WITHOUT SOMEHOW COPYING VIEWTRANSLATION TO NEWVIEWTRANSLATION */
				view.viewVelocity.setZ(view.viewVelocity.getZ() - Mouse.getDWheel() * (view.viewTranslation.getZ() / 16000));
				
				if (mousePos.x > 0 && mousePos.y > 0 && mousePos.x < model.map.width * model.map.tileWidth && mousePos.y < model.map.height * model.map.tileHeight){
					model.map.tiles[mousePos.x / model.TILEW][mousePos.y / model.TILEH].mouseOver = true;
				}
				
				if (keysPressed[Keyboard.KEY_LEFT]){
					view.viewVelocity.setX(-4 * (view.viewTranslation.getZ() / 1000));
				}
				if (keysPressed[Keyboard.KEY_UP]){
					view.viewVelocity.setY(4 * (view.viewTranslation.getZ() / 1000));
				}
				if (keysPressed[Keyboard.KEY_RIGHT]){
					view.viewVelocity.setX(4 * (view.viewTranslation.getZ() / 1000));
				}
				if (keysPressed[Keyboard.KEY_DOWN]){
					view.viewVelocity.setY(-4 * (view.viewTranslation.getZ() / 1000));
				}
			} catch (NullPointerException e){
				continue;
			}
		}
	}
	
	/**
	 * Called by the Mouse Listener when the mouse is pressed.
	 * 
	 * @param evt
	 */
	public void mousePressed(int x, int y){
		System.out.println(x + ", " + y);
	}
	
	/**
	 * Called by the Mouse Listener when the mouse is released.
	 * 
	 * @param evt
	 */
	public void mouseReleased(int x, int y){
		int mapX = (int) (x / model.map.tileWidth);
		int mapY = (int) (y / model.map.tileHeight);
		
		if (mapX >= 0 && mapY >= 0 && mapX < model.map.width && mapY < model.map.height){
			if (model.plantMode){
				model.client.addMonster(mapX, mapY, 0);
			} else {
				if (model.map.tiles[mapX][mapY].tower == null){
					model.client.addTower(mapX, mapY, 1);
				} else {
					model.map.tiles[mapX][mapY].tower.upgrade();
				}
			}
		}
	}
	
	/**
	 * Called by the Mouse Listener when the mouse is clicked.
	 * 
	 * @param evt
	 */
	public void mouseClicked(MouseEvent evt){
		
	}
	
	/**
	 * Called by the Mouse Listener when the mouse is dragged.
	 * 
	 * @param evt
	 */
	public void mouseDragged(MouseEvent evt){
		
	}
	
	/**
	 * Called by the Key Listener when a key is pressed.
	 * 
	 * @param evt
	 */
	public void keyPressed(int key){
		keysPressed[key] = true;
	}
	
	/**
	 * Called by the Key Listener when a key is released.
	 * 
	 * @param evt
	 */
	public void keyReleased(int key){
		keysPressed[key] = false;
	}
}
