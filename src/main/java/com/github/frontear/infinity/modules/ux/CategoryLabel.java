package com.github.frontear.infinity.modules.ux;

import com.github.frontear.infinity.modules.ModuleCategory;
import java.awt.Color;
import lombok.NonNull;
import net.minecraft.client.gui.DrawableHelper;

public class CategoryLabel extends AbstractLabel {
    private static final int BACKGROUND = new Color(42, 57, 79).getRGB();

    public CategoryLabel(@NonNull final ModuleCategory category, final int x,
        final int y, final int width, final int height) {
        // uppercase for category
        super(Character.toUpperCase(category.name().charAt(0)) + category.name().substring(1), x, y,
            width, height);
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float delta) {
        DrawableHelper.fill(x, y, x + width, y + height, BACKGROUND);
        super.render(mouseX, mouseY, delta);
    }
}
