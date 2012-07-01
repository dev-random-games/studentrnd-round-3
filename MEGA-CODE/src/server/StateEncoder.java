package server;

import game.Tile;
import game.Tower;

public class StateEncoder {
	// All server states include: map, towers, monsters, upgrades
	
	public String getEncodedState() {
		String state = "";
		String delimiter = "///|||///";
		
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
		 * Tower encoding:
		 * int x | int y | double damage | int cooldownPeriod | float rotation | int upgradeDamage | 
		 * 		int upgradeCooldown | int upgradeRange | int range | int beamType | char towerType | int uniqueId
		 */
		
		return "";
	}
}
