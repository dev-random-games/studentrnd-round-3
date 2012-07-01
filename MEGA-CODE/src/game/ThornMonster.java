package game;

public class ThornMonster extends Monster{

	static String[] texturePaths = {"data/thorn0.png", "data/thorn1.png", "data/thorn2.png"};
	
	public static int baseEvolution = 0;
	
	public ThornMonster(float x, float y, float tileWidth, float tileHeight) {
		super(x, y, tileWidth, tileHeight, 500, 2, baseEvolution, 2f, texturePaths, 50);
	}
	
	/**
	 * Ignore this it is necessary.
	 */
//	public void draw(){
//		x += 15 * evolution;
//		y -= 10 * evolution;
//		super.draw();
//	}
}
