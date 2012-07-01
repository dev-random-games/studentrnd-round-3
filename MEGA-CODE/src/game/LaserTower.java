package game;

import java.awt.Color;

import mvc.TextureSprite;

public class LaserTower extends Tower {
	
	
	static String[] texturePaths = {"data/BombBase.png", "data/BombArms.png", "data/BombBody.png"};
	
	
	public LaserTower(Map map, float x, float y) {
		super(map, x, y, map.tileHeight, map.tileHeight, 500.0, 10.0, 5.0, 2.0, Color.RED, texturePaths);
		red = 255;
		green = 0;
		blue = 0;
	}
	
	public String getClassName() {
		return "LaserTower";
	}
}
