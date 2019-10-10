package org.frontear.infinity.utils;

import lombok.experimental.UtilityClass;
import lombok.val;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

@UtilityClass
public class EntityUtils {
    public EntityOtherPlayerMP clone(EntityPlayer player) {
        val clone = new EntityOtherPlayerMP(player.worldObj, player.getGameProfile());

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
