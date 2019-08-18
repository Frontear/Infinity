package org.frontear.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.frontear.infinity.events.client.ShutdownEvent;
import org.frontear.infinity.events.client.StartupEvent;
import org.frontear.infinity.events.input.KeyEvent;
import org.frontear.infinity.events.input.MouseEvent;
import org.frontear.wrapper.IMinecraftWrapper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Minecraft.class) @Interface(iface = IMinecraftWrapper.class,
		prefix = "wrap$") public abstract class MixinMinecraft implements IMinecraftWrapper {
	@Shadow public FontRenderer fontRendererObj;
	@Shadow public RenderGlobal renderGlobal;
	@Shadow public EntityPlayerSP thePlayer;
	@Shadow public WorldClient theWorld;
	@Shadow @Final public File mcDataDir;
	@Shadow public GameSettings gameSettings;
	@Shadow @Final private Session session;
	@Shadow private Timer timer;
	@Shadow private int leftClickCounter;

	/**
	 * @author Frontear
	 * @see StartupEvent
	 */
	@Inject(method = "startGame",
			at = @At(value = "TAIL")) private void startGame(CallbackInfo info) {
		MinecraftForge.EVENT_BUS.post(new StartupEvent());
	}

	/**
	 * @author Frontear
	 * @see ShutdownEvent
	 */
	@Inject(method = "shutdownMinecraftApplet",
			at = @At(value = "HEAD")) private void shutdownMinecraftApplet(CallbackInfo info) {
		MinecraftForge.EVENT_BUS.post(new ShutdownEvent());
	}

	/**
	 * @param common The {@link FMLCommonHandler} instance
	 *
	 * @author Frontear
	 * @see KeyEvent
	 */
	@Redirect(method = "runTick",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraftforge/fml/common/FMLCommonHandler;fireKeyInput()V",
					remap = false)) private void fireKeyInput(FMLCommonHandler common) {
		MinecraftForge.EVENT_BUS
				.post(new KeyEvent(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard
						.getEventKey(), Keyboard.getEventKeyState()));
		common.fireKeyInput();
	}

	/**
	 * @param common The {@link FMLCommonHandler} instance
	 *
	 * @author Frontear
	 * @see MouseEvent
	 */
	@Redirect(method = "runTick",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraftforge/fml/common/FMLCommonHandler;fireMouseInput()V",
					remap = false)) private void fireMouseInput(FMLCommonHandler common) {
		MinecraftForge.EVENT_BUS.post(new MouseEvent(Mouse.getEventButton(), Mouse.getEventButtonState()));
		common.fireMouseInput();
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

	@Override public NetworkManager getNetworkManager() {
		return thePlayer.sendQueue.getNetworkManager();
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
}
