package game;

import java.awt.Color;

import mvc.RectSprite;

public class Tile extends RectSprite{
	
	boolean highGround = false;
	
	public static final int lowGroundHeight = 0;
	public static final int highGroundHeight = 20;
	
	Tower tower;
	
	public Tile(int x, int y, float tileWidth, float tileHeight, boolean highGround){
		super(x * tileWidth, y * tileHeight, tileWidth, tileHeight, highGround ? highGroundHeight : lowGroundHeight , highGround ? Color.LIGHT_GRAY : Color.DARK_GRAY);
	}
	
	public void draw(){
		super.draw();
		
		if (tower != null){
			
		}
	}
	
}
