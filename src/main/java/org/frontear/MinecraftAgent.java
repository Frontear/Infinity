package org.frontear;

import lombok.Getter;

import java.lang.instrument.Instrumentation;

//import sun.misc.Unsafe;
//import java.lang.reflect.Field;

public final class MinecraftAgent {
	@Getter private static Instrumentation instrumentation;
	//@Getter private static Unsafe unsafe;

	public static void agentmain(String args, Instrumentation inst) {
		instrumentation = inst;
		//final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
		//theUnsafe.setAccessible(true);
		//unsafe = (Unsafe) theUnsafe.get(null);
	}
}
