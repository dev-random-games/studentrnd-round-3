package server;

import game.Tile;

public class StateEncoder {
	// All server states include: map, towers, monsters, upgrades
	
	public String getEncodedState() {
		String state = "";
		String delimiter = "///|||///";
		
		for (int x = 0; x < server.Init.map.width; x++){
			for (int y = 0; y < server.Init.map.height; y++){
				state += "" + server.Init.map.tiles[x][y].
			}
		}
	}
}
