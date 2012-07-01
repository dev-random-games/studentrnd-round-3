package game;

import java.awt.Color;
import java.awt.Rectangle;

import mvc.RectSprite;
import mvc.Sprite;
import mvc.TextureSprite;

public class Monster extends TextureSprite {
	
	double x, y, z;
	double speed;
	int uniqueId;
	
	public Monster(float x, float y, float w, float h, float depth, String texturePath) {
		super(x, y, w, h, depth, texturePath);
	}	
}
