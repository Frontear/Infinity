package org.frontear.infinity.commands.impl;

import net.minecraft.util.ChatComponentText;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.commands.Command;

public class Help extends Command {
	public Help() {
		super("Gives helpful information about the different commands");
	}

	@Override public void process(String[] args) throws Exception {
		Infinity.inst().getCommands().getObjects().forEachRemaining(x -> {
			respond(new ChatComponentText(String.format("%s: %s", x.getName(), x.getDescription())));
		});
	}
}
