package game;

import java.awt.Color;

import mvc.ExtrudeSprite;
import game.Map;

public class Tower extends ExtrudeSprite{
	
	double range;
	double strength;
	double cooldown;
	
	int evolution;
	double evolutionScalar;
	
	String[] texturePaths;
	
	int uniqueId;
	
	public Tower(Map map, float x, float y) {
		super(x * map.tileWidth + 5, y * map.tileHeight + 5, map.tileWidth - 6, map.tileHeight - 6, 20, Color.BLUE);
		
		range = 150;
		strength = 20;
		cooldown = 1;
		
		evolution = 0;
		evolutionScalar = 1;
		
	}
	
	public Tower(Map map, float x, float y, float width, float height, double range, double strength, double cooldown, double evolutionScalar, Color color, String[] texturePaths) {
		super(x * map.tileWidth + 5, y * map.tileHeight + 5, width, width, height, color);
		
		this.range = range;
		this.strength = strength;
		this.cooldown = cooldown;
		
		this.evolutionScalar = evolutionScalar;
		
		this.texturePaths = texturePaths;
		
	}
}
