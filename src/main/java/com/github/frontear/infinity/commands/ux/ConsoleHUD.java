package com.github.frontear.infinity.commands.ux;

import java.awt.Color;
import java.util.*;
import java.util.function.IntSupplier;
import lombok.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public final class ConsoleHUD implements Drawable, Element {
    private static final int BACKGROUND = new Color(0, 0, 0, 127).getRGB();

    private final TextRenderer renderer;
    private final IntSupplier x;
    private final int y, width, height;
    private final List<String> lines;

    private int scroll;

    public ConsoleHUD(@NonNull final MinecraftClient client, @NonNull final IntSupplier x,
        final int y, final int width, final int height) {
        this.renderer = client.textRenderer;
        this.lines = new ArrayList<>();
        this.height = height;
        this.width = width;
        this.scroll = 0;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY, final float delta) {
        var x1 = x.getAsInt();
        DrawableHelper.fill(matrices, x1, y, x1 + width, y + height, BACKGROUND);

        var y2 = y + height - 2;

        x1 += 2;
        for (var i = lines.size() - 1; i >= scroll; --i) {
            val line = lines.get(i);

            y2 -= renderer.fontHeight;
            if (y2 < y) {
                break;
            }

            renderer.draw(matrices, line, x1, y2, Color.WHITE.getRGB());
        }
    }

    @Override
    public boolean mouseScrolled(final double mouseX, final double mouseY, final double amount) {
        // limits between a scroll range of 0, and the max that can be visibly displayed in comparision to the amount of lines in the list
        this.scroll = (int) Math
            .min(Math.max(0, lines.size() - (int) (height / (double) renderer.fontHeight) + 1),
                Math.max(0, scroll + amount));

        return true;
    }

    // todo: optimize and bugfix
    public void println(@NonNull String line) {
        line = line.trim();

        val builder = new StringBuilder();
        if (line.contains(" ")) { // multiple words
            val words = line.split(" ");
            var t_width = 0;

            for (var i = 0; i < words.length; ++i) {
                val n_width = t_width + renderer.getWidth(words[i]);

                if (n_width < width) {
                    builder.append(words[i]).append(" ");
                    t_width = n_width;
                }
                else {
                    lines.add(builder.toString());
                    val n_words = new String[words.length - i];
                    System.arraycopy(words, i, n_words, 0, n_words.length);
                    println(String.join(" ", n_words));
                    return;
                }
            }
        }
        else { // single word
            //noinspection UnnecessaryLocalVariable
            val word = line;
            var t_width = 0;

            for (var i = 0; i < word.length(); ++i) {
                val n_width = t_width + MathHelper.ceil(renderer.getCharWidth(word.charAt(i)));

                if (n_width < width) {
                    builder.append(word.charAt(i));
                    t_width = n_width;
                }
                else {
                    lines.add(builder.toString());
                    println(word.substring(i));
                    return;
                }
            }
        }

        lines.add(builder.toString());
    }
}
