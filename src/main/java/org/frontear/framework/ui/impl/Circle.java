package org.frontear.framework.ui.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.var;
import org.frontear.framework.ui.Drawable;

import java.awt.*;

import static java.lang.Math.*;
import static org.frontear.framework.utils.opengl.OpenGLState.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class Circle extends Drawable {
	int factor;
	@NonFinal int radius;

	/**
	 * Creates a circle {@link Drawable} object, which is rendered via {@link org.lwjgl.opengl.GL11#GL_TRIANGLE_FAN}
	 *
	 * @param x      The x-coordinate of the {@link Drawable}
	 * @param y      The y-coordinate of the {@link Drawable}
	 * @param radius The radius of the {@link Drawable}
	 * @param factor Defines how smooth the edges will be, higher values are smoother
	 * @param color  The color of the {@link Drawable}
	 */
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
		begin(GL_TRIANGLE_FAN);
		{
			vertex2(x, y);
			for (var i = 0; i <= factor; i++) {
				vertex2((x + (radius * cos(i * (PI * 2f) / factor))), (y + (radius * sin(i * (PI * 2f) / factor))));
			}
		}
		end();
	}

	@Override protected void click(int mouseX, int mouseY, boolean hover, int button) {
		throw new UnsupportedOperationException();
	}
}
