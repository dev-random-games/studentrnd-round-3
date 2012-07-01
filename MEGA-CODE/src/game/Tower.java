package game;

import java.awt.Color;

import mvc.ExtrudeSprite;
import game.Map;

public class Tower extends ExtrudeSprite{
	
	public double range;
	public double damage;
	public double cooldown;
	
	public int evolution;
	public double evolutionScalar;
	
	String[] texturePaths;

	public int beamType;
	public char towerType;
	public int uniqueId;
	
	public Tower(Map map, float x, float y) {
		super(x * map.tileWidth + 5, y * map.tileHeight + 5, map.tileWidth - 6, map.tileHeight - 6, 20, Color.BLUE);
		
		range = 150;
		damage = 20;
		cooldown = 30;
		
		evolution = 0;
		evolutionScalar = 1;
		
	}
	
	public Tower(Map map, float x, float y, float width, float height, double range, double damage, double cooldown, double evolutionScalar, Color color, String[] texturePaths) {
		super(x * map.tileWidth + 5, y * map.tileHeight + 5, width, width, height, color);
		
		this.range = range;
		this.damage = damage;
		this.cooldown = cooldown;
		
		this.evolutionScalar = evolutionScalar;
		
		this.texturePaths = texturePaths;
		
	}
	
	public String getClassName() {
		return "Tower";
	}
}
