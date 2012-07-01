package game;

import java.awt.Color;

import mvc.ExtrudeSprite;

public class Tower extends ExtrudeSprite{
	
	double range;
	double strength;
	double reload;
	
	int uniqueId;
	
	public Tower(float x, float y, float width, float height){
		super(x, y, width, height, 20, Color.BLUE);
		
		range = 150;
		strength = 20;
		reload = 1;
	}
}
