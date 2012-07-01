package mvc;

public class Defender extends Client {

	public static final float STARTENERGY = 500f;
	
	public Defender(Model model, View view) {
		super(model, view, STARTENERGY);
	}

	public boolean canPlaceTower(int x, int y) {
		return model.map.tiles[x][y].highGround;
	}
	
}
