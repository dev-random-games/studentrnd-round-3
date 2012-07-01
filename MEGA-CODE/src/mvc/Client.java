package mvc;

import game.BombTower;
import game.EntMonster;
import game.GatlingTower;
import game.LaserTower;
import game.Monster;
import game.ThornMonster;
import game.Tile;
import game.Tower;
import game.TreeMonster;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import server.Init;
import server.MessageType;

public class Client extends Thread{
	private Socket client;
	private ObjectOutputStream out;
	public ObjectInputStream in;
	
//	String host = "localhost";
	String host = "192.168.1.2";
	int port = 12345;
	
	int userId;
	
	Model model;
	View view;
	
	float energy;
	
	public Client(Model model, View view){
		try {
			this.model = model;
			this.view = view;
			
			client = new Socket(host, port);
			client.setTcpNoDelay(true);
			client.setSoTimeout(1000);
			
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
			
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			start();
			
			System.out.println("Connecting to server...");
			
			sendMessage(MessageType.CONNECT, "user");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Failed to connect to client");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void run(){
		while (true){
			try {
				String val = (String) in.readObject();
				
				MessageType type = MessageType.translate(val.charAt(0));
				String message = val.substring(1);
				
				
				
				int x, y;
				switch (type) {
				case SERVER_MESSAGE:
					System.out.println("SERVER: " + message);
					break;
				case CONNECT:
					switch ((int) message.charAt(0)){	// Determine player type
					case 0:
						model.plantMode = true;
						break;
					case 1:
						model.plantMode = false;
						break;
					default:
						System.out.println("Uh oh! Connection refused by a full server.");
						System.exit(0);
					}
					System.out.println("Connection with server confirmed. Connected as user " + message + ".");
					userId = Integer.parseInt(message.substring(1));
					sendMessage(MessageType.SERVER_MESSAGE, "Hello server!");
					break;
				case ADD_TOWER:
					x = message.charAt(0);
					y = message.charAt(1);
					int towerType = message.charAt(2);
					if (this.canPlaceTower(x, y)) {
						System.out.println("Adding tower at " + x + ", " + y);
						if (!model.plantMode && this.model.energy >= model.map.tiles[x][y].tower.cost) {
							double cost = 0;
							switch (towerType) {
							case(0):
								cost = LaserTower.cost;
							case(1):
								cost = GatlingTower.cost;
							case(2):
								cost = BombTower.cost;
							} 
							
							if (this.model.energy >= cost) {
								this.model.energy -= cost;
								model.map.addTower(x, y, towerType);
							}
						}
					} else {
						System.out.println("Cannot place tower at " + x + ", " + y);
					}
					break;
				case ADD_MONSTER:
					System.out.println("monster - client incoming");
					x = message.charAt(0);
					y = message.charAt(1);
					int monsterType = message.charAt(2);
					int monsterId = Integer.parseInt(message.substring(3));
					if (this.canPlaceTower(x, y)) {
						this.subtractEnergy();
						System.out.println("Adding monster at " + x + ", " + y);
						if (model.plantMode) {
							double cost = 0;
							switch (monsterType) {
							case(0):
								cost = EntMonster.getCost(EntMonster.baseEvolution);
							case(1):
								cost = TreeMonster.getCost(TreeMonster.baseEvolution);
							case(2):
								cost = ThornMonster.getCost(ThornMonster.baseEvolution);
							} 
							
							if (this.model.energy >= cost) {
								this.model.energy -= cost;
								model.map.addMonster(x, y, monsterType, monsterId);	
							}
						}
					} else {
						System.out.println("Cannot place monster at " + x + ", " + y);
					}
					break;
				case MOVE_MONSTER:
					String[] parts = message.split("\\.");
					int checkId = Integer.parseInt(parts[0]);
					x = Integer.parseInt(parts[1]);
					y = Integer.parseInt(parts[2]);
					for (Monster monster : model.map.monsters){
						if (monster.uniqueId == checkId){
							monster.x = x;
							monster.y = y;
							break;
						}
					}
					
					break;
				case REMOVE_MONSTER:
					int monsterID = Integer.parseInt(message);
					System.out.println("deleting monster " + monsterID);
					for (int i = 0; i < model.map.monsters.size(); i++) {
						if (monsterID == model.map.monsters.get(i).uniqueId) {
							model.map.monsters.remove(i);
						}
					}
					break;
				case PROVIDE_EFFECT:
					System.out.println(message);
					String[] split = message.split("#");
					System.out.println(split.toString());
					float startX = Float.parseFloat(split[0]);
					float startY = Float.parseFloat(split[1]);
					float endX = Float.parseFloat(split[2]);
					float endY = Float.parseFloat(split[3]);
					int r = Integer.parseInt(split[4]);
					int g = Integer.parseInt(split[5]);
					int b = Integer.parseInt(split[6]);
					model.effects.add(new Effect(startX, startY, endX, endY, 10, r, g, b));
					break;
				case UPGRADE:
					int upType = (int) message.charAt(0);
					model.map.upgrade(upType);
					break;
				}
				model.map.move();
			} catch (IOException e) {
				if (!client.isConnected()) {	// If server has disconnected, attempt to reconnect.
					try {
						client = new Socket(host, port);
						client.setSoTimeout(1000);
						out = new ObjectOutputStream(client.getOutputStream());
						in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
					} catch (SocketException e1) {
						e1.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				}
			} catch (Exception e3) {
				e3.printStackTrace();
			}
		}
	}
	
	public void subtractEnergy() {
		float scalar  = 1f;
		
		this.energy *= scalar;
	}
	
	public boolean canPlaceTower(int x, int y) {
		return true;
	}
	
	public void sendMessage(MessageType type, String message) {
		try {
			out.writeObject(type.value() + message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addTower(int x, int y, int towerType){
		sendMessage(MessageType.ADD_TOWER, "" + (char) x + (char) y + (char) towerType);
	}
	
	public void addMonster(int x, int y, int monsterType){
		//System.out.println("monster - client outgoing");
		sendMessage(MessageType.ADD_MONSTER, "" + (char) x + (char) y + (char) monsterType);
	}
	
	public void upgrade(int type){
		sendMessage(MessageType.UPGRADE, "" + (char) type);
	}
}
