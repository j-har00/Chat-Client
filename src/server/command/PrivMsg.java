package server.command;

import server.ClientMessage;

/**
 * PRIVMSG concrete command
 * @author Joshua
 *
 */
class PrivMsg implements Command {

	private final CommandAction action;
	
	/**
	 * Constructor for PrivMsg 
	 * @param action
	 */
	public PrivMsg(CommandAction action) {
		this.action = action;
	}
	
	/**
	 * Executes the privmsg command
	 */
	@Override
	public void execute(ClientMessage clientMsg) {
		action.privMsg(clientMsg);
	}
}
