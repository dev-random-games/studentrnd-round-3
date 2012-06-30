package server;

import java.io.*;
import java.net.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Connector extends Thread {
	public Socket reciever;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public int id;
	static int idCounter;

	ClientManager manager;

	public Connector(Socket reciever, ClientManager manager) {
		super();
		try {
			this.reciever = reciever;
			this.manager = manager;
			in = new ObjectInputStream(new BufferedInputStream(reciever.getInputStream()));
			out = new ObjectOutputStream(reciever.getOutputStream());
			
			System.out.println("New connection established");
			
			id = idCounter;
			idCounter++;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean sendMessage(MessageType type, String message) {
		try {
//			System.out.println(type.value());
			out.writeObject(type.value() + message);
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	public void run() {
		try {
			while (true) {
				try {
					String val = (String) in.readObject();
					MessageType type = MessageType.translate(val.charAt(0));
					String message = val.substring(1);
					
					manager.input(type, val, this);
				} catch (Exception e) {
					try {
						Thread.sleep(5);	// Wait for a little bit if no message has been received.
					} catch (InterruptedException e1){
						System.err.println("Error: failed to sleep");
						e1.printStackTrace();
						e.printStackTrace();
					}
				}
			}
			// in.close();
			// client.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), "Error: Could not establish connection to server.\n" + "Please contact a game admin.",
					"No Server!", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void exit(){
		try {
			out.close();
			in.close();
			Init.sendServerMessage("Client disconnected");
			manager.connections.remove(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
