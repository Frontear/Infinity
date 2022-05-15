package com.github.frontear.infinity.modules.ux;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.ModuleCategory;
import java.util.*;
import lombok.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public final class ModuleScreen extends Screen {
    private final Deque<CategoryPanel> panels;
    private final InfinityMod infinity;

    private boolean initialized;

    public ModuleScreen(@NonNull final InfinityMod infinity) {
        super(new LiteralText("ModuleScreen"));

        this.panels = new ArrayDeque<>();
        this.infinity = infinity;
        this.initialized = false;
    }

    @Override
    protected void init() {
        if (!initialized) {
            val width = 100;
            val button_height = 30;
            var x = 2;
            var y = 2;

            val categories = Arrays.stream(ModuleCategory.values())
                .filter(c -> c != ModuleCategory.NONE).iterator();
            while (categories.hasNext()) {
                panels.add(
                    new CategoryPanel(infinity, categories.next(), x, y, width, button_height));
                x += width + 5; // 5 is an offset between panels
            }

            initialized = true;
        }
    }

    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY, final float delta) {
        this.renderBackground(matrices);
        panels.forEach(x -> x.render(mouseX, mouseY, delta));
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        panels.forEach(x -> x.mouseClicked(mouseX, mouseY, button));

        return true;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
