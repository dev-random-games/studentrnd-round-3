package game;

import java.awt.Color;

public class LaserTower extends Tower {
	
	static String[] texturePaths = {"data/laser0.png", "data/laser1.png", "data/laser2.png"};
	
	public LaserTower(Map map, float x, float y) {
		super(map, x, y, 14f, 100f, 500.0, 10.0, 0.1, 2.0, Color.RED, texturePaths);
	}
	
	public String getClassName() {
		return "LaserTower";
	}
}
