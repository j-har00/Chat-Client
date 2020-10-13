package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Sets up and manages a client that connects to a server
 * 
 * @author Joshua
 *
 */
public class Client {
	private String hostName;
	private int port;
	private String userName;

	/**
	 * Constructor for Client
	 * 
	 * @param hostName the host address of the server
	 * @param port     the port of the server
	 */
	public Client(String hostName, int port) {
		this.hostName = hostName;
		this.port = port;
	}

	/**
	 * Runs the server and initialises the reading and writing thread to handle
	 * simultaneous info coming in.
	 */
	public void execute() {
		try {
			Socket socket = new Socket(hostName, port);

			System.out.println("Connected to chat server");

			new ReadThread(socket, this).start();
			new WriteThread(socket, this).start();
		} catch (UnknownHostException uhe) {
			System.out.println("Server not found: " + uhe.getMessage());
		} catch (IOException i) {
			System.out.println("I/O Error: " + i.getMessage());
		}
	}

	/**
	 * Setter for userName
	 * @param userName the userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Getter for userName
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Main driving method
	 * @param args hostName port
	 */
	public static void main(String[] args) {
		if (args.length < 2)
			return;

		String hostName = args[0];
		int port = Integer.parseInt(args[1]);

		Client client = new Client(hostName, port);
		client.execute();
	}
}
