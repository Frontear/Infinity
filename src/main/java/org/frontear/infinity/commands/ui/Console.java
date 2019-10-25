package org.frontear.infinity.commands.ui;

import static org.lwjgl.opengl.GL11.glScalef;

import com.google.common.collect.Queues;
import java.awt.Color;
import java.util.Deque;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.val;
import lombok.var;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ChatComponentText;
import org.frontear.framework.graphics.IRenderer;
import org.frontear.framework.graphics.color.ColorFactory;
import org.frontear.framework.graphics.impl.Renderable;
import org.frontear.framework.graphics.shapes.Rectangle;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class Console extends Renderable {
    private static final float scale = 0.5f;
    FontRenderer font;
    Rectangle backing;
    ConsoleTextField field;
    Deque<String> lines = Queues.newArrayDeque();
    @NonFinal int scrollFactor = 0;

    Console(@NonNull FontRenderer font, int x, int y, int width, int height, @NonNull final
    IRenderer renderer) {
        this.font = font;
        this.backing = new Rectangle(x, y, width, height, ColorFactory.from(0, 0, 0, 127));
        backing.setRenderer(renderer);
        this.field = new ConsoleTextField(font, 0, 0, width, 12, renderer);

        this.setPosition(x, y);
        this.setRenderer(renderer);
    }

    void updateCursorCounter() {
        field.updateCursorCounter();
    }

    void textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
        field.textboxKeyTyped(p_146201_1_, p_146201_2_);
    }

    @Override
    public void render() throws IllegalArgumentException { // todo: some cleanup
        backing.render();
        renderer.escapeContext(() -> {
            var scrollPos = scrollFactor;
            glScalef(scale, scale, 1f);
            {
                var y =
                    backing.getY() + backing.getHeight(); // text starts from the bottom to the top
                for (val line : lines) {
                    if (scrollPos-- > 0) {
                        continue;
                    }

                    y -= font.FONT_HEIGHT;
                    font.drawString(line, (backing.getX() + 2) / scale, y / scale,
                        Color.WHITE.getRGB(), false);
                }
            }
            glScalef(1 / scale, 1 / scale, 1f);
        });
        field.drawTextBox();
        field.setFocused(true);
    }

    void setPosition(int x, int y) {
        backing.setX(x);
        backing.setY(y);
        field.setPosition(x, y + backing.getHeight() + 1);
    }

    void scroll(int factor) {
        this.scrollFactor = Math
            .min(lines.size(), Math.max(0, scrollFactor + factor)); // between 0 and lines.size
    }

    void print(@NonNull ChatComponentText text) {
        lines.addFirst(text.getFormattedText());
    }
}
