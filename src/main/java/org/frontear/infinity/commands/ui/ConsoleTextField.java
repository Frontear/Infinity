package org.frontear.infinity.commands.ui;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.frontear.framework.graphics.IRenderer;
import org.frontear.framework.graphics.color.ColorFactory;
import org.frontear.framework.graphics.shapes.Rectangle;
import org.frontear.infinity.Infinity;
import org.lwjgl.input.Keyboard;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class ConsoleTextField extends GuiTextField {
    IRenderer renderer;
    Rectangle backing;

    ConsoleTextField(@NonNull final FontRenderer fontrendererObj, final int x, final int y,
        final int par5Width, final int par6Height, @NonNull final IRenderer renderer) {
        super(-1, fontrendererObj, 0, 0, par5Width,
            par6Height);

        this.setCanLoseFocus(false);
        this.setMaxStringLength(par5Width / 6); // majority of the character widths are 6
        this.setEnableBackgroundDrawing(false);

        this.renderer = renderer;
        this.backing = new Rectangle(x, y, par5Width, par6Height, ColorFactory.from(0, 0, 0, 127));
        backing.setRenderer(renderer);

        this.setPosition(x, y);
    }

    @Override
    public boolean textboxKeyTyped(final char p_146201_1_, final int p_146201_2_) {
        val text = getText()
            .trim(); // removes all empty spaces from ends and beginnings, they are unnecessary and can cause problems
        if (p_146201_2_ == Keyboard.KEY_RETURN && !text.isEmpty()) {
            Infinity.inst().getCommands().processMessage(text);
            this.setText("");

            return true;
        }

        return super.textboxKeyTyped(p_146201_1_, p_146201_2_);
    }

    @Override
    public void drawTextBox() {
        backing.render();
        renderer.escapeContext(super::drawTextBox);
    }

    void setPosition(final int x, final int y) {
        backing.setX(x);
        backing.setY(y);

        // see GuiTextField#drawTextBox
        this.xPosition = x + 4 / 2;
        this.yPosition = y + (backing.getHeight() - 8) / 2;
    }
}
