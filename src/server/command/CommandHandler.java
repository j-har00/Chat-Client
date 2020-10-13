package server.command;

import java.util.HashMap;

import server.ClientMessage;

/**
 * Invoker class for commands
 * 
 * @author Joshua
 *
 */
class CommandHandler {

	private final HashMap<String, Command> commandMap;

	/**
	 * Constructor for CommandHandler
	 */
	public CommandHandler() {
		this.commandMap = new HashMap<>();
	}

	/**
	 * Adds a command to the HashMap of commands
	 * 
	 * @param commandName the name of the command
	 * @param command     the command
	 */
	public void register(String commandName, Command command) {
		commandMap.put(commandName, command);
	}

	/**
	 * Runs the command specified. If command is null an exception is thrown.
	 * 
	 * @param commandName the name of the command
	 * @param user        the UserThread the command is coming from
	 */
	public void execute(String commandName, ClientMessage clientMsg) {
		Command command = commandMap.get(commandName);
		if (command == null) {
			throw new IllegalStateException("no command register for: " + commandName);
		}
		command.execute(clientMsg);
	}
}
