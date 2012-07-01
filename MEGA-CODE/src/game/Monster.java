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
	public float evolutionScalar;
	
	String[] texturePaths;
	
	TextureSprite stage0, stage1, stage2;
	
	public Monster(float x, float y, float tileWidth, float tileHeight, double health) {
		super(x * tileWidth - 5 + tileWidth / 2, y * tileHeight - 5 + tileHeight / 2, 10, 10, 5, Color.RED);
		this.maxHealth = health;
		this.health = health;
		this.health = 100;
		this.evolutionScalar = 1;
	}
	
	public Monster(float x, float y, float tileWidth, float tileHeight, double health, float speed, int evolution, float evolutionScalar, String[] texturePaths) {
		super(x * tileWidth + tileWidth / 4, y * tileHeight + tileHeight / 4, tileWidth / 2, tileHeight / 2, 5, Color.RED);
		this.speed = speed;
		this.maxHealth = health;
		this.health = health;
		this.evolution = evolution;
		this.evolutionScalar = evolutionScalar;
		this.texturePaths = texturePaths;
		
//		for (int i = 0; i < evolution; i++){
//			if (!evolve()) break;
//		}
		
		this.stage0 = new TextureSprite(this.x, this.y, w, h, 20, this.texturePaths[0]);
		this.stage1 = new TextureSprite(this.x, this.y, w * evolutionScalar, h * evolutionScalar, 20, this.texturePaths[1]);
		this.stage2 = new TextureSprite(this.x, this.y, w * evolutionScalar * evolutionScalar, h * evolutionScalar * evolutionScalar, 20, this.texturePaths[2]);
		
		System.out.println("sex: " + this.x);
	}
	
	public boolean evolve(){
		if (evolution < 2){
			evolution ++;
			speed *= evolutionScalar;
			maxHealth *= evolutionScalar;
			health *= evolutionScalar;
			return true;
		} else {
			return false;
		}
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
			stage0.w = this.w;
			stage0.h = this.h;
			stage0.depth = this.depth;
			stage0.draw();
			break;
		case 1:
			stage1.x = this.x - (w * evolutionScalar - w) / 2;
			stage1.y = this.y - (h * evolutionScalar - h) / 2;
			stage1.w = this.w * evolutionScalar;
			stage1.h = this.h * evolutionScalar;
			stage1.depth = this.depth;
			stage1.draw();
			break;
		case 2:
			stage2.x = this.x - (w * evolutionScalar * evolutionScalar - w) / 2;
			stage2.y = this.y - (h * evolutionScalar * evolutionScalar - h) / 2;
			stage2.w = this.w * evolutionScalar * evolutionScalar;
			stage2.h = this.h * evolutionScalar * evolutionScalar;
			stage2.depth = this.depth;
			stage2.draw();
			break;
		}
	}
}
