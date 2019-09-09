package org.frontear.infinity.commands.impl;

import lombok.NonNull;
import net.minecraft.util.EnumChatFormatting;
import org.frontear.infinity.commands.Command;

import java.lang.management.ManagementFactory;

public final class GC extends Command {
	private final boolean allowed = !ManagementFactory.getRuntimeMXBean().getInputArguments()
			.contains("-XX:+DisableExplicitGC");

	public GC() {
		super("Invokes the garbage collector. This can potentially resolve memory issues");
	}

	@Override public void process(@NonNull String[] args) throws Exception {
		if (allowed) {
			final long last = getMemory();
			System.gc();
			final long current = getMemory();
			sendMessage(String.format("Freed %dMB", Math.abs(current - last)));
		}
		else {
			sendMessage("Explicit garbage collection has been disabled [-XX:+DisableExplicitGC]", EnumChatFormatting.RED);
		}
	}

	private long getMemory() {
		final Runtime runtime = Runtime.getRuntime();
		return (runtime.totalMemory() - runtime.freeMemory()) / (1024L * 1024L);
	}
}
