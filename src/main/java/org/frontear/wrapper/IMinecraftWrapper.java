package org.frontear.wrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.Timer;

import java.io.File;

public interface IMinecraftWrapper {
	static IMinecraftWrapper getMinecraft() {
		return (IMinecraftWrapper) Minecraft.getMinecraft(); // will succeed due to mixins
	}

	Timer getTimer();

	EntityPlayerSP getPlayer();

	WorldClient getWorld();

	File getDirectory();
}
