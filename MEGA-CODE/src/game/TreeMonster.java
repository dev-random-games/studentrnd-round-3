package game;

public class TreeMonster extends Monster {
	
	static String[] texturePaths = {"data/tree0.png", "data/tree1.png", "data/tree2.png"};
	
	public TreeMonster(float x, float y, float tileWidth, float tileHeight) {
		super(x, y, tileWidth, tileHeight, 200, 2, 0, 2, texturePaths);
	}
}
