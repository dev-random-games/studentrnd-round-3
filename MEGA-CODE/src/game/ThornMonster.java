package game;

public class ThornMonster extends Monster{

	static String[] texturePaths = {"data/thorn0.png", "data/thorn1.png", "data/thorn2.png"};
	
	public static int baseEvolution = 0;
	
	public ThornMonster(float x, float y, float tileWidth, float tileHeight) {
		super(x, y, tileWidth, tileHeight, 50, 2, 0, 2, texturePaths);
	}
}
