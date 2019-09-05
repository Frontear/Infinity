package org.frontear.wrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Session;

public interface IMinecraftWrapper {
	static IMinecraftWrapper getMinecraft() {
		return (IMinecraftWrapper) Minecraft.getMinecraft(); // will succeed due to mixins
	}

	EntityPlayerSP getPlayer();

	WorldClient getWorld();

	GameSettings getGameSettings();

	RenderGlobal getRenderGlobal();

	FontRenderer getFontRenderer();

	GuiScreen getCurrentScreen();

	void clickMouse(boolean reset_click_counter);

	Session getSession();

	RenderManager getRenderManager();

	void displayGuiScreen(GuiScreen screen);

	MovingObjectPosition getMouseOver();
}
