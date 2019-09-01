package org.frontear;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.frontear.framework.environment.ModdingEnvironment;
import org.frontear.framework.logger.impl.Logger;
import org.frontear.infinity.Infinity;

@Mod(modid = "${modid}",
		version = "${version}") public final class MinecraftMod {
	public static final byte ENVIRONMENT = ModdingEnvironment.FORGE;
	private final Logger logger = new Logger("${name} MinecraftMod");
	private Thread concurrent;

	@Mod.EventHandler private void onFMLPreInitialization(FMLPreInitializationEvent event) {
		logger.debug("Creating concurrent client thread");
		this.concurrent = new Thread(() -> {
			try {
				logger.debug("Loading ${name}");
				final Infinity instance = Infinity.inst();
				instance.getLogger().debug("Registering to EVENT_BUS");
				MinecraftForge.EVENT_BUS.register(instance);
			}
			catch (Throwable e) {
				e.printStackTrace();
				FMLCommonHandler.instance()
						.exitJava(-1, false); // Infinity has failed to load, which is technically game-breaking. (Mixins are dependant on it)
			}
		});
		concurrent.setName("${name} loader");
		concurrent.start();
	}

	@Mod.EventHandler private void onFMLPostInitialization(FMLPostInitializationEvent event) throws InterruptedException {
		logger.debug("Joining concurrent client thread");
		concurrent.join();
	}
}
