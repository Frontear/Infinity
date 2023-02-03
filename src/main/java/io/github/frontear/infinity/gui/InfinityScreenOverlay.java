package io.github.frontear.infinity.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.frontear.infinity.tweaks.TweakManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;

import java.awt.*;

// TODO: create a custom font renderer
public class InfinityScreenOverlay extends GuiComponent {
    public static void render(PoseStack poseStack) {
        var font = Minecraft.getInstance().font;
        var offset = 2;

        poseStack.pushPose();
        poseStack.scale(2.0f, 2.0f, 2.0f);
        poseStack.translate(offset, offset, 1.0f);

        font.draw(poseStack, "Infinity ∞", 0, 0, Color.WHITE.getRGB());

        poseStack.popPose();

        TweakManager.renderEnabled(poseStack);
    }
}
