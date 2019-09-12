package org.frontear.infinity.commands.impl;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.EnumChatFormatting;
import org.frontear.infinity.commands.Command;

import java.lang.management.ManagementFactory;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class GC extends Command {
	boolean allowed = !ManagementFactory.getRuntimeMXBean().getInputArguments().contains("-XX:+DisableExplicitGC");

	public GC() {
		super("Invokes the garbage collector. This can potentially resolve memory issues");
	}

	@Override public void process(@NonNull String[] args) throws Exception {
		if (allowed) {
			val last = getMemory();
			System.gc();
			val current = getMemory();
			sendMessage("Freed ${Math.abs(current - last)}MB");
		}
		else {
			sendMessage("Explicit garbage collection has been disabled [-XX:+DisableExplicitGC]", EnumChatFormatting.RED);
		}
	}

	private long getMemory() {
		val runtime = Runtime.getRuntime();
		return (runtime.totalMemory() - runtime.freeMemory()) / (1024L * 1024L);
	}
}
