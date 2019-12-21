package org.frontear.infinity.modules.impl;

import com.google.common.collect.Queues;
import java.util.Deque;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.events.client.PacketEvent;
import org.frontear.infinity.modules.*;
import org.frontear.infinity.utils.EntityUtils;
import org.lwjgl.input.Keyboard;

@Deprecated
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class Blink extends Module {
    private static final byte ID = -1;
    Deque<Packet> packets = Queues.newArrayDeque();

    public Blink() {
        super(Keyboard.KEY_Z, false, Category.RENDER);
    }

    @Override
    protected void onToggle(final boolean active) {
        if (active) {
            mc.theWorld.addEntityToWorld(ID, EntityUtils.clone(mc.thePlayer));
        }
        else {
            mc.theWorld.removeEntityFromWorld(ID);

            while (packets.size() > 0) {
                if (mc.thePlayer.sendQueue.getNetworkManager().isChannelOpen()) {
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(packets.remove());
                }
            }
        }
    }

    @SubscribeEvent
    public void onPacket(final PacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            packets.add(event.getPacket());

            event.setPacket(null);
        }
    }
}
