package org.frontear.framework.threading;

import lombok.NonNull;
import lombok.SneakyThrows;

public class InfiniteThread extends Thread {
	public InfiniteThread(@NonNull Runnable runnable) {
		super(() -> {
			//noinspection InfiniteLoopStatement
			while (true) {
				delay();
				runnable.run();
			}
		});
	}

	@SneakyThrows private static void delay() {
		Thread.sleep(1); // prevents cpu-cycle burnout
	}

	@Override public synchronized void start() {
		super.start();
	}
}
