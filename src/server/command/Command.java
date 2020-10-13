package server.command;

import server.ClientMessage;

/**
 * Interface for implementation in individual concrete commands
 * 
 * @author Joshua
 *
 */
interface Command {

	/**
	 * Executes the specified command
	 * 
	 * @param clientMsg the ClientMessage object
	 */
	void execute(ClientMessage clientMsg);

}
