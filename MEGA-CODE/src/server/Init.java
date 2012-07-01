package server;

import game.Map;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.ServerSocket;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class Init extends Thread{
	
	private ClientManager connector;
	
	static JFrame serverDisplay;
	static JPanel displayPanel;
	static JScrollPane scroll;
	static JTextArea textOut;
	static JTextPane textIn;
	
	public static int currentId;
	
	int WIDTH  = 500;
	int HEIGHT = 500;
	
	static Map map;
	
	public void run(){
		try {
			currentId = 0;
			ServerSocket server = new ServerSocket(12345);
			connector = new ClientManager(server);
			connector.start();
			
			/*
			 * Set up a display window for the server
			 */
			
			textOut = new JTextArea();
//			textOut.setLocation(20, 20);
//			textOut.setSize(WIDTH - 40, HEIGHT - 40);
//			textOut.setPreferredSize(new Dimension(WIDTH - 40, HEIGHT - 40));
			textOut.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			textOut.setEditable(false);
			
			scroll = new JScrollPane(textOut);
			scroll.setLocation(20, 20);
			scroll.setSize(WIDTH - 40, HEIGHT - 40);
			scroll.setPreferredSize(new Dimension(WIDTH - 40, HEIGHT - 40));
//			scroll.add(textOut);
			
			displayPanel = new JPanel();
			displayPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
			displayPanel.setSize(WIDTH, HEIGHT);
			displayPanel.add(scroll);
			
			serverDisplay = new JFrame("Server");
			serverDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			serverDisplay.getContentPane().setLayout(new GridLayout());
			serverDisplay.add(displayPanel);
			serverDisplay.pack();
			serverDisplay.setVisible(true);
			
			map = new Map("data/testcase.png", 32, 32, 29, 29);
			
			while (true) {
				try {
					
				} catch (Exception e) {
					System.exit(0);
				}
			}
			
		} catch (IOException e) {
			System.err.println("Error: Failed to start server");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new Init().start();
	}
	
	public static int getUniqueId(){
		currentId++;
		return currentId;
	}
	
	public static void sendServerMessage(String msg){
		textOut.append(msg + "\n");
		scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
	}
}
