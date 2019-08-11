package org.frontear.framework.ui.impl;

import org.frontear.framework.ui.AbstractDrawable;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public final class Rectangle extends AbstractDrawable {
	public Rectangle(int x, int y, int width, int height, float scale, Color color) {
		super(x, y, width, height, scale, color);
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
}
