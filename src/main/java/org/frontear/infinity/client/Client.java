package org.frontear.infinity.client;

import org.frontear.framework.client.IClient;
import org.frontear.infinity.logger.Logger;

public class Client implements IClient {
	private final Logger logger;

	protected Client(String name) {
		this.logger = new Logger(name);
	}

	@Override public Logger getLogger() {
		return logger;
	}
}
