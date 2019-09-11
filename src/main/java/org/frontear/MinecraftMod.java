package org.frontear;

import com.ea.agentloader.AgentLoader;
import lombok.AccessLevel;
import lombok.experimental.*;
import lombok.val;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.frontear.framework.environment.ModEnvironment;
import org.frontear.framework.logger.impl.Logger;
import org.frontear.infinity.Infinity;

import java.util.Arrays;

@Mod(modid = "${modid}",
		version = "${version}") @FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) @ExtensionMethod(Arrays.class) public final class MinecraftMod {
	Logger logger = new Logger("${name} MinecraftMod");
	@NonFinal Thread concurrent;

	public static byte getEnvironment() {
		return ModEnvironment.FORGE;
	}

	@Mod.EventHandler private void onFMLPreInitialization(FMLPreInitializationEvent event) {
		if (true) {
			final int[] arr = { 1, 5, 3, 6, 8, 2 }; // 1, 2, 3, 5, 6, 8
			arr.sort();
			System.out.println(arr.toString()); // Arrays.toString()
		}
		logger.debug("Creating concurrent client thread");
		this.concurrent = new Thread(() -> {
			try {
				logger.debug("Loading agent");
				AgentLoader.loadAgentClass(MinecraftAgent.class.getName(), "");

				logger.debug("Loading ${name}");
				val instance = Infinity.inst();
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
