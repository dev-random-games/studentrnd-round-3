package game;

import java.awt.Color;

import mvc.ExtrudeSprite;
import mvc.RectSprite;
import mvc.TextureSprite;

public class Tile extends ExtrudeSprite{
	
	public boolean highGround = false;
	boolean onPath = false;
	
	public boolean mouseOver = false;
	
	public static final int lowGroundHeight = 0;
	public static final int highGroundHeight = 20;
	
	public String terrainType = "default";
	
	public Tower tower;
	
	private static TextureSprite grass, metal;
	
	public Tile(int x, int y, float tileWidth, float tileHeight, boolean highGround){
		super(x * tileWidth, y * tileHeight, tileWidth, tileHeight, highGround ? highGroundHeight : lowGroundHeight , highGround ? Color.LIGHT_GRAY : Color.DARK_GRAY);
		
		this.highGround = highGround;
		
		if (grass == null || metal == null){
			grass = new TextureSprite(x * tileWidth, y * tileHeight, tileWidth, tileHeight, lowGroundHeight, "src/data/GrassTile.png");
			metal = new TextureSprite(x * tileWidth, y * tileHeight, tileWidth, tileHeight, highGroundHeight, "src/data/MetalTile.png");
		}
	}
	
	public void draw(){
		if (mouseOver){
			setColor(Color.WHITE);
			mouseOver = false;
			super.draw();
		} else {
			if (highGround) {
				super.draw();
				
//				metal.x = x;
//				metal.y = y;
//				
//				metal.draw();
				mouseOver = false;
			} else {
				
				grass.x = x;
				grass.y = y;
				
				grass.draw();
			}
			
			if (onPath){
				setColor(Color.GREEN);
			} else {
				setColor(highGround ? Color.LIGHT_GRAY : Color.DARK_GRAY);
			}
			
			super.draw();
		}
		
		if (tower != null){
			tower.draw();
		}
	}
	
}
