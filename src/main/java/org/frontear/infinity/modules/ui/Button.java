package org.frontear.infinity.modules.ui;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.minecraft.client.Minecraft;
import org.frontear.framework.ui.Drawable;
import org.frontear.framework.ui.impl.Rectangle;

import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public abstract class Button extends Drawable {
	protected static final Minecraft mc = Minecraft.getMinecraft();
	Rectangle rectangle;
	@NonFinal String text;
	@NonFinal int color;

	protected Button(@NonNull String text, int x, int y, int width, int height, Color color) {
		this.rectangle = new Rectangle(x, y, width, height, color);
		this.text = text;
		this.color = contrast(color);
	}

	// https://stackoverflow.com/a/13030061/9091276
	private int contrast(Color color) {
		val y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000f;
		return (y >= 128 ? Color.BLACK : Color.WHITE).getRGB();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override public int getX() {
		return rectangle.getX();
	}

	@Override public int getY() {
		return rectangle.getY();
	}

	@Override public int getWidth() {
		return rectangle.getWidth();
	}

	@Override public void setWidth(int width) {
		rectangle.setWidth(width);
	}

	@Override public int getHeight() {
		return rectangle.getHeight();
	}

	@Override public void setHeight(int height) {
		rectangle.setHeight(height);
	}

	@Override public void setColor(Color color) {
		rectangle.setColor(color);
		this.color = contrast(color);
	}

	@Override public void draw() {
		rectangle.draw();
		mc.fontRendererObj
				.drawString(text, ((rectangle.getX() + (rectangle.getX() + rectangle.getWidth())) - mc.fontRendererObj
						.getStringWidth(text)) / 2, (((rectangle.getY() + (rectangle.getY() + rectangle
						.getHeight())) - (mc.fontRendererObj.FONT_HEIGHT + 1)) / 2), color);
	}

	@Override protected void render(int x, int y, int width, int height) {
		throw new UnsupportedOperationException();
	}

	@Override public void setPosition(int x, int y) {
		rectangle.setPosition(x, y);
	}
}