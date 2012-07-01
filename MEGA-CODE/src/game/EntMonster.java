package game;

public class EntMonster extends Monster {

	static String[] texturePaths = {"data/ent0.png", "data/ent1.png", "data/ent2.png"};
	
	public static int baseEvolution = 0;
	
	public EntMonster(float x, float y, float tileWidth, float tileHeight) {
		super(x, y, tileWidth, tileHeight, 100, .25f, 2, 1.5f, texturePaths);
	}
}
