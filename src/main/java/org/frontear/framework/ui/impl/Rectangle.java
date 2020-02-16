package org.frontear.framework.ui.impl;

import org.frontear.framework.ui.Drawable;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public final class Rectangle extends Drawable {
	/**
	 * Creates a rectangle {@link Drawable} object, which is rendered via {@link org.lwjgl.opengl.GL11#GL_QUADS}
	 *
	 * @param x      The x-coordinate of the {@link Drawable}
	 * @param y      The y-coordinate of the {@link Drawable}
	 * @param width  The width of the {@link Drawable}
	 * @param height The height of the {@link Drawable}
	 * @param color  The color of the {@link Drawable}
	 */
	public Rectangle(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
	}

	@Override protected void render(int x, int y, int width, int height) {
		glBegin(GL_QUADS);
		{
			glVertex2d(x, y);
			glVertex2d(x + width, y);
			glVertex2d(x + width, y + height);
			glVertex2d(x, y + height);
		}
		glEnd();
	}

	@Override protected void click(int mouseX, int mouseY, boolean hover, int button) {
		throw new UnsupportedOperationException();
	}
}