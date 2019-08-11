package org.frontear.framework.ui;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Represents an object that is to be drawn to the screen via OpenGL
 */
public abstract class AbstractDrawable {
	private int x, y, width, height;
	private Color color;

	/**
	 * The constructor for a {@link AbstractDrawable} object
	 *
	 * @param x      The x-coordinate of the object
	 * @param y      The y-coordinate of the object
	 * @param width  The width of the object
	 * @param height The height of the object
	 * @param color  The color of the object
	 */
	public AbstractDrawable(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	/**
	 * Sets the position of the object in an xy plane
	 *
	 * @param x The new x-coordinate of the object
	 * @param y The new y-coordinate of the object
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the width of the object
	 *
	 * @param width The new width of the object
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets the height of the object
	 *
	 * @param height The new height of the object
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Sets the color of the object
	 *
	 * @param color The new color of the object
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Draws the object in an OpenGL context. Color is automatically applied. Additionally, {@link
	 * org.lwjgl.opengl.GL11#GL_TEXTURE_2D) and {@link org.lwjgl.opengl.GL11#GL_CULL_FACE} are automatically disabled
	 */
	public void draw() {
		glPushMatrix();
		{
			glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

			glDisable(GL_TEXTURE_2D);
			glDisable(GL_CULL_FACE);
			//glEnable(GL_BLEND);
			{
				render(x, y, width, height);
			}
			//glDisable(GL_BLEND);
			glEnable(GL_CULL_FACE);
			glEnable(GL_TEXTURE_2D);
		}
		glPopMatrix();
	}

	protected abstract void render(int x, int y, int width, int height);
}
