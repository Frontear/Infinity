package org.frontear.infinity.modules.impl;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.frontear.infinity.modules.*;
import org.frontear.infinity.utils.InventoryUtils;
import org.lwjgl.input.Keyboard;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class AutoTool extends Module {
    public AutoTool() {
        super(Keyboard.KEY_L, true, Category.PLAYER);
    }

    @SubscribeEvent
    public void onTick(final TickEvent.PlayerTickEvent event) {
        if (mc.gameSettings.keyBindAttack.isKeyDown()) {
            this.selectOptimizedItem(event.player.inventory);
            mc.clickMouse();
        }
    }

    // todo: detect materials and enchants
    public void selectOptimizedItem(final InventoryPlayer player) {
        val object = mc.objectMouseOver;
        var slot = 0;
        switch (object.typeOfHit) {
            case ENTITY:
                slot = InventoryUtils.findItem(player, ItemSword.class::isInstance, true);
                break;
            case BLOCK:
                val block = mc.theWorld.getBlockState(object.getBlockPos()).getBlock();
                slot = InventoryUtils.findItem(player,
                    x -> x instanceof ItemTool && ((ItemTool) x).effectiveBlocks
                        .contains(block), true);
                break;
            case MISS:
                return; // we don't need to handle this at all
            default: // codefactor is gonna cry
                logger.debug("${object.typeOfHit} not supported");
        }

        if (player.currentItem != slot) {
            if (slot != InventoryUtils.NO_ITEM_FOUND) {
                val tool = (ItemTool) player.mainInventory[slot]
                    .getItem(); // this shouldn't ever fail
                logger.debug(
                    "Setting slot to $slot with item ${tool.getSimpleName()}:${tool.getToolMaterial()} for hit ${object.typeOfHit}");
                player.currentItem = slot;
            }
            else {
                logger.debug("No slot with useful item found for hit ${object.typeOfHit}");
            }
        }
    }
}
