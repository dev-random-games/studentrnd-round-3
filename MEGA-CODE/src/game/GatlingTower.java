package game;

import java.awt.Color;
import mvc.TextureSprite;

public class GatlingTower extends Tower{
	
	static String[] texturePaths = {"data/RabbitBase128.png", "data/RabbitArms128.png", "data/RabbitBody128.png"};
	
	public GatlingTower(Map map, float x, float y) {
		
		super(map, x, y, map.tileHeight, map.tileHeight, 100.0, 20.0, 20.0, 2.0, Color.CYAN, texturePaths);
		red = 0;
		green = 0;
		blue = 255;
	}
	
	public String getClassName() {
		return "GatlingTower";
	}
	
	
}
