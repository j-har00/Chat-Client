package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * Handles the user receiving data from the server
 * @author Joshua
 *
 */
public class ReadThread extends Thread {
	private BufferedReader in;
	private Socket socket;
	private Client client;
	
	/**
	 * Constructor for ReadThread
	 * @param socket the socket for the user
	 * @param client the current client instance
	 */
	public ReadThread(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;
		
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException i) {
			System.out.println(i);
		}
	}
	
	/**
	 * Handles incoming messages from the server
	 */
	public void run() {
		while (true) {
			try {
				String response = in.readLine();
				System.out.println("\n" + response);
				
				if (client.getUserName() != null) {
					System.out.print("[" + client.getUserName() + "]: ");
				}
			} catch (SocketException s) {
				System.out.println("Left Server.");
				break;
			} catch (IOException i) {
				System.out.println(i);
			} 
		} 
	}
}
