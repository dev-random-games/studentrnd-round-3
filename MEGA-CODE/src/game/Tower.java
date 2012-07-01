package game;

import java.awt.Color;

import mvc.ExtrudeSprite;

public class Tower extends ExtrudeSprite{
	
	double range, coolDown, damage;
	int upgradeRange, upgradeDamage, upgradeCooldown;
	int beamType;
	
	int uniqueId;
	
	public Tower(float x, float y, float width, float height) {
		super(x, y, width, height, 20, Color.BLUE);
		
		range = 150;
		coolDown = 1;
		damage = 1;
		
	}
}
