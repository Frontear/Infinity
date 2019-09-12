package org.frontear.framework.threading;

import lombok.NonNull;

public final class InfiniteThread extends Thread {
	public InfiniteThread(@NonNull Runnable runnable) {
		super(() -> {
			//noinspection InfiniteLoopStatement
			while (true) {
				Thread.sleep(1); // prevents cpu-cycle burnout
				runnable.run();
			}
		});
	}

	@Override public synchronized void start() {
		super.start();
	}
}
