package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.command.CommandParser;

/**
 * Creates a thread allowing for simultaneous communication between multiple
 * clients
 * 
 * @author Joshua
 *
 */
public class UserThread extends Thread {

	private Socket socket;
	private Server server;
	private PrintWriter out;
	private BufferedReader in;
	private String userName;

	/**
	 * Constructor for UserThread
	 * 
	 * @param socket the user socket
	 * @param server the current server
	 */
	public UserThread(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}

	/**
	 * Establishes the connection and interfaces with the client
	 */
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			displayUsers();

			userName = in.readLine();
			server.addUserName(userName, this);

			server.broadcast("New user connected: " + userName, this);

			String message;

			CommandParser cmdParser = new CommandParser();

			do {
				message = in.readLine();
				

				if (message.charAt(0) == '/') {
					cmdParser.processCommand(message.substring(1), this);
				} else {
					server.broadcast("[" + userName + "]: " + message, this);
				}

			} while (!message.equalsIgnoreCase("Bye"));

			endConnection();

		} catch (IOException i) {
			System.out.println(i);
		}
	}

	/**
	 * Displays a list of all currently connected users.
	 */
	public void displayUsers() {
		if (!server.hasUsers()) {
			out.println("No users connected");
		} else {
			StringBuilder names = new StringBuilder("Users Connected to Server :");
			for (String userName : server.getUserNames().keySet()) {
				names.append(" " + userName);
			}
			out.println(names);
		}
	}

	/**
	 * Sends a message to the user thread
	 * 
	 * @param message the messages
	 */
	public void sendMessage(String message) {
		out.println(message);
	}

	/**
	 * Ends the connection to the server and sends a quit message to the server
	 * 
	 * @param userName the user name of the user leaving
	 * @throws IOException
	 */
	private void endConnection() throws IOException {
		server.removeUser(userName);
		socket.close();

		server.broadcast(userName + " has quit.", this);
	}

	/**
	 * Getter for server
	 * 
	 * @return the server
	 */
	public Server getServer() {
		return server;
	}

	/**
	 * Getter for userName
	 * 
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
}
