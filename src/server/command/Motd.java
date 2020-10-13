package server.command;

import server.ClientMessage;

/**
 * MOTD concrete command
 * 
 * @author Joshua
 *
 */
class Motd implements Command {

	private final CommandAction action;

	/**
	 * Constructor for Motd
	 * 
	 * @param action the CommandAction receiver class
	 */
	public Motd(CommandAction action) {
		this.action = action;
	}

	/**
	 * Executes the command
	 */
	@Override
	public void execute(ClientMessage clientMsg) {
		action.motd(clientMsg);
	}
}
