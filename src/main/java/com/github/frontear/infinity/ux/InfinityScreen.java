package com.github.frontear.infinity.ux;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.InfinityMod;
import java.awt.Color;
import lombok.NonNull;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;

// todo: decide impl
@Deprecated
public final class InfinityScreen extends Screen {
    private final Screen parent;
    private final InfinityMod infinity;

    protected InfinityScreen(@NonNull final Screen parent) {
        super(new LiteralText("Infinity"));

        this.parent = parent;
        this.infinity = InfinityLoader.getMod();
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float delta) {
        this.renderBackground();
        this.drawCenteredString(font, infinity.getMetadata().getName(), width / 2, 15,
            Color.WHITE.getRGB());

        super.render(mouseX, mouseY, delta);
    }

    @Override
    public void init(final MinecraftClient client, final int width, final int height) {
        super.init(client, width, height);
    }
}
