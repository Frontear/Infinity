package org.frontear.mixins;

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
import net.minecraftforge.common.MinecraftForge;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.client.ShutdownEvent;
import org.frontear.infinity.events.client.StartupEvent;
import org.frontear.infinity.events.input.KeyEvent;
import org.frontear.infinity.events.input.MouseEvent;
import org.frontear.infinity.modules.impl.AutoClicker;
import org.frontear.infinity.modules.impl.AutoTool;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class) public abstract class MixinMinecraft {
	@Shadow public FontRenderer fontRendererObj;
	@Shadow public RenderGlobal renderGlobal;
	@Shadow public EntityPlayerSP thePlayer;
	@Shadow public WorldClient theWorld;
	@Shadow public GameSettings gameSettings;
	@Shadow public GuiScreen currentScreen;
	@Shadow public MovingObjectPosition objectMouseOver;
	@Shadow @Final private Session session;
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

	/**
	 * @author prplz Please see https://prplz.io/memoryfix/ for more information
	 */
	@SuppressWarnings("UnresolvedMixinReference") @Redirect(method = "*",
			at = @At(value = "INVOKE",
					target = "Ljava/lang/System;gc()V")) private void gc() {}

	@Inject(method = "clickMouse",
			at = @At("HEAD")) private void clickMouse(CallbackInfo info) {
		final AutoTool tool = Infinity.inst().getModules().get(AutoTool.class);
		if (tool.isActive()) {
			tool.selectOptimizedItem(thePlayer.inventory, objectMouseOver);
		}

		if (Infinity.inst().getModules().get(AutoClicker.class).isActive()) {
			leftClickCounter = 0; // this was meant to have been an auto-clicker preventative thing, but... yeah
		}
	}
}
