package server.command;

import server.ClientMessage;

/**
 * PART concrete command
 * @author Joshua
 *
 */
class Part implements Command {
	
	private final CommandAction action;
	
	/**
	 * Constructor for Part
	 * @param action the CommandAction receiver class
	 */
	public Part(CommandAction action) {
		this.action = action;
	}

	/**
	 * Executes the part command
	 */
	@Override
	public void execute(ClientMessage clientMsg) {
		action.part(clientMsg);
	}
}
