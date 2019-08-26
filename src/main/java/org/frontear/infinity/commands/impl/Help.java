package org.frontear.infinity.commands.impl;

import org.frontear.infinity.Infinity;
import org.frontear.infinity.commands.Command;

public class Help extends Command {
	public Help() {
		super("Displays useful information about the commands.");
	}

	@Override protected void process(String[] args) throws Exception {
		Infinity.inst().getCommands().getObjects().forEach(x -> {
			sendMessage(String.format("%s: %s", x.getName(), x.getDescription()));
		});
	}
}