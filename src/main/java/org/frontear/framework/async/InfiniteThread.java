package org.frontear.framework.async;

public class InfiniteThread extends Thread {
	public InfiniteThread(Runnable runnable, boolean trace) {
		super(() -> {
			try {
				while (true) {
					Thread.sleep(1); // prevents cpu-cycle burnout
					runnable.run();
				}
			}
			catch (InterruptedException e) {
				if (trace) {
					e.printStackTrace();
				}
			}
		});
	}

	public InfiniteThread(Runnable runnable) {
		this(runnable, false);
	}

	@Override public synchronized void start() {
		super.start();
	}
}
