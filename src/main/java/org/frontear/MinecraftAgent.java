package org.frontear;

import lombok.Getter;

import java.lang.instrument.Instrumentation;

public final class MinecraftAgent {
	@Getter private static Instrumentation instrumentation;

	public static void agentmain(String args, Instrumentation inst) {
		instrumentation = inst;
	}
}
