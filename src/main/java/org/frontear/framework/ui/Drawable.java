package org.frontear.framework.ui;

import org.lwjgl.input.Mouse;

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
	 * Draws the {@link Drawable} in an OpenGL context. Color is automatically applied. {@link
	 * org.lwjgl.opengl.GL11#GL_BLEND} is enabled, and {@link org.lwjgl.opengl.GL11#GL_TEXTURE_2D} is disabled
	 */
	public void draw() {
		glPushAttrib(GL_CURRENT_BIT);
		glPushMatrix();
		{
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);

			glEnable(GL_BLEND);
			glDisable(GL_CULL_FACE);
			glDisable(GL_TEXTURE_2D);
			{
				render(x, y, width, height);
			}
			glEnable(GL_TEXTURE_2D);
			glEnable(GL_CULL_FACE);
			glDisable(GL_BLEND);
		}
		glPopMatrix();
		glPopAttrib();
	}

	/**
	 * This is called after a GLContext has been created via {@link Drawable#draw()}
	 *
	 * @param x      The x-coordinate of the {@link Drawable}
	 * @param y      The y-coordinate of the {@link Drawable}
	 * @param width  The width of the {@link Drawable}
	 * @param height The height of the {@link Drawable}
	 */
	protected abstract void render(int x, int y, int width, int height);

	/**
	 * This should be called when {@link Mouse#next()} is available, in order to allow the {@link Drawable} to handle
	 * mouse input, such as clicking or hovering
	 *
	 * @param mouseX The x-coordinate of the {@link Mouse}
	 * @param mouseY The y-coordinate of the {@link Mouse}
	 * @param button The button being pressed by the {@link Mouse}, 0 == LEFT, 1 == RIGHT, else == NONE
	 */
	public void mouse(int mouseX, int mouseY, int button) {
		click(mouseX, mouseY, mouseX >= getX() && mouseY >= getY() && mouseX < getX() + getWidth() && mouseY < getY() + getHeight(), button);
	}

	/**
	 * This is called after {@link Drawable#mouse(int, int, int)} has calculated whether this object is being hovered or
	 * not
	 *
	 * @param mouseX The x-coordinate of the {@link Mouse}
	 * @param mouseY The y-coordinate of the {@link Mouse}
	 * @param hover  If the x and y coordinates are within this {@link Drawable} bounds
	 * @param button The button being pressed by the {@link Mouse}, 0 == LEFT, 1 == RIGHT, else == NONE
	 */
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
