package org.frontear.infinity.commands;

import lombok.Getter;
import lombok.NonNull;
import net.minecraft.util.EnumChatFormatting;
import org.frontear.infinity.Infinity;
import org.frontear.wrapper.IMinecraftWrapper;

public abstract class Command {
	protected final IMinecraftWrapper mc = IMinecraftWrapper.getMinecraft();
	@Getter private final String description;
	@Getter private final int arguments; // represents only the mandatory arguments

	public Command(@NonNull String description) {
		this(description, 0);
	}

	public Command(@NonNull String description, int arguments) {
		this.description = description;
		this.arguments = arguments;
	}

	public abstract void process(@NonNull String[] args) throws Exception; // catch all

	protected final void sendMessage(@NonNull String message, EnumChatFormatting... format) {
		Infinity.inst().getCommands().sendMessage(message, format);
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}
}
