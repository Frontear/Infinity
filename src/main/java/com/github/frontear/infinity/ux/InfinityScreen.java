package com.github.frontear.infinity.ux;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.InfinityMod;
import java.awt.Color;
import lombok.NonNull;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

// todo: decide impl
@Deprecated
public final class InfinityScreen extends Screen {
    private final Screen parent;
    private final InfinityMod infinity;

    protected InfinityScreen(@NonNull final Screen parent) {
        super(Text.of("Infinity"));

        this.parent = parent;
        this.infinity = InfinityLoader.getMod();
    }

    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY,
        final float delta) {
        this.renderBackground(matrices);
        DrawableHelper.drawCenteredText(matrices, this.textRenderer,
            infinity.getMetadata().getName(), width / 2, 15,
            Color.WHITE.getRGB());

        super.render(matrices, mouseX, mouseY, delta);
    }
}
