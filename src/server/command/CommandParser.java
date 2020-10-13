package server.command;

import java.util.ArrayList;

import server.ClientMessage;
import server.UserThread;

/**
 * Parses a command and determines whether it is valid in terms of the actual
 * command and/or parameters
 * 
 * @author Joshua
 *
 */
public class CommandParser {

	private final CommandHandler handler;
	private final CommandAction action;

	/**
	 * Constructor for CommandParser
	 */
	public CommandParser() {
		handler = new CommandHandler();
		action = new CommandAction();
		registerCmds();
	}

	/**
	 * Registers the current list of valid commands (May change to reading from
	 * file)
	 */
	private void registerCmds() {
		handler.register("TIME", new Time(action));
		handler.register("MOTD", new Motd(action));
		handler.register("NAMES", new Names(action));
		handler.register("JOIN", new Join(action));
		handler.register("PART", new Part(action));
		handler.register("PRIVMSG", new PrivMsg(action));
	}

	/**
	 * Takes in a command and checks to see if command is valid.
	 * 
	 * If valid then the corresponding command is executed. If invalid a message
	 * corresponding to this is returned.
	 * 
	 * @param message the message to be checked
	 * @param user    the origin on the message
	 */
	public void processCommand(String message, UserThread user) {
		ClientMessage clientMsg = parse(message, user);

		switch (clientMsg.getCommand()) {
		case "TIME":
			executeCommand(clientMsg);
			break;
		case "MOTD":
			executeCommand(clientMsg);
			break;
		case "NAMES":
			executeCommand(clientMsg);
			break;
		case "JOIN":
			executeCommand(clientMsg);
			break;
		case "PART":
			executeCommand(clientMsg);
			break;
		case "PRIVMSG":
			executeCommand(clientMsg);
			break;
		default:
			sendErrMessage("Invalid Command", user);
		}

	}

	/**
	 * Calls the execute command of the CommandHandler for the message given.
	 * 
	 * @param clientMsg the message from the client
	 */
	private void executeCommand(ClientMessage clientMsg) {
		handler.execute(clientMsg.getCommand(), clientMsg);
	}

	/**
	 * Sends an error message to the user if the command is not valid.
	 * 
	 * @param message the error message to be sent.
	 * @param user    the UserThread the message will be sent to.
	 */
	private void sendErrMessage(String message, UserThread user) {
		user.sendMessage("Error : " + message);
	}

	/**
	 * Parses the message and returns a ClientMessage
	 * 
	 * @param message the message
	 * @param user    the origin of the message
	 * @return the ClientMessage
	 */
	private ClientMessage parse(String message, UserThread user) {
		String parts[] = message.trim().split(" ");

		String command = parts[0].toUpperCase();

		ArrayList<String> parameters = new ArrayList<String>();

		for (int i = 1; i < parts.length; i++) {
			if (parts[i].charAt(0) == ':') {
				parameters.add(joinParameterParts(parts, i));
				break;
			}

			parameters.add(parts[i]);
		}

		return new ClientMessage(message, user, command, parameters);
	}

	/**
	 * Combines the elements of an array into a single string containing spaces
	 * between each part from the start position given.
	 * 
	 * @param parts the array to be combined
	 * @param start the index to start combining from
	 * @return the String result
	 */
	private String joinParameterParts(String parts[], int start) {

		StringBuilder result = new StringBuilder(parts[start].substring(1));

		for (int i = start + 1; i < parts.length; i++) {
			result.append(" " + parts[i]);
		}

		return result.toString();
	}
}
