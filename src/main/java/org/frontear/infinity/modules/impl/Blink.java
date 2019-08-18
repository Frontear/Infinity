package org.frontear.infinity.modules.impl;

import com.google.common.collect.Queues;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.events.client.PacketEvent;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.Deque;

@Deprecated public class Blink extends Module {
	private static final byte ID = -1;
	private final Deque<Packet> packets = Queues.newArrayDeque();

	public Blink() {
		super(Keyboard.KEY_Z, false);
	}

	@Override protected void onToggle(boolean active) {
		if (active) {
			mc.getWorld().addEntityToWorld(ID, clone(mc.getPlayer()));
		}
		else {
			mc.getWorld().removeEntityFromWorld(ID);

			while (packets.size() > 0) {
				if (mc.getNetworkManager().isChannelOpen()) {
					mc.getNetworkManager().sendPacket(packets.remove());
				}
			}
		}
	}

	private EntityOtherPlayerMP clone(EntityPlayer player) {
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

	@SubscribeEvent public void onPacket(PacketEvent event) {
		if (event.getPacket() instanceof C03PacketPlayer) {
			packets.add(event.getPacket());

			event.setPacket(null);
		}
	}
}
