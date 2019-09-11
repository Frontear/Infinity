package org.frontear.infinity.modules.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.frontear.infinity.events.client.PacketEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

@Deprecated @FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class InventoryMove extends Module {
	KeyBinding[] keys = { mc.getGameSettings().keyBindForward, mc.getGameSettings().keyBindLeft, mc
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
		// C0EPacketClickWindow -> When clicking an inventory slot
		// C0BPacketEntityAction -> Sprinting, Sneaking
		// C16PacketClientStatus -> When opening the inventory
		// C0DPacketCloseWindow -> When closing the inventory
	}
}
