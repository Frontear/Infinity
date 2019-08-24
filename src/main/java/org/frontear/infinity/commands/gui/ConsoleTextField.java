package org.frontear.infinity.commands.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.frontear.framework.ui.impl.Rectangle;
import org.frontear.infinity.Infinity;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ConsoleTextField extends GuiTextField {
	private final Rectangle backing;

	public ConsoleTextField(FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
		super(-1, fontrendererObj, x + 4 / 2, y + (par6Height - 8) / 2, par5Width, par6Height); // see GuiTextField#drawTextBox

		this.setCanLoseFocus(false);
		this.setMaxStringLength(par5Width / 6); // majority of the character widths are 6
		this.setEnableBackgroundDrawing(false);

		this.backing = new Rectangle(x, y, par5Width, par6Height, new Color(0, 0, 0, 127));
	}

	@Override public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
		final String text = getText()
				.trim(); // removes all empty spaces from ends and beginnings, they are unnecessary and can cause problems
		if (p_146201_2_ == Keyboard.KEY_RETURN && !text.isEmpty()) {
			Infinity.inst().getCommands().processMessage(text);
			setText("");

			return true;
		}

		return super.textboxKeyTyped(p_146201_1_, p_146201_2_);
	}

	@Override public void drawTextBox() {
		backing.draw();

		super.drawTextBox();
	}
}
