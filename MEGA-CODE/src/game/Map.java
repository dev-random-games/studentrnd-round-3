package game;

import java.awt.Color;
import java.awt.Rectangle;

import mvc.RectSprite;
import mvc.Sprite;

/*
 * Representation of game map (landscape and towers) kept by both the server and both clients.
 */
public class Map extends Sprite{
	
	int width, height;		//x and y position, number of tiles width-wise and height-wise
	float tileWidth, tileHeight;	//W/H for individual tiles on the map
	float mapWidth, mapHeight;		//Total width and height of the map
	
	Tile[][] tiles;
	
	public Map(int width, int height, float tileWidth, float tileHeight){
		this.width = width;
		this.height = height;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
		mapWidth = width * tileWidth;
		mapHeight = height * tileHeight;
		
		tiles = new Tile[width][height];
		
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				tiles[x][y] = new Tile(x, y, tileWidth, tileHeight, x % 2 == 0 ? true : false);
			}
		}
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, (int) mapWidth, (int) mapHeight);
	}

	@Override
	public void draw() {
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				tiles[x][y].draw();
			}
		}
	}

}
