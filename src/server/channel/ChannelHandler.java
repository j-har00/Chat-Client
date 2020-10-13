package server.channel;

import java.util.HashMap;

/**
 * Stores and manages channels
 * 
 * @author Joshua
 *
 */
public class ChannelHandler {

	private HashMap<String, Channel> channels;

	/**
	 * Constructor for ChannelHandler
	 */
	public ChannelHandler() {
		this.channels = new HashMap<String, Channel>();
		defaultChannels();
	}
	
	/**
	 * Adds default channels
	 */
	private void defaultChannels() {
		addChannel("#General");
		addChannel("#Other");
	}

	/**
	 * Adds a channel
	 * 
	 * @param channel the channel to be added
	 * @return true if channel was added and false if channel is null or is already
	 *         in HashMap
	 */
	public boolean addChannel(Channel channel) {
		if (channel == null || channels.containsValue(channel)) {
			return false;
		}

		channels.put(channel.getName(), channel);
		return true;
	}

	/**
	 * Adds a channel using a name
	 * 
	 * @param name the name of the channel
	 * @return true if channel was added and false if name was null
	 */
	public boolean addChannel(String name) {
		if (name == null) {
			return false;
		}

		channels.put(name, new Channel(name));
		return true;
	}

	/**
	 * Removes the specified channel
	 * 
	 * @param name the name of the channel
	 * @return true if channel was removed and false if channel was not stored
	 */
	public boolean removeChannel(String name) {
		return channels.remove(name) != null;
	}

	/**
	 * Checks if the specified channel is stored
	 * 
	 * @param name the name of the channel
	 * @return true if channel is stored and false otherwise
	 */
	public boolean hasChannel(String name) {
		return channels.get(name) != null;
	}

	/**
	 * Getter for channel
	 * 
	 * @param name the name of the channel
	 * @return the channel
	 * @throws IllegalArgumentException
	 */
	public Channel getChannel(String name) throws IllegalArgumentException {
		if (!channels.containsKey(name)) {
			throw new IllegalArgumentException("Channel name not valid.");
		}
		
		return channels.get(name);
	}
}
