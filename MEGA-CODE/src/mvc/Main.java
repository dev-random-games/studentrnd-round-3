package mvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * 
 * Initialize the MVC system and begin all of the separate threads. And static
 * utility functions and variables should be written here as well.
 * 
 * @author Dylan Swiggett
 * 
 */
public class Main extends Thread {
	Model model;
	View view;
	Controller controller;
	Client client;

	public static void main(String[] args) {
		new Main().start();
	}

	public void run() {
		
		model = new Model();
		view = new View(model);
		
		System.out.print("[a]ttacker or [d]efender? ");
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	    String rawChoice = null;
	    
	    try {
	    	rawChoice = stdin.readLine();
	    } catch (IOException ioe) {
	    	System.out.println("io error while parsing attacker defender choice");
	    	System.exit(1);
	    }
		
	    if (rawChoice.toLowerCase().contains("a")) {
	    	client = new Attacker(model, view);
	    } else {
	    	client = new Defender(model, view);
	    }
	    
		controller = new Controller(model, view);
		model.setClient(client);
		
		model.start();
		controller.start();
		view.start();
		client.start();
		
		/*
		 * This will probably lead to a lot of concurrence issues. Whatever.
		 */
	}

}
