package org.frontear.infinity.commands.ui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.frontear.framework.ui.Drawable;
import org.frontear.framework.ui.impl.Rectangle;
import org.frontear.infinity.commands.gui.ConsoleTextField;

import java.awt.*;

public class Console extends Drawable {
	private final FontRenderer renderer;
	private final Rectangle backing;
	private final GuiTextField field;

	public Console(FontRenderer renderer, int x, int y, int width, int height) {
		final Color background = new Color(0, 0, 0, 127);

		this.renderer = renderer;
		this.backing = new Rectangle(x, y, width, height, background);
		this.field = new ConsoleTextField(renderer, x, y + height + 1, width, 12, background); // +1 temporary, only to distinguish between the two
	}

	@Override public void draw() {
		backing.draw();
		field.drawTextBox();
		field.setFocused(true);
	}

	@Override protected void render(int x, int y, int width, int height) {
		throw new UnsupportedOperationException();
	}

	@Override protected void click(int mouseX, int mouseY, boolean hover, int button) {
		throw new UnsupportedOperationException();
	}

	public void updateCursorCounter() {
		field.updateCursorCounter();
	}

	public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
		return field.textboxKeyTyped(p_146201_1_, p_146201_2_);
	}
}
