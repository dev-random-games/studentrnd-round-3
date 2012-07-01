package game;

import java.awt.Color;

import mvc.RectSprite;

public class Tile extends RectSprite{
	
	public boolean highGround = false;
	boolean onPath = false;
	
	public static final int lowGroundHeight = 0;
	public static final int highGroundHeight = 0;
	
	Tower tower;
	
	public Tile(int x, int y, float tileWidth, float tileHeight, boolean highGround){
		super(x * tileWidth, y * tileHeight, tileWidth, tileHeight, highGround ? highGroundHeight : lowGroundHeight , highGround ? Color.LIGHT_GRAY : Color.DARK_GRAY);
		
		this.highGround = highGround;
	}
	
	public void draw(){
		super.draw();
		
		if (onPath){
			setColor(Color.GREEN);
		} else {
			setColor(highGround ? Color.LIGHT_GRAY : Color.DARK_GRAY);
		}
		
		if (tower != null){
			tower.draw();
		}
	}
	
}
