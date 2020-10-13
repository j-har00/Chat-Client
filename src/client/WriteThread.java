package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Handles the user sending data to the server
 * @author Joshua
 *
 */
public class WriteThread extends Thread {
	private PrintWriter out;
	private Socket socket;
	private Client client;
	
	/**
	 * Constructor for WriteThread
	 * @param socket the socket for the user
	 * @param client the current client instance
	 */
	public WriteThread(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;
		
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException i) {
			System.out.println(i);
		}
	}
	
	/**
	 * Handles outgoing messages from the client
	 */
	public void run() {
		Scanner in = new Scanner(System.in);
		
		System.out.print("\nEnter your name: ");
		String userName = in.nextLine();
		client.setUserName(userName);
		out.println(userName);
		
		String text;
		
		do {
			System.out.print("[" + userName + "]: ");
			text = in.nextLine();
			out.println(text);
		} while (!text.equalsIgnoreCase("Bye"));
		
		in.close();
		
		try {
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}
		
	}
}
