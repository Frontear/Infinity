package com.github.frontear.infinity.modules.ui;

import com.github.frontear.framework.graphics.IRenderer;
import com.github.frontear.framework.graphics.color.ColorFactory;
import com.github.frontear.framework.graphics.impl.Renderable;
import com.github.frontear.framework.graphics.shapes.Rectangle;
import com.github.frontear.infinity.modules.Module;
import com.github.frontear.infinity.modules.gui.KeyBindScreen;
import java.awt.Color;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class Button extends Renderable {
    protected static final Minecraft mc = Minecraft.getMinecraft();
    private static final Color DEFAULT = ColorFactory.from(54, 71, 96);
    private static final Color ACTIVE = ColorFactory.from(7, 152, 252);
    Rectangle rectangle;
    GuiScreen parent;
    @NonFinal Module module;
    @NonFinal int color;

    public Button(@NonNull final Module module, final int x, final int y, final int width,
        final int height, @NonNull final GuiScreen parent) {
        val color = module.isActive() ? ACTIVE : DEFAULT;
        this.rectangle = new Rectangle(x, y, width, height, color);
        this.module = module;
        this.color = contrast(color);
        this.parent = parent;
    }

    // https://stackoverflow.com/a/13030061/9091276
    private int contrast(final Color color) {
        val y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000f;
        return (y >= 128 ? Color.BLACK : Color.WHITE).getRGB();
    }

    @Override
    public int getX() {
        return rectangle.getX();
    }

    @Override
    public int getY() {
        return rectangle.getY();
    }

    @Override
    public int getWidth() {
        return rectangle.getWidth();
    }

    @Override
    public void setWidth(final int width) {
        rectangle.setWidth(width);
    }

    @Override
    public int getHeight() {
        return rectangle.getHeight();
    }

    @Override
    public void setHeight(final int height) {
        rectangle.setHeight(height);
    }

    @Override
    public void setColor(Color color) {
        rectangle.setColor(color);
        this.color = contrast(color);
    }

    @Override
    public void render() throws IllegalArgumentException {
        val text = module.getName();
        this.setColor(module.isActive() ? ACTIVE : DEFAULT);

        rectangle.render();
        renderer.escapeContext(() -> mc.fontRendererObj.drawString(text,
            ((rectangle.getX() + (rectangle.getX() + rectangle.getWidth())) - mc.fontRendererObj
                .getStringWidth(text)) / 2,
            (((rectangle.getY() + (rectangle.getY() + rectangle.getHeight())) - (
                mc.fontRendererObj.FONT_HEIGHT + 1)) / 2), color));
    }

    void click(final int mouseX, final int mouseY, final boolean hover, final int button) {
        if (hover) {
            if (button == 0) {
                module.toggle();
            }
            else if (button == 1) {
                mc.displayGuiScreen(new KeyBindScreen(module, parent));
            }
        }
    }

    void setPosition(final int x, final int y) {
        rectangle.setX(x);
        rectangle.setY(y);
    }

    @Override
    public void setRenderer(@NonNull final IRenderer renderer) {
        super.setRenderer(renderer);
        rectangle.setRenderer(renderer);
    }
}