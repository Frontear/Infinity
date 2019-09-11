package org.frontear.infinity.modules.impl;

import lombok.val;
import lombok.var;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraft.util.MovingObjectPosition;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.function.Predicate;

public class AutoTool extends Module {
	public AutoTool() {
		super(Keyboard.KEY_L, true, Category.PLAYER);
	}

	public void selectOptimizedItem(final InventoryPlayer player, final MovingObjectPosition object) {
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
			default:
				Infinity.inst().getLogger().debug("%s not supported", object.typeOfHit.name());
		}

		if (slot != -1) {
			player.currentItem = slot;
		}
	}

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
