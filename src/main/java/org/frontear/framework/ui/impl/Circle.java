package org.frontear.framework.ui.impl;

import org.frontear.framework.ui.Drawable;

import java.awt.*;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;

public final class Circle extends Drawable {
	private final int factor;
	private int radius;

	public Circle(int x, int y, int radius, int factor, Color color) {
		super(x, y, 0, 0, color);

		this.radius = radius;
		this.factor = factor;
	}

	@Deprecated @Override public void setWidth(int width) {
		this.setRadius(width);
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Deprecated @Override public void setHeight(int height) {
		this.setRadius(height);
	}

	// https://stackoverflow.com/a/24843626/9091276
	@Override protected void render(int x, int y, int width, int height) {
		glBegin(GL_TRIANGLE_FAN);
		{
			glVertex2d(x, y);
			for (int i = 0; i <= factor; i++) {
				glVertex2d((x + (radius * cos(i * (PI * 2f) / factor))), (y + (radius * sin(i * (PI * 2f) / factor))));
			}
		}
		glEnd();
	}
}
