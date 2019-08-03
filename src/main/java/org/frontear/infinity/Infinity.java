package org.frontear.infinity;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import org.frontear.infinity.events.ShutdownEvent;

import java.io.File;

@Mod(modid = "${modid}",
		version = "${version}") public class Infinity {
	private Logger log;
	private File config_dir;

	@Mod.EventHandler public void pre_init(FMLPreInitializationEvent event) {
		this.log = event.getModLog();
		this.config_dir = event.getModConfigurationDirectory();

		log.info("Pre_Init");
	}

	@Mod.EventHandler public void post_init(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);

		log.info("Post_Init");
	}

	@SubscribeEvent public void shutdown_event(ShutdownEvent event) {
		log.info("Shutdown_Event");
	}
}