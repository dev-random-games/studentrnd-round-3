package server;

import game.Tile;
import game.Tower;

public class StateEncoder {
	// All server states include: map, towers, monsters, upgrades
	
	public String getEncodedState() {
		String state = "";
		String delimiter = "///|||///";
		
		state += "tiles\n";
		
		/*
		 * Tile storage format:
		 * x | y | filename | highground
		 */
		
		for (int x = 0; x < server.Init.map.width; x++) {
			for (int y = 0; y < server.Init.map.height; y++) {
				state += "" + x + delimiter + y + delimiter + 
						server.Init.map.tiles[x][y].filename + delimiter +
						server.Init.map.tiles[x][y].highGround + "\n";
			}
		}
		
		state += "towers\n";
		
		/*
		 * Tower storage format:
		 * x | y | range | strength | reload | uniqueid
		 */
		
		for (Tower t : server.Init.map.towers) {
			state += t.x + delimiter + t.y + delimiter + t.strength + delimiter;
		}
		return "";
	}
}
