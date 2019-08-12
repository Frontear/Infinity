package org.frontear.framework.ui.impl;

import org.frontear.framework.ui.Drawable;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

// be wary that this does NOT support complex triangles like equilateral, right, or other various types
public final class Triangle extends Drawable {
	public Triangle(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
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
