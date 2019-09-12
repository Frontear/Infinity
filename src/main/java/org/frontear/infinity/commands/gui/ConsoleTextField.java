package org.frontear.infinity.commands.gui;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.frontear.framework.ui.impl.Rectangle;
import org.frontear.infinity.Infinity;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class ConsoleTextField extends GuiTextField {
	Rectangle backing;

	public ConsoleTextField(@NonNull FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height, @NonNull Color color) {
		super(-1, fontrendererObj, x + 4 / 2, y + (par6Height - 8) / 2, par5Width, par6Height); // see GuiTextField#drawTextBox

		this.setCanLoseFocus(false);
		this.setMaxStringLength(par5Width / 6); // majority of the character widths are 6
		this.setEnableBackgroundDrawing(false);

		this.backing = new Rectangle(x, y, par5Width, par6Height, color);
	}

	@Override public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
		val text = getText()
				.trim(); // removes all empty spaces from ends and beginnings, they are unnecessary and can cause problems
		if (p_146201_2_ == Keyboard.KEY_RETURN && !text.isEmpty()) {
			Infinity.inst().getCommands().processMessage(text);
			this.setText("");

			return true;
		}

		return super.textboxKeyTyped(p_146201_1_, p_146201_2_);
	}

	@Override public void drawTextBox() {
		backing.draw();

		super.drawTextBox();
	}

	public void setPosition(int x, int y) {
		backing.setPosition(x, y);
		this.xPosition = x + 4 / 2;
		this.yPosition = y + (backing.getHeight() - 8) / 2;
	}
}
