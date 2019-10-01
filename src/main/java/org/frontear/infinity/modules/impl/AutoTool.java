package org.frontear.infinity.modules.impl;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.function.Predicate;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class AutoTool extends Module {


	public AutoTool() {
		super(Keyboard.KEY_L, true, Category.PLAYER);
	}

	@SubscribeEvent public void onTick(TickEvent.PlayerTickEvent event) {
		if (mc.gameSettings.keyBindAttack.isKeyDown()) {
			this.selectOptimizedItem(event.player.inventory);
			mc.clickMouse();
		}
	}

	public void selectOptimizedItem(final InventoryPlayer player) {
		val object = mc.objectMouseOver;
		var slot = -1;
		switch (object.typeOfHit) {
			case ENTITY:
				slot = searchHotbar(player, ItemSword.class::isInstance);
				break;
			case BLOCK:
				val block = mc.theWorld.getBlockState(object.getBlockPos()).getBlock();
				slot = searchHotbar(player, x -> x instanceof ItemTool && ((ItemTool) x).effectiveBlocks
						.contains(block));
				break;
			case MISS:
				return; // we don't need to handle this at all
			default: // codefactor is gonna cry
				logger.debug("%s not supported", object.typeOfHit.name());
		}

		if (slot != -1) {
			val tool = (ItemTool) player.mainInventory[slot].getItem(); // this shouldn't ever fail
			logger.debug("Setting slot to $slot with item ${tool.getSimpleName()}:${tool.getToolMaterial()} for hit ${object.typeOfHit}");
			player.currentItem = slot;
		}
		else {
			logger.debug("No slot with useful item found for hit ${object.typeOfHit}");
		}
	}

	// todo: detect materials and enchants
	private int searchHotbar(InventoryPlayer inventory, Predicate<? super Item> filter) {
		for (var i = 0; i < 9; i++) {
			val stack = inventory.mainInventory[i];
			if (stack != null && filter.test(stack.getItem())) {
				return i;
			}
		}

		return -1;
	}
}
