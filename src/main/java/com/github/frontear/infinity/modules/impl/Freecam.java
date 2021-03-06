package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.events.client.PacketEvent;
import com.github.frontear.infinity.events.entity.UpdateEvent;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.utils.EntityUtils;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class Freecam extends Module {
    private static final byte ID = -2;
    @NonFinal EntityOtherPlayerMP clone;

    public Freecam() {
        super(Keyboard.KEY_I, false, Category.RENDER);
    }

    @SubscribeEvent
    public void onPacket(final PacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            event.setPacket(normalize(clone, (C03PacketPlayer) event.getPacket()));
        }
        else if (event.getPacket() instanceof C0BPacketEntityAction || event
            .getPacket() instanceof C0APacketAnimation /*|| event
				.getPacket() instanceof C07PacketPlayerDigging || event
				.getPacket() instanceof C08PacketPlayerBlockPlacement*/) {
            event.setPacket(null);
        }
    }

    private C03PacketPlayer normalize(final EntityPlayer player, final C03PacketPlayer packet) {
        packet.x = player.posX;
        packet.y = player.posY;
        packet.z = player.posZ;
        packet.yaw = player.rotationYaw;
        packet.pitch = player.rotationPitch;
        packet.onGround = player.onGround;
        packet.moving = false; //player.motionX != 0 || player.motionY != 0 || player.motionZ != 0;
        packet.rotating = false; //player.rotationYaw != player.prevRotationYaw || player.rotationPitch != player.prevRotationPitch;

        return packet;
    }

    @SubscribeEvent
    public void onUpdate(final UpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayerSP) {
            event.getEntity().noClip = true;
            event.getEntity().onGround = false;
            event.getEntity().motionY = 0; // prevents rapid falling of gravity

            val speed = 0.4f;
            ((EntityPlayerSP) event.getEntity()).jumpMovementFactor = speed / 2;
            if (mc.gameSettings.keyBindJump.isKeyDown()) {
                event.getEntity().motionY += speed;
            }
            if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                event.getEntity().motionY -= speed;
            }
        }
    }

    @Override
    public void load(final Module self) {
        this.setBind(self.getBind());
    }

    @Override
    protected void onToggle(final boolean active) {
        if (active) {
            this.clone = EntityUtils.clone(mc.thePlayer);

            mc.theWorld.addEntityToWorld(ID, clone);
        }
        else {
            mc.theWorld.removeEntityFromWorld(ID);

            mc.thePlayer
                .setPositionAndRotation(clone.posX, clone.posY, clone.posZ, clone.rotationYaw,
                    clone.rotationPitch);
            mc.thePlayer.setVelocity(0, 0, 0);
            mc.thePlayer.noClip = false;
        }
    }
}
