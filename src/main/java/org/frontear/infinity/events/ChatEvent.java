package org.frontear.infinity.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class ChatEvent extends Event {
	private String message;
	private boolean addToChat;

	public ChatEvent(String message, boolean addToChat) {
		this.message = message;
		this.addToChat = addToChat;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean shouldAddToChat() {
		return addToChat;
	}

	public void addToChat(boolean addToChat) {
		this.addToChat = addToChat;
	}
}
