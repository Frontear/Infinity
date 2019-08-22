package org.frontear.infinity.utils;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

public final class EntityUtils {
	public static EntityOtherPlayerMP clone(EntityPlayer player) {
		final EntityOtherPlayerMP clone = new EntityOtherPlayerMP(player.worldObj, player.getGameProfile());

		// rotation, position, and inventory (doesn't copy all rotations for some reason)
		clone.copyLocationAndAnglesFrom(player);
		clone.inventory.copyInventory(player.inventory);

		// rotates the head correctly
		clone.rotationYawHead = player.rotationYawHead;
		clone.renderYawOffset = player.renderYawOffset;

		// prevents cape from spazzing out
		clone.chasingPosX = player.posX;
		clone.chasingPosY = player.posY;
		clone.chasingPosZ = player.posZ;

		return clone;
	}
}
