package org.frontear.framework.threading;

import lombok.NonNull;

public class InfiniteThread extends Thread {
	public InfiniteThread(@NonNull Runnable runnable) {
		super(() -> {
			//noinspection InfiniteLoopStatement
			while (true) {
				try {
					Thread.sleep(1); // prevents cpu-cycle burnout
					runnable.run();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override public synchronized void start() {
		super.start();
	}
}
