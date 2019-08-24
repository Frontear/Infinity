package org.frontear.infinity.commands.ui;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatComponentText;
import org.frontear.framework.ui.Drawable;
import org.frontear.framework.ui.impl.Rectangle;
import org.frontear.infinity.commands.gui.ConsoleTextField;

import java.awt.*;
import java.util.List;

import static org.lwjgl.opengl.GL11.glScalef;

public class Console extends Drawable {
	private final FontRenderer renderer;
	private final Rectangle backing;
	private final GuiTextField field;

	private final List<String> lines = Lists.newArrayList();

	public Console(FontRenderer renderer, int x, int y, int width, int height) {
		final Color background = new Color(0, 0, 0, 127);

		this.renderer = renderer;
		this.backing = new Rectangle(x, y, width, height, background);
		this.field = new ConsoleTextField(renderer, x, y + height + 1, width, 12, background); // +1 temporary, only to distinguish between the two
	}

	public void updateCursorCounter() {
		field.updateCursorCounter();
	}

	public void textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
		field.textboxKeyTyped(p_146201_1_, p_146201_2_);
	}

	@Override public void setPosition(int x, int y) {
		backing.setPosition(x, y);
	}

	@Override public void draw() {
		backing.draw();

		final float scale = 0.5f;
		glScalef(scale, scale, 1f);
		{
			int y = backing.getY() + backing.getHeight(); // text starts from the bottom to the top
			for (String line : Lists.reverse(lines)) {
				y -= renderer.FONT_HEIGHT;
				renderer.drawString(line, (backing.getX() + 2) / scale, y / scale, Color.WHITE.getRGB(), false);
			}
		}
		glScalef(1 / scale, 1 / scale, 1f);

		field.drawTextBox();
		field.setFocused(true);
	}

	@Override protected void render(int x, int y, int width, int height) {
		throw new UnsupportedOperationException();
	}

	@Override protected void click(int mouseX, int mouseY, boolean hover, int button) {
		throw new UnsupportedOperationException();
	}

	public void print(ChatComponentText text) {
		lines.add(text.getFormattedText());
	}
}
