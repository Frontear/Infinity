package org.frontear.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.Timer;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.infinity.events.ShutdownEvent;
import org.frontear.infinity.events.StartupEvent;
import org.frontear.wrapper.IMinecraftWrapper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Minecraft.class) @Interface(iface = IMinecraftWrapper.class,
		prefix = "wrap$") public abstract class MixinMinecraft implements IMinecraftWrapper {
	@Shadow public EntityPlayerSP thePlayer;
	@Shadow public WorldClient theWorld;
	@Shadow @Final public File mcDataDir;
	@Shadow public GuiIngame ingameGUI;
	@Shadow private Timer timer;

	/**
	 * @reason {@link org.frontear.infinity.events.StartupEvent}
	 * @author Frontear
	 */
	@Inject(method = "startGame",
			at = @At(value = "TAIL")) private void startGame(CallbackInfo info) {
		MinecraftForge.EVENT_BUS.post(new StartupEvent());
	}

	/**
	 * @reason {@link org.frontear.infinity.events.ShutdownEvent}
	 * @author Frontear
	 */
	@Inject(method = "shutdownMinecraftApplet",
			at = @At(value = "HEAD")) private void shutdownMinecraftApplet(CallbackInfo info) {
		MinecraftForge.EVENT_BUS.post(new ShutdownEvent());
	}

	@Override public Timer getTimer() {
		return timer;
	}

	@Override public EntityPlayerSP getPlayer() {
		return thePlayer;
	}

	@Override public WorldClient getWorld() {
		return theWorld;
	}

	@Override public File getDirectory() {
		return mcDataDir;
	}

	@Override public GuiNewChat getChatGUI() {
		return ingameGUI.getChatGUI();
	}
}
