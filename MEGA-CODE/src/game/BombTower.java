package game;

import java.awt.Color;

public class BombTower extends Tower {
	static String[] texturePaths = {"data/OtherBase.png", "data/OtherArms.png", "data/OtherBody.png"};
	
	
	public BombTower(Map map, float x, float y) {
		super(map, x, y, map.tileHeight, map.tileHeight, 100.0, 100.0, 60.0, 2.0, Color.CYAN, texturePaths);
		red = 255;
		blue = 0;
		green = 255;
	}
	
	public String getClassName() {
		return "GatlingTower";
	}
	
}
