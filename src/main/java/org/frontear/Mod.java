package org.frontear;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.frontear.infinity.Infinity;

@net.minecraftforge.fml.common.Mod(modid = "${modid}",
		version = "${version}") public class Mod {
	private FMLPreInitializationEvent pre;

	@net.minecraftforge.fml.common.Mod.EventHandler public void pre_init(FMLPreInitializationEvent event) {
		this.pre = event;
	}

	@net.minecraftforge.fml.common.Mod.EventHandler public void post_init(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new Infinity(pre));
	}
}
