package org.frontear.infinity.commands.ui;

import com.google.common.collect.Queues;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ChatComponentText;
import org.frontear.framework.ui.Drawable;
import org.frontear.framework.ui.impl.Rectangle;
import org.frontear.infinity.commands.gui.ConsoleTextField;

import java.awt.*;
import java.util.Deque;

import static org.lwjgl.opengl.GL11.glScalef;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class Console extends Drawable {
	private static final float scale = 0.5f;
	FontRenderer renderer;
	Rectangle backing;
	ConsoleTextField field;
	Deque<String> lines = Queues.newArrayDeque();
	@NonFinal int scrollFactor = 0;

	public Console(@NonNull FontRenderer renderer, int x, int y, int width, int height) {
		val background = new Color(0, 0, 0, 127);

		this.renderer = renderer;
		this.backing = new Rectangle(x, y, width, height, background);
		this.field = new ConsoleTextField(renderer, x, y + height + 1, width, 12, background);
	}

	public void updateCursorCounter() {
		field.updateCursorCounter();
	}

	public void textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
		field.textboxKeyTyped(p_146201_1_, p_146201_2_);
	}

	@Override public void draw() {
		backing.draw();

		var scrollPos = scrollFactor;
		glScalef(scale, scale, 1f);
		{
			var y = backing.getY() + backing.getHeight(); // text starts from the bottom to the top
			for (val line : lines) {
				if (scrollPos-- > 0) {
					continue;
				}

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

	@Override public void setPosition(int x, int y) {
		backing.setPosition(x, y);
		field.setPosition(x, y + backing.getHeight() + 1);
	}

	public void scroll(int factor) {
		this.scrollFactor = Math.min(lines.size(), Math.max(0, scrollFactor + factor)); // between 0 and lines.size
	}

	public void print(@NonNull ChatComponentText text) {
		lines.addFirst(text.getFormattedText());
	}
}
