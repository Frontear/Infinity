package org.frontear.wrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Session;
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

	GameSettings getGameSettings();

	RenderGlobal getRenderGlobal();

	FontRenderer getFontRenderer();

	Session getSession();

	void clickMouse(boolean reset_click_counter);
}
