package com.github.frontear.infinity.modules.ux;

import com.github.frontear.infinity.modules.ModuleCategory;
import java.awt.Color;
import lombok.NonNull;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public final class CategoryLabel extends AbstractLabel {
    private static final int BACKGROUND = new Color(42, 57, 79).getRGB();

    public CategoryLabel(@NonNull final ModuleCategory category, final int x,
        final int y, final int width, final int height) {
        // uppercase for category
        super(Character.toUpperCase(category.name().charAt(0)) + category.name().substring(1), x, y,
            width, height);
    }

    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY,
        final float delta) {
        DrawableHelper.fill(matrices, x, y, x + width, y + height, BACKGROUND);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
