package org.frontear.infinity.ui;

import org.frontear.framework.ui.Drawable;
import org.frontear.framework.ui.impl.Rectangle;
import org.frontear.wrapper.IMinecraftWrapper;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScalef;

public abstract class Button extends Drawable {
	protected static final IMinecraftWrapper mc = IMinecraftWrapper.getMinecraft();
	private final Rectangle rectangle;
	private String text;

	public Button(String text, int x, int y, int width, int height, Color color) {
		this.rectangle = new Rectangle(x, y, width, height, color);
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override public void setPosition(int x, int y) {
		rectangle.setPosition(x, y);
	}

	@Override public void draw(float scale) {
		rectangle.draw(scale);
		glScalef(scale, scale, 0);
		{
			mc.getFontRenderer().drawString(text, ((rectangle.getX() + (rectangle.getX() + rectangle.getWidth())) - mc
					.getFontRenderer().getStringWidth(text)) / 2, (((rectangle.getY() + (rectangle.getY() + rectangle
					.getHeight())) - (mc.getFontRenderer().FONT_HEIGHT + 1)) / 2), contrast(rectangle.getColor()));
		}
		glScalef(1 / scale, 1 / scale, 0);
	}

	// https://stackoverflow.com/a/13030061/9091276
	private int contrast(Color color) {
		double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
		return (y >= 128 ? Color.BLACK : Color.WHITE).getRGB();
	}

	@Override protected void render(int x, int y, int width, int height) {
		throw new UnsupportedOperationException();
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
	}
}