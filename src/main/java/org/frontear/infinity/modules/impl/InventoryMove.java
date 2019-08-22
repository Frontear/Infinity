package org.frontear.infinity.modules.impl;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.frontear.infinity.events.client.PacketEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public final class InventoryMove extends Module {
	private final KeyBinding[] keys = { mc.getGameSettings().keyBindForward, mc.getGameSettings().keyBindLeft, mc
			.getGameSettings().keyBindBack, mc.getGameSettings().keyBindRight, mc.getGameSettings().keyBindJump, mc
			.getGameSettings().keyBindSneak };

	public InventoryMove() {
		super(Keyboard.KEY_K, false, Category.PLAYER);
	}

	@SubscribeEvent public void onTick(TickEvent.PlayerTickEvent event) {
		if (mc.getCurrentScreen() instanceof GuiContainerCreative || mc.getCurrentScreen() instanceof GuiInventory) {
			Arrays.stream(keys)
					.forEach(x -> KeyBinding.setKeyBindState(x.getKeyCode(), Keyboard.isKeyDown(x.getKeyCode())));
		}
	}

	@SubscribeEvent public void onPacket(PacketEvent event) {
		if (event.getPacket() instanceof C03PacketPlayer) {
			((C03PacketPlayer) event
					.getPacket()).moving = false; // convince the server that you're being moved by circumstances outside your control
		}
	}
}
