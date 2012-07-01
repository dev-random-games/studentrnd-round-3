package mvc;

import game.Monster;
import game.Tile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import server.MessageType;

public class Client extends Thread{
	private Socket client;
	private ObjectOutputStream out;
	public ObjectInputStream in;
	
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
				
				switch (type){
				case SERVER_MESSAGE:
					System.out.println("SERVER: " + message);
					break;
				case CONNECT:
					System.out.println("Connection with server confirmed. Connected as user " + message + ".");
					userId = Integer.parseInt(message);
					sendMessage(MessageType.SERVER_MESSAGE, "Hello server!");
					break;
				case ADD_TOWER:
					x = message.charAt(0);
					y = message.charAt(1);
					int towerType = message.charAt(2);
					if (this.canPlaceTower(x, y)) {
						this.subtractEnergy();
						System.out.println("Adding tower at " + x + ", " + y);
						model.map.addTower(x, y, towerType);	
					} else {
						System.out.println("Cannot place tower at " + x + ", " + y);
					}
					break;
				case ADD_MONSTER:
					x = message.charAt(0);
					y = message.charAt(1);
					int monsterType = message.charAt(2);
					int monsterId = Integer.parseInt(message.substring(3));
					if (this.canPlaceTower(x, y)) {
						this.subtractEnergy();
						System.out.println("Adding monster at " + x + ", " + y);
						model.map.addMonster(x, y, monsterType, monsterId);	
					} else {
						System.out.println("Cannot place monster at " + x + ", " + y);
					}
					break;
				case PROVIDE_STATE:
					model.map.monsters = new ArrayList<Monster>();
					
					String delimiter = "#@#@#@#";
					// Load state from this
					String[] split = message.split("\n");
					for (String l : split) {
						String[] splitl = l.split(delimiter);
						if (splitl[0] == "tile") {
							float tileX = Float.parseFloat(splitl[1]);
							float tileY = Float.parseFloat(splitl[2]);
							
							
						}
					}
					
				}
				
			} catch (IOException e){
				if (!client.isConnected()) {	// If server has disconnected, attempt to reconnect.
					try {
						client = new Socket(host, port);
						client.setSoTimeout(1000);
						
						out = new ObjectOutputStream(client.getOutputStream());
						in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
						
					} catch (Exception e1) {
						e1.printStackTrace();
						System.exit(0);
					}
				}
				try {
					Thread.sleep(5);	// Wait for a little bit if no message has been received.
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}	
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
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
		sendMessage(MessageType.ADD_MONSTER, "" + (char) x + (char) y + (char) monsterType);
	}
}
