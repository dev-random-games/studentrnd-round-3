package game;

import java.awt.Color;

import mvc.ExtrudeSprite;

public class Tower extends ExtrudeSprite{
	
	public double range, coolDown, damage;
	public int upgradeRange, upgradeDamage, upgradeCooldown;
	public int beamType;
	
	public char towerType;
	
	public int uniqueId;
	
	public Tower(float x, float y, float width, float height) {
		super(x, y, width, height, 50, Color.BLUE);
		
		range = 150;
		coolDown = 1;
		damage = 1;
		
	}
}
