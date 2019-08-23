package org.frontear.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.infinity.events.client.ShutdownEvent;
import org.frontear.infinity.events.client.StartupEvent;
import org.frontear.infinity.events.input.KeyEvent;
import org.frontear.infinity.events.input.MouseEvent;
import org.frontear.wrapper.IMinecraftWrapper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Minecraft.class) @Implements(@Interface(iface = IMinecraftWrapper.class,
		prefix = "wrap$")) public abstract class MixinMinecraft implements IMinecraftWrapper {
	@Shadow public FontRenderer fontRendererObj;
	@Shadow public RenderGlobal renderGlobal;
	@Shadow public EntityPlayerSP thePlayer;
	@Shadow public WorldClient theWorld;
	@Shadow @Final public File mcDataDir;
	@Shadow public GameSettings gameSettings;
	@Shadow public GuiScreen currentScreen;
	@Shadow @Final private Session session;
	@Shadow private Timer timer;
	@Shadow private int leftClickCounter;
	@Shadow private RenderManager renderManager;

	/**
	 * @author Frontear
	 * @reason {@link StartupEvent}
	 */
	@Inject(method = "startGame",
			at = @At(value = "TAIL")) private void startGame(CallbackInfo info) {
		MinecraftForge.EVENT_BUS.post(new StartupEvent());
	}

	/**
	 * @author Frontear
	 * @reason {@link ShutdownEvent}
	 */
	@Inject(method = "shutdownMinecraftApplet",
			at = @At(value = "HEAD")) private void shutdownMinecraftApplet(CallbackInfo info) {
		MinecraftForge.EVENT_BUS.post(new ShutdownEvent());
	}

	/**
	 * @author Frontear
	 * @reason {@link KeyEvent} and {@link MouseEvent}
	 */
	@Inject(method = "runTick",
			id = "inputEvent",
			at = { @At(value = "INVOKE",
					id = "key",
					target = "Lnet/minecraftforge/fml/common/FMLCommonHandler;fireKeyInput()V",
					remap = false), @At(value = "INVOKE",
					id = "mouse",
					target = "Lnet/minecraftforge/fml/common/FMLCommonHandler;fireMouseInput()V",
					remap = false) }) private void runTick(CallbackInfo info) {
		if (info.getId().equals("inputEvent:key")) {
			MinecraftForge.EVENT_BUS
					.post(new KeyEvent(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard
							.getEventKey(), Keyboard.getEventKeyState()));
		}
		else if (info.getId().equals("inputEvent:mouse")) {
			MinecraftForge.EVENT_BUS.post(new MouseEvent(Mouse.getEventButton(), Mouse.getEventButtonState()));
		}
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

	@Override public GameSettings getGameSettings() {
		return gameSettings;
	}

	@Override public RenderGlobal getRenderGlobal() {
		return renderGlobal;
	}

	@Override public FontRenderer getFontRenderer() {
		return fontRendererObj;
	}

	@Override public GuiScreen getCurrentScreen() {
		return currentScreen;
	}

	@Override public void clickMouse(boolean reset_click_counter) {
		if (reset_click_counter) {
			leftClickCounter = 0;
		}

		clickMouse();
	}

	@Shadow protected abstract void clickMouse();

	@Intrinsic public Session wrap$getSession() {
		return session;
	}

	@Intrinsic public RenderManager wrap$getRenderManager() {
		return renderManager;
	}

	@Intrinsic public void wrap$displayGuiScreen(GuiScreen screen) {
		shadow$displayGuiScreen(screen);
	}

	@Shadow public abstract void shadow$displayGuiScreen(GuiScreen guiScreenIn);
}
