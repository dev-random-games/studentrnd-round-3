package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import server.Init;

import mvc.RectSprite;
import mvc.Sprite;
import mvc.TextureSprite;
import mvc.Vector3D;

public class Monster extends RectSprite {
	
//	float x, y, z;
	float speed = 2;
	public double health;
	int uniqueId;
	public double maxHealth, rotation;
	public int speedUpgrade, healthUpgrade;
	
	public Monster(float x, float y, float tileWidth, float tileHeight, double health) {
		super(x * tileWidth - 5 + tileWidth / 2, y * tileHeight - 5 + tileHeight / 2, 10, 10, 5, Color.RED);
		this.health = health;
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
	
	public boolean moveAlong(ArrayList<Point> path, float tileWidth, float tileHeight){
		for (int i = 1; i < path.size(); i++){
			if (path.get(i).equals(getMapPosition(tileWidth, tileHeight))){
				moveTowards(path.get(i - 1), tileWidth, tileHeight);
				return true;
			}
		}
		return false;
	}
	
	public void damage(double amount) {
		this.health -= amount;
	}
	
	public boolean shouldDie() {
		return this.health <= 0;
	}
}
