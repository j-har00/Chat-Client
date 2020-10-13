package server.command;

import server.ClientMessage;

/**
 * JOIN concrete command
 * @author Joshua
 *
 */
class Join implements Command {
	
	private final CommandAction action;
	
	/**
	 * Constructor for Join
	 * @param action the CommandAction receiver class
	 */
	public Join(CommandAction action) {
		this.action = action;
	}

	/**
	 * Executes the join command
	 */
	@Override
	public void execute(ClientMessage clientMsg) {
		action.join(clientMsg);
	}
}
