package org.frontear;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.client.Client;

import java.lang.reflect.Constructor;
import java.util.Arrays;

@Mod(modid = "${modid}",
		version = "${version}") public class ForgeMod {
	private Thread concurrent;

	@Mod.EventHandler private void onFMLPreInitialization(FMLPreInitializationEvent event) {
		this.concurrent = new Thread(() -> {
			try {
				final Infinity instance = createClient(Infinity.class, "${name}");
				MinecraftForge.EVENT_BUS.register(instance);
			}
			catch (Throwable e) {
				FMLCommonHandler.instance().exitJava(-1, false); // Infinity has failed to start, which is technically game-breaking. (Mixins are dependant on it)
			}
		});
		concurrent.setName("${name} loader");
		concurrent.start();
	}

	@Mod.EventHandler private void onFMLPostInitialization(FMLPostInitializationEvent event) throws InterruptedException {
		concurrent.join();
	}

	private <C extends Client> C createClient(Class<C> client, Object... parameters) throws Throwable {
		final Constructor<C> singleton = client.getDeclaredConstructor(Arrays.stream(parameters).map(Object::getClass).toArray(Class[]::new));
		singleton.setAccessible(true);
		return singleton.newInstance(parameters);
	}
}
