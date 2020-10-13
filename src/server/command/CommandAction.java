package server.command;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import server.ClientMessage;
import server.UserThread;
import server.channel.*;

/**
 * Receiver class for for commands. Stores the actual processing and result of
 * the commands
 * 
 * @author Joshua
 *
 */
class CommandAction {

	/**
	 * The local date and time.
	 * 
	 * @param clientMsg the message containing the request.
	 */
	public void time(ClientMessage clientMsg) {
		if (!isZero(clientMsg.getParameterCount())) {
			sendErrMessage("Usage: /time", clientMsg.getOrigin());
			return;
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy - HH:mm:ss");
		String dateAndTime = "Server Time: " + formatter.format(LocalDateTime.now());
		sendMessage(dateAndTime, clientMsg.getOrigin());
	}

	/**
	 * The message of the day.
	 * 
	 * @param clientMsg the message containing the request.
	 */
	public void motd(ClientMessage clientMsg) {
		if (!isZero(clientMsg.getParameterCount())) {
			sendErrMessage("Usage: /motd", clientMsg.getOrigin());
			return;
		}

		String motd = "This is the currrent MOTD. This does nothing else :).";
		sendMessage(motd, clientMsg.getOrigin());
	}

	/**
	 * Calls the display users method of the current thread or channel. If the
	 * channel parameter is provided and it doesn't exist and error message is sent.
	 * 
	 * @param clientMsg the message containing the request.
	 */
	public void names(ClientMessage clientMsg) {
		UserThread origin = clientMsg.getOrigin();

		if (clientMsg.getParameterCount() > 1) {
			sendErrMessage("Usage: /names [<channel>]", origin);
			return;
		}

		if (isZero(clientMsg.getParameterCount())) {
			origin.displayUsers();
			return;
		}

		try {
			Channel channel = getChannel(origin, clientMsg.getParameter(0));
			sendMessage(channel.getUsers(), origin);
		} catch (IllegalArgumentException iae) {
			sendErrMessage(iae.getMessage(), origin);
		}
	}

	/**
	 * Attempts to add a user to the channel specified. If channel doesn't exist an
	 * error message is sent to the origin
	 * 
	 * @param clientMsg the message containing the request.
	 */
	public void join(ClientMessage clientMsg) {
		UserThread origin = clientMsg.getOrigin();

		if (clientMsg.getParameterCount() != 1) {
			sendErrMessage("Usage: /join <channel>", origin);
			return;
		}

		try {
			Channel channel = getChannel(origin, clientMsg.getParameter(0));
			channel.broadcast(
					channel.getName() + ": New User " + origin.getUserName() + " Connected to " + channel.getName(),
					origin);
			channel.addUser(origin);
			sendMessage(channel.getUsers(), origin);
		} catch (IllegalArgumentException iae) {
			sendErrMessage(iae.getMessage(), origin);
		}
	}

	/**
	 * Attempts to remove a user from the channel specified. If channel doesn't
	 * exist or the user is not in a channel an error message is sent to the origin.
	 * 
	 * @param clientMsg the message containing the request.
	 */
	public void part(ClientMessage clientMsg) {
		UserThread origin = clientMsg.getOrigin();

		if (clientMsg.getParameterCount() != 1) {
			sendErrMessage("Usage: /part <channel>", origin);
			return;
		}

		try {
			Channel channel = getChannel(origin, clientMsg.getParameter(0));
			boolean result = channel.removeUser(origin);

			if (result) {
				channel.broadcast(
						channel.getName() + ": User " + origin.getUserName() + " has left " + channel.getName(),
						origin);
				sendMessage("Left " + channel.getName(), origin);
			} else {
				sendErrMessage("User not in channel", origin);
			}

		} catch (IllegalArgumentException iae) {
			sendErrMessage(iae.getMessage(), origin);
		}
	}

	/**
	 * Sends a private message to a user or a channel. If the channel or the
	 * recipient user doesn't exist then an error message is sent to the origin.
	 * 
	 * @param clientMsg the message containing the request
	 */
	public void privMsg(ClientMessage clientMsg) {
		UserThread origin = clientMsg.getOrigin();

		if (clientMsg.getParameterCount() != 2) {
			sendErrMessage("Usage: /privmsg <msgtarget> :<message>", origin);
			return;
		}

		String paramOne = clientMsg.getParameter(0);

		if (isChannelPrefix(paramOne)) {
			try {
				Channel channel = getChannel(origin, paramOne);
				channel.broadcast(
						origin.getUserName() + " PRIVMSG " + channel.getName() + ": " + clientMsg.getParameter(1),
						origin);
			} catch (IllegalArgumentException iae) {
				sendErrMessage(iae.getMessage(), origin);
			}
		} else {
			try {
				UserThread reciever = origin.getServer().getUserThread(paramOne);
				sendMessage(
						origin.getUserName() + " PRIVMSG " + reciever.getUserName() + ": " + clientMsg.getParameter(1),
						reciever);
			} catch (IllegalArgumentException iae) {
				sendErrMessage(iae.getMessage(), origin);
			}
		}
	}

	/**
	 * Checks if the ClientMessage has a valid channel prefix.
	 * 
	 * @param clientMsg the ClientMessage to be checked
	 * @return true if a valid prefix and false otherwise
	 */
	private boolean isChannelPrefix(String paramOne) {
		return paramOne.charAt(0) == '#';
	}

	/**
	 * Checks if the value is zero or not
	 * 
	 * @param count the value to be checked
	 * @return true if zero is 0 and false otherwise
	 */
	private boolean isZero(int count) {
		return count == 0;
	}

	/**
	 * Gets the channel requested by the ClientMessage parameters.
	 * 
	 * @param clientMsg the message containing the request.
	 * @return
	 * @throws IllegalArgumentException
	 */
	private Channel getChannel(UserThread user, String channelName) throws IllegalArgumentException {
		ChannelHandler chanHand = user.getServer().getChanHand();
		Channel channel = chanHand.getChannel(channelName);
		return channel;
	}

	/**
	 * Sends a message to the user
	 * 
	 * @param message the message to be sent
	 * @param user    the UserThread the message will be sent to.
	 */
	private void sendMessage(String message, UserThread user) {
		user.sendMessage(message);
	}

	/**
	 * Sends an error message to the user if syntax is incorrect. (May move to be in
	 * CommandAction)
	 * 
	 * @param message the error message to be sent.
	 * @param user    the UserThread the message will be sent to.
	 */
	private void sendErrMessage(String message, UserThread user) {
		user.sendMessage("Error : " + message);
	}
}
