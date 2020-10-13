package server.command;

import server.ClientMessage;

/**
 * TIME concrete command
 * 
 * @author Joshua
 *
 */
class Time implements Command {

	private final CommandAction action;

	/**
	 * Constructor for Time
	 * 
	 * @param action the CommandAction receiver class
	 */
	public Time(CommandAction action) {
		this.action = action;
	}

	/**
	 * Executes the time command
	 */
	@Override
	public void execute(ClientMessage clientMsg) {
		action.time(clientMsg);
	}
}
