package io.github.frontear.infinity.tweaks.impl.freecam;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import org.jetbrains.annotations.Nullable;

public class FreeCamPlayer extends LocalPlayer {
    public FreeCamPlayer(LocalPlayer player) {
        super(Minecraft.getInstance(), player.clientLevel, new FakeClientPacketListener(), null, null, false, false);

        getAbilities().flying = true;
        copyPosition(player);
        input = player.input;
        noPhysics = true;
    }

    @Override
    public boolean isSpectator() {
        return true;
    }

    private static class FakeClientPacketListener extends ClientPacketListener {
        public FakeClientPacketListener() {
            super(Minecraft.getInstance(), null, new FakeConnection(), new GameProfile(null, "FreeCamPlayer"), null);
        }

        @Override
        public void send(Packet<?> packet) {
        }
    }

    private static class FakeConnection extends Connection {
        public FakeConnection() {
            super(null);
        }

        @Override
        public void send(Packet<?> packet) {
        }

        @Override
        public void send(Packet<?> packet, @Nullable PacketSendListener sendListener) {
        }
    }
}
