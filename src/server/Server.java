package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;

import server.channel.ChannelHandler;

/**
 * Sets up and manages server
 * 
 * @author Joshua
 *
 */
public class Server {

	private ServerSocket server;
	private int port;
	private HashMap<String, Integer> userNames;
	private HashMap<Integer, UserThread> userThreads;
	private int uniqueId = 1;
	private ChannelHandler chanHand;

	/**
	 * Constructor for Server
	 * 
	 * @param port the port the server is running on
	 */
	public Server(int port) {
		this.port = port;
		userNames = new HashMap<String, Integer>();
		userThreads = new HashMap<Integer, UserThread>();
		chanHand = new ChannelHandler();
	}

	/**
	 * Runs the server and manages incoming connection attempts
	 */
	public void execute() {
		try {
			server = new ServerSocket(port);

			System.out.println("Server listening on port: " + port);

			while (true) {
				Socket socket = server.accept();
				System.out.println("User connected");

				UserThread newUser = new UserThread(socket, this);
				userThreads.put(uniqueId++, newUser);
				newUser.start();
			}
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	/**
	 * Main driver method
	 * 
	 * @param args port-number
	 */
	public static void main(String[] args) {
		if (args.length < 1) {
			System.err.println("Syntax: java Server <port-number>");
			System.exit(0);
		}

		int port = Integer.parseInt(args[0]);

		Server server = new Server(port);
		server.execute();
	}

	/**
	 * Sends a message to all users currently on the server
	 * 
	 * @param message the message
	 * @param exclude the user sending the message
	 */
	public void broadcast(String message, UserThread exclude) {
		for (UserThread user : userThreads.values()) {
			if (user != exclude) {
				user.sendMessage(message);
			}
		}
	}

	/**
	 * Adds a user name to the userNames HashMap
	 * 
	 * @param userName the user name
	 * @param user     the UserThread
	 */
	public void addUserName(String userName, UserThread user) {
		userNames.put(userName, findThreadId(user));
	}

	/**
	 * Finds the key value based on the thread given (May change so that UserThread
	 * constructor takes in the id)
	 * 
	 * @param user the UserThread to find the key of
	 * @return the key of the user
	 */
	private Integer findThreadId(UserThread user) {
		for (Entry<Integer, UserThread> thread : userThreads.entrySet()) {
			if (thread.getValue().equals(user)) {
				return thread.getKey();
			}
		}
		return -1;
	}

	/**
	 * Removes the user specified from the server list and it's corresponding
	 * thread.
	 * 
	 * @param userName the user name
	 */
	public void removeUser(String userName) {
		int id = userNames.get(userName);

		boolean removed = userNames.remove(userName, id);
		if (removed) {
			userThreads.remove(id);
			System.out.println("User " + userName + " has left the server.");
		}
	}

	/**
	 * Getter for userNames
	 * 
	 * @return userNames HashMap
	 */
	public HashMap<String, Integer> getUserNames() {
		return this.userNames;
	}

	/**
	 * Checks whether userNames is empty or not.
	 * 
	 * @return true if not empty and false otherwise
	 */
	public boolean hasUsers() {
		return !this.userNames.isEmpty();
	}

	/**
	 * Getter for chanHand
	 * 
	 * @return the chanHand
	 */
	public ChannelHandler getChanHand() {
		return chanHand;
	}

	/**
	 * Returns the UserThread of a user based on their name.
	 * 
	 * @param name the name of the user
	 * @return the corresponding UserThread to the name
	 * @throws IllegalArgumentException
	 */
	public UserThread getUserThread(String name) throws IllegalArgumentException {
		if (!userNames.containsKey(name)) {
			throw new IllegalArgumentException("User by the name of " + name + " doesn't exist");
		}

		Integer id = userNames.get(name);
		return userThreads.get(id);
	}
}
