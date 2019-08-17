package org.frontear.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.Timer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.frontear.infinity.events.execution.ShutdownEvent;
import org.frontear.infinity.events.execution.StartupEvent;
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
	@Shadow public EntityPlayerSP thePlayer;
	@Shadow public WorldClient theWorld;
	@Shadow @Final public File mcDataDir;
	@Shadow private Timer timer;

	@Inject(method = "startGame",
			at = @At(value = "TAIL")) private void startGame(CallbackInfo info) {
		MinecraftForge.EVENT_BUS.post(new StartupEvent());
	}

	@Inject(method = "shutdownMinecraftApplet",
			at = @At(value = "HEAD")) private void shutdownMinecraftApplet(CallbackInfo info) {
		MinecraftForge.EVENT_BUS.post(new ShutdownEvent());
	}

	@Redirect(method = "runTick",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraftforge/fml/common/FMLCommonHandler;fireKeyInput()V")) private void fireKeyInput(FMLCommonHandler common) {
		MinecraftForge.EVENT_BUS
				.post(new KeyEvent(Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard
						.getEventKey(), Keyboard.getEventKeyState()));
		common.fireKeyInput();
	}

	@Redirect(method = "runTick",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraftforge/fml/common/FMLCommonHandler;fireMouseInput()V")) private void fireMouseInput(FMLCommonHandler common) {
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
}
