package server.command;

import server.ClientMessage;

/**
 * NAMES concrete command
 * 
 * @author Joshua
 *
 */
class Names implements Command {

	private final CommandAction action;

	/**
	 * Constructor for Names
	 * 
	 * @param action the CommandAction reciver class
	 */
	public Names(CommandAction action) {
		this.action = action;
	}

	/**
	 * Executes the names command
	 */
	@Override
	public void execute(ClientMessage clientMsg) {
		action.names(clientMsg);
	}
}
