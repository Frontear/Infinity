package com.github.frontear.infinity.modules.gui;

import com.github.frontear.framework.graphics.IRenderer;
import com.github.frontear.framework.graphics.impl.Renderer;
import com.github.frontear.infinity.Infinity;
import com.github.frontear.infinity.modules.ui.*;
import com.google.common.collect.Queues;
import java.util.*;
import lombok.*;
import lombok.var;
import lombok.experimental.*;
import net.minecraft.client.gui.GuiScreen;
import org.frontear.framework.graphics.IRenderer;
import org.frontear.framework.graphics.impl.Renderer;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.ui.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ClickGuiScreen extends GuiScreen {
    Deque<Panel> categoryPanels = Queues.newArrayDeque();
    IRenderer renderer = new Renderer();
    @NonFinal boolean init = false; // prevents initGui from being called more than once

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        renderer.begin();
        {
            categoryPanels.forEach(Panel::render);
        }
        renderer.end();
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        categoryPanels.forEach(x -> x.mouse(mouseX, mouseY, mouseButton));
    }

    @Override
    public void initGui() {
        if (!init) {
            val width = 100;
            val height = 30;
            var x = 5;
            var y = 5;

            Category[] categories = Arrays.stream(Category.values()).filter(z -> z != Category.NONE)
                .toArray(Category[]::new);
            for (val category : categories) {
                val panel = new Panel(x, y, width, height);

                Infinity.inst().getModules().getObjects().filter(z -> z.getCategory() == category)
                    .forEach(z -> panel.add(new Button(z, 0, 0, 0, 0, this)));

                x += width + 5;

                panel.setRenderer(renderer);
                categoryPanels.add(panel);
            }

            init = true;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}