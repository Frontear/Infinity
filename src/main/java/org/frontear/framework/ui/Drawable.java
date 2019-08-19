package org.frontear.framework.ui;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * Represents an object that is to be drawn to the screen via OpenGL
 */
public abstract class Drawable {
	private int x, y, width, height;
	private Color color;

	/**
	 * An empty constructor for a {@link Drawable} object
	 */
	public Drawable() {
		this(0, 0, 0, 0, null);
	}

	/**
	 * The constructor for a {@link Drawable} object
	 *
	 * @param x      The x-coordinate of the {@link Drawable}
	 * @param y      The y-coordinate of the {@link Drawable}
	 * @param width  The width of the {@link Drawable}
	 * @param height The height of the {@link Drawable}
	 * @param color  The color of the {@link Drawable}
	 */
	public Drawable(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
	}

	/**
	 * Sets the position of the {@link Drawable} in an xy plane
	 *
	 * @param x The new x-coordinate of the {@link Drawable}
	 * @param y The new y-coordinate of the {@link Drawable}
	 */
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Draws the {@link Drawable} in an OpenGL context. Color and scaling are automatically applied. Additionally,
	 * {@link org.lwjgl.opengl.GL11#GL_TEXTURE_2D} and {@link org.lwjgl.opengl.GL11#GL_CULL_FACE} are automatically
	 * disabled
	 */
	public final void draw() {
		this.draw(1f);
	}

	/**
	 * Draws the {@link Drawable} in an OpenGL context. Color and scaling are automatically applied. Additionally,
	 * {@link org.lwjgl.opengl.GL11#GL_TEXTURE_2D} and {@link org.lwjgl.opengl.GL11#GL_CULL_FACE} are automatically
	 * disabled
	 *
	 * @param scale The scaling of the {@link Drawable}
	 */
	public void draw(float scale) {
		scale = 1f; // todo: fix scaling

		glPushAttrib(GL_CURRENT_BIT);
		glPushMatrix();
		{
			glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

			glDisable(GL_TEXTURE_2D);
			glDisable(GL_CULL_FACE);
			//glEnable(GL_BLEND);

			glScalef(scale, scale, 0);
			{
				render(x, y, width, height);
			}
			glScalef(1 / scale, 1 / scale, 0);

			//glDisable(GL_BLEND);
			glEnable(GL_CULL_FACE);
			glEnable(GL_TEXTURE_2D);
		}
		glPopMatrix();
		glPopAttrib();
	}

	protected abstract void render(int x, int y, int width, int height);

	public void mouse(int mouseX, int mouseY, int button) {
		click(mouseX, mouseY, mouseX >= getX() && mouseY >= getY() && mouseX < getX() + getWidth() && mouseY < getY() + getHeight(), button);
	}

	protected abstract void click(int mouseX, int mouseY, boolean hover, int button);

	/**
	 * @return The x-coordinate of this {@link Drawable}
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return The y-coordinate of this {@link Drawable}
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return The width of this {@link Drawable}
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width of the {@link Drawable}
	 *
	 * @param width The new width of the {@link Drawable}
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return The height of this {@link Drawable}
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height of the {@link Drawable}
	 *
	 * @param height The new height of the {@link Drawable}
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the {@link Drawable}
	 *
	 * @param color The new color of the {@link Drawable}
	 */
	public void setColor(Color color) {
		this.color = color;
	}
}
