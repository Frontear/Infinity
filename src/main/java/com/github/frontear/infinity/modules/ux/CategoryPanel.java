package com.github.frontear.infinity.modules.ux;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.ModuleCategory;
import java.util.*;
import lombok.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.util.math.MatrixStack;

public final class CategoryPanel implements Drawable, Element {
    private final InfinityMod infinity;
    private final int x, y, width, height;
    private final Deque<AbstractLabel> buttons;

    public CategoryPanel(@NonNull final InfinityMod infinity,
        @NonNull final ModuleCategory category, final int x, final int y, final int width,
        final int button_height) {
        this.buttons = new ArrayDeque<>();
        this.infinity = infinity;

        buttons.add(new CategoryLabel(category, x, y, width, button_height));

        val modules = infinity.getModules().stream().filter(m -> m.getCategory() == category)
            .iterator();
        while (modules.hasNext()) {
            buttons.add(
                new ModuleLabel(modules.next(), x, y + button_height * buttons.size(), width,
                    button_height));
        }

        this.height = button_height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY,
        final float delta) {
        buttons.forEach(b -> b.render(matrices, mouseX, mouseY, delta));
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        buttons.forEach(b -> b.mouseClicked(mouseX, mouseY, button));

        return true;
    }
}
