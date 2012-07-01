package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import mvc.RectSprite;
import mvc.Sprite;
import mvc.TextureSprite;
import mvc.Vector3D;

public class Monster extends RectSprite {
	
//	float x, y, z;
	float speed = 2;
	int uniqueId;
	
	public Monster(float x, float y, float tileWidth, float tileHeight) {
		super(x - 5 + tileWidth / 2, y - 5 + tileHeight / 2, 10, 10, 5, Color.RED);
	}
	
	public Point getMapPosition(float tileWidth, float tileHeight){
		return new Point ((int) ((x - w / 2) / tileWidth), (int) ((y - h / 2) / tileHeight));
	}
	
	public void moveTowards(Point mapPosition, float tileWidth, float tileHeight){
		float destX = (mapPosition.x + .5f) * tileWidth - w / 2;
		float destY = (mapPosition.y + .5f) * tileHeight - h / 2;
		
		Vector3D velocity = new Vector3D(destX - x, destY - y, 0);
		velocity = velocity.normalize().scale(speed);
		
		x += velocity.getX();
		y += velocity.getY();
	}
}
