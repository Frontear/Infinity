package org.frontear;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.frontear.infinity.Infinity;

@Mod(modid = "${modid}",
		version = "${version}") public class ForgeMod {
	private Thread concurrent;

	@Mod.EventHandler private void onFMLPreInitialization(FMLPreInitializationEvent event) {
		this.concurrent = new Thread(() -> MinecraftForge.EVENT_BUS.register(Infinity.inst()));
		concurrent.setName("${name} loader");
		concurrent.start();
	}

	@Mod.EventHandler private void onFMLPostInitialization(FMLPostInitializationEvent event) throws InterruptedException {
		concurrent.join();
	}
}
