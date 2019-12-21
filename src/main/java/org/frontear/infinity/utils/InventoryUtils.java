package org.frontear.infinity.utils;

import java.util.function.Predicate;
import lombok.experimental.UtilityClass;
import lombok.*;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;

@UtilityClass
public class InventoryUtils {
    public final byte NO_ITEM_FOUND = -1;

    public int findItem(final InventoryPlayer player, final Predicate<? super Item> filter,
        final boolean hotbar) {
        for (var i = 0; i < (hotbar ? 9 : player.mainInventory.length); i++) {
            val stack = player.mainInventory[i];
            if (stack != null && filter.test(stack.getItem())) {
                return i;
            }
        }

        return NO_ITEM_FOUND;
    }
}
