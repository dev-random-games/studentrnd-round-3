package mvc;

import game.Map;
import game.Tower;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.Port;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

/**
 * 
 * The Model component of the MVC system. All direct interfacing with the engine
 * should be here.
 * 
 * @author Dylan Swiggett
 * 
 */
public class Model extends Thread {
	
	public ArrayList<Sprite> sprites;
	
	/*
	 * Audio *out* port.
	 */
	static Port audioOut;
	
	Client client;
	
	Map map;
	
	boolean gameMode;

	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;

	public static final int TILEW = 50;
	public static final int TILEH = 50;
	
	public static final float STARTENERGY = 500f;
	
	/**
	 * True if plants, false if mecha.
	 */
	boolean plantMode = false;
	
	public Model() {
		sprites = new ArrayList<Sprite>();
		
//		sprites.add(new ExtrudeSprite(100, 100, 100, 100, 300, Color.RED));
		
		//map = new Map(WIDTH, HEIGHT, TILEW, TILEH);
		map = new Map("data/testcase.png", WIDTH, HEIGHT, TILEW, TILEH);
		
		sprites.add(map);
		
//		sprites.add(new TextureSprite(100, 100, 100, 100, 300, "/data/test.png"));
	}
	
	public void setClient(Client client){
		this.client = client;
	}

	/*
	 * Load the specified audio file to a clip, or return null if not found.
	 */
	public Clip getAudioClip(String path) {
		try {
			System.out.println("Loading audio " + path + "...");
			AudioInputStream stream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
			try {
				Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, null));
				clip.open(stream);
				return clip;
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	/*
	 * Checks for headphones, then speakers, establishing an audio output port
	 * to whichever is available. Complains and returns false if no audio out is
	 * available.
	 */
	public boolean connectAudio() {
		if (AudioSystem.isLineSupported(Port.Info.HEADPHONE)) {
			try {
				audioOut = (Port) AudioSystem.getLine(Port.Info.HEADPHONE);
				audioOut.open();
				System.out.println("Connected headphones.");
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No headphones found, checking speakers.");
			if (AudioSystem.isLineSupported(Port.Info.SPEAKER)) {
				try {
					audioOut = (Port) AudioSystem.getLine(Port.Info.SPEAKER);
					audioOut.open();
					System.out.println("Connected speakers.");
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Error -- no sound output available");
			}
		}
		return false;
	}

	/**
	 * This is the main loop of the program -- should call iterate() in APOPS.
	 */
	public void run() {
		while (true) {
			map.move();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
