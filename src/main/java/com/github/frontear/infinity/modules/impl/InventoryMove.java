package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.events.client.PacketEvent;
import com.github.frontear.infinity.modules.*;
import java.util.Arrays;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

@Deprecated
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class InventoryMove extends Module {
    KeyBinding[] keys = { mc.gameSettings.keyBindForward, mc.gameSettings.keyBindLeft,
        mc.gameSettings.keyBindBack, mc.gameSettings.keyBindRight, mc.gameSettings.keyBindJump,
        mc.gameSettings.keyBindSneak };

    public InventoryMove() {
        super(Keyboard.KEY_K, false, Category.PLAYER);
    }

    @SubscribeEvent
    public void onTick(final TickEvent.PlayerTickEvent event) {
        if (mc.currentScreen instanceof GuiContainerCreative
            || mc.currentScreen instanceof GuiInventory) {
            Arrays.stream(keys)
                .forEach(x -> KeyBinding
                    .setKeyBindState(x.getKeyCode(), Keyboard.isKeyDown(x.getKeyCode())));
        }
    }

    @SubscribeEvent
    public void onPacket(final PacketEvent event) {
        // C0EPacketClickWindow -> When clicking an inventory slot
        // C0BPacketEntityAction -> Sprinting, Sneaking
        // C16PacketClientStatus -> When opening the inventory
        // C0DPacketCloseWindow -> When closing the inventory
    }
}
