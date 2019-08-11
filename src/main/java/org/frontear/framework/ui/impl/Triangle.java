package org.frontear.framework.ui.impl;

import org.frontear.framework.ui.AbstractDrawable;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public final class Triangle extends AbstractDrawable {
	public Triangle(int x, int y, int width, int height, float scale, Color color) {
		super(x, y, width, height, scale, color);
	}

	@Override protected void render(int x, int y, int width, int height) {
		glBegin(GL_TRIANGLES);
		{
			glVertex2d(x, y);
			glVertex2d(x - width / 2f, y + height);
			glVertex2d(x + width / 2f, y + height);
		}
		glEnd();
	}
}
