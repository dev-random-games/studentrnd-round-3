package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Runs a new thread that detects any new clients and creates a Connector for
 * them. Also handles all input messages, and responds to them. Stores the
 * entire game in a World. Essentially is the master class that runs all
 * server-client communication.
 * 
 * @author Dylan
 * 
 */
public class ClientManager extends Thread {
	ServerSocket server;

	ArrayList<Connector> connections;

	public ClientManager(ServerSocket server) {
		this.server = server;

		connections = new ArrayList<Connector>();
	}

	public void run() {
		while (true) {
			try {
				Socket newClient = server.accept();
				Connector newConnector = new Connector(newClient, this);
				newConnector.start();
				connections.add(newConnector);
				
				newConnector.sendMessage("Welcome to the server!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void cleanConnections() {
		for (Connector client : connections) {
			if (client.reciever.isClosed()) {
				connections.remove(client);
				System.out.println("Closed connection");
			}
		}
	}

	public void sendMessage(String message) {
		for (Connector client : connections) {
			client.sendMessage(message);
		}
		cleanConnections();
	}

	public void input(String in, Connector client) {
		System.out.println(in);
	}
}
