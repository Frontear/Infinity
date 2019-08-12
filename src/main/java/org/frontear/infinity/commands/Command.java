package org.frontear.infinity.commands;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.frontear.wrapper.IMinecraftWrapper;

public abstract class Command {
	protected final IMinecraftWrapper mc = IMinecraftWrapper.getMinecraft();
	private final String description;
	private final int arguments; // the mandatory arguments
	private final String name = String.format("[%s]: ", getName());

	public Command(String description) {
		this(description, 0);
	}

	public Command(String description, int arguments) {
		this.description = description;
		this.arguments = arguments;
	}

	final boolean processCommand(String[] args) {
		if (args.length < arguments) {
			respond(new ChatComponentText("Too few arguments."));
		}
		else {
			try {
				process(args);
				return true;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	protected final void respond(IChatComponent component) {
		mc.getChatGUI().printChatMessage(component);
	}

	protected abstract void process(String[] args) throws Exception; // catch all

	public String getDescription() {
		return description;
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}
}
