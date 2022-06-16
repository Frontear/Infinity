package com.github.frontear.infinity.commands.ux;

import java.awt.Color;
import java.util.*;
import java.util.function.IntSupplier;
import lombok.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class ConsoleField extends TextFieldWidget {
    private static final int BACKGROUND = new Color(0, 0, 0, 127).getRGB();

    private final IntSupplier x_supplier;
    private final int x_shift, y_shift;
    private final List<String> lines;

    private int factor;

    public ConsoleField(@NonNull final MinecraftClient client,
        @NonNull final IntSupplier x_supplier, final int y_origin, final int width,
        @NonNull final Text message) {
        super(client.textRenderer, -1, y_origin + 2, width, 12, message);

        // TextFieldWidget makes dimensional shifts if no border is set. See #renderButton(int, int, float)
        this.x_shift = 4 / 2;
        this.y_shift = (height - 8) / 2;

        super.setMaxLength(256);
        super.setDrawsBackground(false);
        super.y += y_shift;

        this.x_supplier = x_supplier;

        this.lines = new ArrayList<>();
        this.factor = 0;
    }

    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY,
        final float delta) {
        this.x = x_supplier.getAsInt() + x_shift;

        fill(matrices, this.x - x_shift, y - y_shift, this.x - x_shift + this.width,
            y - y_shift + this.height, BACKGROUND);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(final int keyCode, final int scanCode, final int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_UP) {
            factor += 1;
        }
        else if (keyCode == GLFW.GLFW_KEY_DOWN) {
            factor -= 1;
        }
        else {
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        if (lines.size() > 0) {
            this.factor = Math.min(lines.size() - 1, Math.max(-1, factor));
            setText(factor == -1 ? ""
                : lines.get(lines.size() - 1 - factor)); // traverse the list backwards
        }

        return true;
    }

    public String clear() {
        val text = getText().trim();

        lines.add(text);
        super.setText("");

        return text;
    }
}
