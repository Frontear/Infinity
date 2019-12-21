package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.modules.Module;
import lombok.val;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.modules.*;
import org.lwjgl.input.Keyboard;

public final class NoFOV extends Module {
    public NoFOV() {
        super(Keyboard.KEY_V, true, Category.RENDER);
    }

    @SubscribeEvent
    public void onFOV(final FOVUpdateEvent event) {
        if (event.entity instanceof EntityPlayerSP) {
            event.newfov = reset(event.entity, event.fov);

            if (event.entity.isSprinting()) {
                event.newfov *= 1.15f;
            }
        }
    }

    // reverses fov, from AbstractClientPlayer
    private float reset(final EntityPlayer entity, final float fov) {
        val iattributeinstance = entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        return (float) ((double) fov / (
            (iattributeinstance.getAttributeValue() / (double) entity.capabilities
                .getWalkSpeed() + 1.0D) / 2.0D));
    }
}
