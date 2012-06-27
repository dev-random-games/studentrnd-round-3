package mvc;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
	private Socket client;
	private ObjectOutputStream out;
	public ObjectInputStream in;
	
	String host = "localhost";
	int port = 12345;
	
	public Client(){
		try {
			client = new Socket(host, port);
			client.setSoTimeout(1000);
			
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(new BufferedInputStream(client.getInputStream()));
			
			System.out.println("Connection established with server");
			
			start();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while (true){
			try {
				System.out.println("test");
				String val = (String) in.readObject();
				System.out.println(val);
				
			} catch (Exception e){
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
				} else {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public void sendMessage(String message) {
		try {
			out.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
