package game;

import java.awt.Color;

import mvc.ExtrudeSprite;

public class Tile extends ExtrudeSprite{
	
	public boolean highGround = false;
	boolean onPath = false;
	
	public boolean mouseOver = false;
	
	public static final int lowGroundHeight = 0;
	public static final int highGroundHeight = 20;
	
	public String terrainType = "default";
	
	public Tower tower;
	
	public Tile(int x, int y, float tileWidth, float tileHeight, boolean highGround){
		super(x * tileWidth, y * tileHeight, tileWidth, tileHeight, highGround ? highGroundHeight : lowGroundHeight , highGround ? Color.LIGHT_GRAY : Color.DARK_GRAY);
		
		this.highGround = highGround;
	}
	
	public void draw(){
		if (mouseOver){
			setColor(Color.WHITE);
			mouseOver = false;
		}
		super.draw();
		
		if (onPath){
			setColor(Color.GREEN);
		} else {
			setColor(highGround ? Color.LIGHT_GRAY : Color.DARK_GRAY);
		}
		
		if (tower != null){
			//System.out.println(tower.x + ", " + tower.y);
			//System.out.println("Range: " + tower.range);
			tower.draw();
		}
	}
	
}
