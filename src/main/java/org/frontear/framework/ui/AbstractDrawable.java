package org.frontear.framework.ui;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public abstract class AbstractDrawable implements IRenderable {
	private int x, y, width, height;
	private Color color;

	public AbstractDrawable(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	protected abstract void draw(int x, int y, int width, int height);

	@Override public void render() {
		glPushMatrix();
		{
			glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

			glDisable(GL_TEXTURE_2D);
			glDisable(GL_CULL_FACE);
			//glEnable(GL_BLEND);
			{
				draw(x, y, width, height);
			}
			//glDisable(GL_BLEND);
			glEnable(GL_CULL_FACE);
			glEnable(GL_TEXTURE_2D);
		}
		glPopMatrix();
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
