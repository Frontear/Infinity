package org.frontear.infinity.modules.impl;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.events.client.PacketEvent;
import org.frontear.infinity.events.entity.UpdateEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.frontear.infinity.utils.EntityUtils;
import org.lwjgl.input.Keyboard;

public class Freecam extends Module {
	private static final byte ID = -2;
	private C03PacketPlayer packet;

	public Freecam() {
		super(Keyboard.KEY_I, false, Category.RENDER);
	}

	@SubscribeEvent public void onPacket(PacketEvent event) {
		if (event.getPacket() instanceof C03PacketPlayer) {
			event.setPacket(packet);
		}
		else if (event.getPacket() instanceof C0BPacketEntityAction || event.getPacket() instanceof C0APacketAnimation /*|| event
				.getPacket() instanceof C07PacketPlayerDigging || event
				.getPacket() instanceof C08PacketPlayerBlockPlacement*/) {
			event.setPacket(null);
		}
		else if (event.getPacket().getClass().getSimpleName().startsWith("C")) {
			System.out.println(event.getPacket().getClass());
		}
	}

	@SubscribeEvent public void onUpdate(UpdateEvent event) {
		if (event.getEntity() instanceof EntityPlayerSP) {
			event.getEntity().noClip = true;
			event.getEntity().onGround = false;
			event.getEntity().motionY = 0; // prevents rapid falling of gravity

			final float speed = 0.4f;
			((EntityPlayerSP) event.getEntity()).jumpMovementFactor = speed / 2;
			if (mc.getGameSettings().keyBindJump.isKeyDown()) {
				event.getEntity().motionY += speed;
			}
			if (mc.getGameSettings().keyBindSneak.isKeyDown()) {
				event.getEntity().motionY -= speed;
			}
		}
	}

	@Override public void load(Module self) {
		this.setBind(self.getBind());
	}

	@Override protected void onToggle(boolean active) {
		if (active) {
			this.packet = new PacketPlayer(mc.getPlayer());
			mc.getWorld().addEntityToWorld(ID, EntityUtils.clone(mc.getPlayer()));
		}
		else {
			mc.getWorld().removeEntityFromWorld(ID);

			mc.getPlayer()
					.setPositionAndRotation(packet.getPositionX(), packet.getPositionY(), packet.getPositionZ(), packet
							.getYaw(), packet.getPitch());
			mc.getPlayer().setVelocity(0, 0, 0);
			mc.getPlayer().noClip = false;
		}
	}

	private static class PacketPlayer extends C03PacketPlayer {
		PacketPlayer(EntityPlayer player) {
			this.x = player.posX;
			this.y = player.posY;
			this.z = player.posZ;
			this.yaw = player.rotationYaw;
			this.pitch = player.rotationPitch;
			this.onGround = player.onGround;
			this.moving = false;
			this.rotating = false;
		}
	}
}
