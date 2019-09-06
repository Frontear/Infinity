package org.frontear.infinity.modules.impl;

import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;
import net.minecraft.util.MovingObjectPosition;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.function.Predicate;

public class AutoTool extends Module {
	private static AutoTool self;

	public AutoTool() {
		super(Keyboard.KEY_L, true, Category.PLAYER);
		if (self == null) {
			self = this;
		}
	}

	public static void selectOptimizedItem(final InventoryPlayer player, final MovingObjectPosition object) {
		int slot = -1;
		switch (object.typeOfHit) {
			case ENTITY:
				slot = searchHotbar(player, ItemSword.class::isInstance);
				break;
			case BLOCK:
				final Block block = mc.getWorld().getBlockState(object.getBlockPos()).getBlock();
				slot = searchHotbar(player, x -> x instanceof ItemTool && ((ItemTool) x).effectiveBlocks
						.contains(block));
				break;
		}

		if (slot != -1) {
			player.currentItem = slot;
		}
	}

	private static int searchHotbar(InventoryPlayer inventory, Predicate<? super Item> filter) {
		for (int i = 0; i < 9; i++) {
			final ItemStack stack = inventory.mainInventory[i];
			if (stack != null && filter.test(stack.getItem())) {
				return i;
			}
		}

		return -1;
	}

	public static boolean active() {
		return self.isActive();
	}
}
