package org.frontear.infinity.modules.ui;

import com.google.common.collect.Sets;
import org.frontear.framework.ui.Drawable;
import org.frontear.framework.ui.impl.Rectangle;

import java.awt.*;
import java.util.Set;

public final class Panel extends Drawable {
	private static final int offset = 2;
	private final Set<org.frontear.infinity.modules.ui.Button> buttons = Sets.newLinkedHashSet();
	private final Rectangle background;

	public Panel(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);

		this.background = new Rectangle(x, y, width, height, new Color(0, 0, 0, 127));
	}

	public void add(org.frontear.infinity.modules.ui.Button button) {
		buttons.add(button);

		this.setColor(this.getColor());
		this.setHeight(this.getHeight());
		this.setWidth(this.getWidth());
		this.setPosition(this.getX(), this.getY());
	}

	@Override public void setPosition(int x, int y) {
		background.setPosition(x - offset, y - offset);
		for (org.frontear.infinity.modules.ui.Button button : buttons) {
			button.setPosition(x, y);
			y += button.getHeight();
		}
	}

	@Override public void draw() {
		background.draw();
		buttons.forEach(Button::draw);
	}

	@Override protected void render(int x, int y, int width, int height) {
		throw new UnsupportedOperationException();
	}

	@Override public void mouse(int mouseX, int mouseY, int button) {
		buttons.forEach(x -> x.mouse(mouseX, mouseY, button));
	}

	@Override protected void click(int mouseX, int mouseY, boolean hover, int button) {
		throw new UnsupportedOperationException();
	}

	@Override public void setWidth(int width) {
		buttons.forEach(x -> x.setWidth(width));
		background.setWidth(width + offset * 2);
	}

	@Override public void setHeight(int height) {
		buttons.forEach(x -> x.setHeight(height));
		background.setHeight(((height) * buttons.size()) + offset * 2);
	}

	@Override public void setColor(Color color) {
		buttons.forEach(x -> x.setColor(color));
	}
}
