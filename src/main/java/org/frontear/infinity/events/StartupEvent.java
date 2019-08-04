package org.frontear.infinity.events;

import net.minecraftforge.fml.common.eventhandler.Event;
import org.apache.logging.log4j.Logger;

public class StartupEvent extends Event {
	private final Logger logger;

	public StartupEvent(Logger logger) {
		this.logger = logger;
	}

	public Logger getLogger() {
		return logger;
	}
}
