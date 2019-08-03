package org.frontear.infinity;

import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import org.frontear.infinity.events.ShutdownEvent;

import java.io.File;

public class Infinity {
	private final Logger log;
	private final File config_dir;

	public Infinity(FMLPreInitializationEvent event) {
		this.log = event.getModLog();
		this.config_dir = event.getModConfigurationDirectory();
	}

	@SubscribeEvent public void onShutdown(ShutdownEvent event) {
		log.info("Shutting down!");
	}
}