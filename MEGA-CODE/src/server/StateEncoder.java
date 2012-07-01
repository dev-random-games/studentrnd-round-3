package server;

import game.Tile;
import game.Tower;
import game.Monster;

public class StateEncoder {
	// All server states include: map, towers, monsters, upgrades
	
	public String getEncodedState() {
		String state = "";
		String delimiter = "#@#@#@#";
		
		/*
		 * Tile encoding:
		 * float x | float y | String terrainType | boolean highGround
		 */
		
		for (int x = 0; x < server.Init.map.width; x++) {
			for (int y = 0; y < server.Init.map.height; y++) {
				Tile t = server.Init.map.tiles[x][y];
				state += "tile" + delimiter + x + delimiter + y + delimiter + 
						t.terrainType + delimiter +
						t.highGroundHeight + "\n";
				if (t.tower != null) {
					state += "tower" + delimiter + x + delimiter + y + delimiter +
							t.tower.damage + delimiter + t.tower.cooldown + delimiter + 
							t.tower.r + delimiter + t.tower.evolution + delimiter +
							t.tower.evolutionScalar + delimiter +
							t.tower.range + delimiter + t.tower.beamType + delimiter + 
							t.tower.towerType + delimiter + t.tower.uniqueId + delimiter +
							t.tower.getClassName() + "\n";
				}
				
			}
		}
		
		
		/* 
		 * Monster encoding:
		 * float x | float y | float speed | double maxHealth | double health | int speedUpgrade |
		 * 		int healthUpgrade | double rotation | int uniqueId;
		 */
		for (Monster m : Init.map.monsters) {
			state += "monster" + delimiter + m.x + delimiter + m.y + delimiter + m.speed + delimiter +
					m.maxHealth + delimiter + m.health + delimiter + m.evolution + delimiter + 
					m.evolutionScalar + delimiter + m.r + delimiter + m.uniqueId + "\n";
		}
		
		/*
		 * Tower encoding:
		 * int x | int y | double damage | int cooldown | float rotation | int evolution |
		 * 	int evolutionScalar | int range | int beamType | char towerType | int uniqueId
		 */
		
		return state;
	}
}
