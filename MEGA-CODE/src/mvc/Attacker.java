package mvc;

public class Attacker extends Client {
	
	public static final float STARTENERGY = 500f;
	
	public Attacker(Model model, View view) {
		super(model, view, STARTENERGY);
	}
	
	public boolean canPlaceTower(int x, int y) {
		return !model.map.tiles[x][y].highGround;
	}

}
