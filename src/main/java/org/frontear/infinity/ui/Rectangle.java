package org.frontear.infinity.ui;

import org.frontear.framework.ui.AbstractDrawable;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public class Rectangle extends AbstractDrawable {
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
}
