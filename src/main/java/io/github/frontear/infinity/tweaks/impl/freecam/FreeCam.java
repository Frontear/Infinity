package io.github.frontear.infinity.tweaks.impl.freecam;

import io.github.frontear.infinity.tweaks.AbstractTweak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.Entity;
import org.lwjgl.glfw.GLFW;

public class FreeCam extends AbstractTweak {
    private static final int ID = -1;

    public FreeCam() {
        super(GLFW.GLFW_KEY_I);
    }

    @Override
    public void toggle() {
        super.toggle();

        var client = Minecraft.getInstance();

        if (client.player != null && client.level != null) {
            if (isEnabled()) {
                var fake = new FreeCamPlayer(client.player);
                client.setCameraEntity(fake);
                client.level.addPlayer(ID, fake);

                client.player.input = new Input();
            }
            else {
                client.setCameraEntity(client.player);
                client.level.removeEntity(ID, Entity.RemovalReason.DISCARDED);

                client.player.input = new KeyboardInput(client.options);
            }
        }
    }
}
