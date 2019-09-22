package org.frontear.mixins.impl;

import lombok.Getter;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.framework.utils.unsafe.MemoryPool;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.client.ShutdownEvent;
import org.frontear.infinity.events.client.StartupEvent;
import org.frontear.infinity.events.input.KeyEvent;
import org.frontear.infinity.events.input.MouseEvent;
import org.frontear.infinity.modules.impl.*;
import org.frontear.mixins.IMinecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class) public abstract class MixinMinecraft implements IMinecraft {
	@Getter private final MemoryPool pool = new MemoryPool();
	@Shadow public EntityPlayerSP thePlayer;
	@Shadow public MovingObjectPosition objectMouseOver;
	@Shadow private int leftClickCounter;

	@Redirect(method = "isAmbientOcclusionEnabled",
			at = @At(value = "FIELD",
					opcode = Opcodes.GETFIELD,
					target = "Lnet/minecraft/client/settings/GameSettings;ambientOcclusion:I")) private static int isAmbientOcclusionEnabled(GameSettings settings) {
		return Infinity.inst().getModules().get(Xray.class).isActive() ? 0 : settings.ambientOcclusion;
	}

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
		val tool = Infinity.inst().getModules().get(AutoTool.class);
		if (tool.isActive()) {
			tool.selectOptimizedItem(thePlayer.inventory, objectMouseOver);
		}

		if (Infinity.inst().getModules().get(AutoClicker.class).isActive()) {
			leftClickCounter = 0; // this was meant to have been an auto-clicker preventative thing, but... yeah
		}
	}
}
