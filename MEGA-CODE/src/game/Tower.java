package game;

import java.awt.Color;

import server.Init;

import mvc.ExtrudeSprite;

public class Tower extends ExtrudeSprite{
	
	public float range, strength, reload;
	
	int uniqueId;
	
	public Tower(float x, float y, float width, float height) {
		super(x, y, width, height, 20, Color.BLUE);
		
		uniqueId = Init.getUniqueId();
		
		range = 150;
		strength = 20;
		reload = 1;
	}
}
