package game;

public class EntMonster extends Monster {

	static String[] texturePaths = {"data/ent0.png, data/ent1.png, data/ent2.png"};
	
	public EntMonster(float x, float y, float tileWidth, float tileHeight) {
		super(x, y, tileWidth, tileHeight, 100, 3, 0, 2, texturePaths);
	}
}
