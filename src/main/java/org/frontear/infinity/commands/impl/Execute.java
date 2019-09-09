package org.frontear.infinity.commands.impl;

import org.apache.commons.lang3.ArrayUtils;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.commands.Command;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class Execute extends Command {
	private static final Executor async = Executors.newSingleThreadExecutor();

	public Execute() {
		super("Executes a command after a specified amount of milliseconds", 2);
	}

	@Override public void process(String[] args) throws Exception {
		async.execute(() -> {
			try {
				Thread.sleep(Long.parseLong(args[0]));
				Infinity.inst().getCommands().getObjects().filter(x -> x.getName().equalsIgnoreCase(args[1]))
						.findFirst().ifPresent(x -> {
					final String[] arguments = ArrayUtils.removeAll(args, 0, 1);
					try {
						x.process(arguments);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
}
