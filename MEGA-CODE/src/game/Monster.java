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
	public float speed = .5f;
	public double health;
	public int uniqueId;
	public double maxHealth;

	public int evolution;
	public double evolutionScalar;
	
	String[] texturePaths;
	
	TextureSprite stage0, stage1, stage2;
	
	public Monster(float x, float y, float tileWidth, float tileHeight, double health) {
		super(x * tileWidth - 5 + tileWidth / 2, y * tileHeight - 5 + tileHeight / 2, 10, 10, 5, Color.RED);
		this.maxHealth = health;
		this.health = health;
		this.health = 100;
		this.evolutionScalar = 1;
	}
	
	public Monster(float x, float y, float tileWidth, float tileHeight, double health, float speed, int evolution, double evolutionScalar, String[] texturePaths) {
		super(x * tileWidth - 5 + tileWidth / 2, y * tileHeight - 5 + tileHeight / 2, 10, 10, 5, Color.RED);
		this.speed = speed;
		this.maxHealth = health;
		this.health = health;
		this.evolution = evolution;
		this.evolutionScalar = evolutionScalar;
		this.texturePaths = texturePaths;
		
		this.stage0 = new TextureSprite(this.x, this.y, tileWidth, tileHeight, 20, this.texturePaths[0]);
		this.stage1 = new TextureSprite(this.x, this.y, tileWidth, tileHeight, 20, this.texturePaths[1]);
		this.stage2 = new TextureSprite(this.x, this.y, tileWidth, tileHeight, 20, this.texturePaths[2]);
		
		System.out.println("sex: " + this.x);
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
	
	public void draw() {
		switch(this.evolution) {
		case 0:
			stage0.x = this.x;
			stage0.y = this.y;
			stage0.draw();
			break;
		case 1:
			stage1.x = this.x;
			stage1.y = this.y;
			stage1.draw();
			break;
		case 2:
			stage2.x = this.x;
			stage2.y = this.y;
			stage2.draw();
			break;
		}
	}
}
