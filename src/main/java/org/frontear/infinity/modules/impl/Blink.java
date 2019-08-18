package org.frontear.infinity.modules.impl;

import com.google.common.collect.Queues;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.events.client.PacketEvent;
import org.frontear.infinity.modules.Module;
import org.frontear.infinity.utils.EntityUtils;
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
			mc.getWorld().addEntityToWorld(ID, EntityUtils.clone(mc.getPlayer()));
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

	@SubscribeEvent public void onPacket(PacketEvent event) {
		if (event.getPacket() instanceof C03PacketPlayer) {
			packets.add(event.getPacket());

			event.setPacket(null);
		}
	}
}
