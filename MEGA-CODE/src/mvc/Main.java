package mvc;


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
		client = new Defender(model, view);
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
