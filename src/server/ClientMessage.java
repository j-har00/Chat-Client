package server;

import java.util.ArrayList;

/**
 * Creates a message from the client for internal use in the server
 * 
 * @author Joshua
 *
 */
public class ClientMessage {
	private String message;
	private UserThread origin;
	private String command;
	private ArrayList<String> parameters;

	/**
	 * Constructor for ClientMessage
	 * 
	 * @param message the message
	 * @param origin  the UserThread the message came from
	 */
	public ClientMessage(String message, UserThread origin) {
		this(message, origin, null);
	}

	/**
	 * Constructor for ClientMessage
	 * 
	 * @param message the message
	 * @param origin  the UserThread the message came from
	 * @param command the command
	 */
	public ClientMessage(String message, UserThread origin, String command) {
		this(message, origin, command, new ArrayList<String>());
	}

	/**
	 * Constructor for ClientMessage
	 * 
	 * @param message    the message
	 * @param origin     the UserThread the message came from
	 * @param command    the command
	 * @param parameters the parameters of the command
	 */
	public ClientMessage(String message, UserThread origin, String command, ArrayList<String> parameters) {
		this.message = message;
		this.origin = origin;
		this.command = command;
		this.parameters = parameters;
	}

	/**
	 * Getter for message
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Getter for origin
	 * 
	 * @return the origin
	 */
	public UserThread getOrigin() {
		return origin;
	}

	/**
	 * Getter for command
	 * 
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Getter for parameter based on the index
	 * 
	 * @param index the index of the parameter
	 * @return the parameter
	 */
	public String getParameter(int index) {
		return parameters.get(index);
	}

	/**
	 * Returns the count of the parameters stored
	 * 
	 * @return the parameter count
	 */
	public int getParameterCount() {
		return parameters.size();
	}
}
