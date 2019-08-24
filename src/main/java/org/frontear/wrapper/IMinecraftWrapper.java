package org.frontear.wrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
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

	GuiScreen getCurrentScreen();

	void clickMouse(boolean reset_click_counter);

	Session getSession();

	RenderManager getRenderManager();

	GuiNewChat getChatGUI();

	void displayGuiScreen(GuiScreen screen);
}
