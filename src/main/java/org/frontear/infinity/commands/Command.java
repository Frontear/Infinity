package org.frontear.infinity.commands;

import net.minecraft.util.EnumChatFormatting;
import org.frontear.infinity.Infinity;
import org.frontear.wrapper.IMinecraftWrapper;

public abstract class Command {
	protected final IMinecraftWrapper mc = IMinecraftWrapper.getMinecraft();
	private final String description;
	private final int arguments; // represents only the mandatory arguments

	public Command(String description) {
		this(description, 0);
	}

	public Command(String description, int arguments) {
		this.description = description;
		this.arguments = arguments;
	}

	public abstract void process(String[] args) throws Exception; // catch all

	protected final void sendMessage(String message, EnumChatFormatting... format) {
		Infinity.inst().getCommands().sendMessage(message, format);
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public String getDescription() {
		return description;
	}

	public int getArguments() {
		return arguments;
	}
}
