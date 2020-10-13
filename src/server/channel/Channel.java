package server.channel;

import java.util.ArrayList;

import server.UserThread;

/**
 * Stores information on the channel
 * 
 * @author Joshua
 *
 */
public class Channel {

	private String name;
	private int maxUsers;
	private ArrayList<UserThread> users;

	/**
	 * Constructor for Channel
	 * 
	 * @param name the name of the channel
	 */
	public Channel(String name) {
		setName(name);
		this.maxUsers = 5;
		this.users = new ArrayList<UserThread>();
	}

	/**
	 * Setter for maxUsers
	 * 
	 * @param maxUsers the value of maxUsers
	 * @throws IllegalArgumentException
	 */
	public synchronized void setMaxUsers(int maxUsers) throws IllegalArgumentException {
		if (maxUsers <= 1) {
			throw new IllegalArgumentException("Max Users must be greater than 1");
		}

		this.maxUsers = maxUsers;
	}

	/**
	 * Setter for name
	 * 
	 * @param name the value of name
	 * @throws IllegalArgumentException
	 */
	public synchronized void setName(String name) throws IllegalArgumentException {
		if (name.charAt(0) != '#') {
			throw new IllegalArgumentException("Name must start with prefix #");
		}

		if (name.length() > 50) {
			throw new IllegalArgumentException("Name cannot be greater than 50 characters");
		}

		char chars[] = name.toCharArray();

		for (char ch : chars) {
			if (ch == ' ' || ch == ',') {
				throw new IllegalArgumentException("Invalid character in name : " + ch);
			}
		}

		this.name = name;
	}

	/**
	 * Adds a user to the channel user list. If a user is already in the list or the
	 * max size of the channel is reached, than the user is not added.
	 * 
	 * @param user the user to be added.
	 * @return true if user was added and false otherwise.
	 */
	public synchronized boolean addUser(UserThread user) {
		if (users.contains(user) || users.size() >= maxUsers) {
			return false;
		}

		users.add(user);
		return true;
	}

	/**
	 * Removes the specified user for the channel user list if they are in the
	 * channel.
	 * 
	 * @param user the user to be removed.
	 * @return true if user was removed and false if the user is not in the channel
	 */
	public synchronized boolean removeUser(UserThread user) {
		return users.remove(user);
	}

	/**
	 * Sends a message to all users in the channel
	 * 
	 * @param message the message
	 * @param exclude the user sending the message
	 */
	public synchronized void broadcast(String message, UserThread exclude) {
		for (UserThread user : users) {
			if (user != exclude) {
				user.sendMessage(message);
			}
		}
	}

	/**
	 * Checks whether users is empty or not.
	 * 
	 * @return true if not empty and false otherwise
	 */
	public synchronized boolean hasUsers() {
		return !users.isEmpty();
	}

	/**
	 * Getter for name
	 * 
	 * @return the name of the channel
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a string containing all users in the channel
	 * 
	 * @return the users
	 */
	public synchronized String getUsers() {
		if (!hasUsers()) {
			return "No users connected to " + getName();
		}

		StringBuilder names = new StringBuilder("Users connected to " + getName() + " :");
		for (UserThread user : users) {
			names.append(" " + user.getUserName());
		}

		return names.toString();
	}
}
