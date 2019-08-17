package org.frontear.infinity.events.render;

import net.minecraftforge.fml.common.eventhandler.Event;

import java.awt.*;

public class FontEvent extends Event {
	private String text;
	private float x, y;
	private int color;
	private boolean dropShadow;

	public FontEvent(String text, float x, float y, int color, boolean dropShadow) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.color = color;
		this.dropShadow = dropShadow;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color.getRGB();
	}

	public boolean dropShadow() {
		return dropShadow;
	}

	public void setDropShadow(boolean dropShadow) {
		this.dropShadow = dropShadow;
	}
}
