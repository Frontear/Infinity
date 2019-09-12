package org.frontear.infinity.modules.ui;

import com.google.common.collect.Sets;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.frontear.framework.ui.Drawable;
import org.frontear.framework.ui.impl.Rectangle;

import java.awt.*;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class Panel extends Drawable {
	private static final int offset = 2;
	Set<Button> buttons = Sets.newLinkedHashSet();
	Rectangle background;

	public Panel(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);

		this.background = new Rectangle(x, y, width, height, new Color(0, 0, 0, 127));
	}

	public void add(Button button) {
		buttons.add(button);

		this.setColor(this.getColor());
		this.setHeight(this.getHeight());
		this.setWidth(this.getWidth());
		this.setPosition(this.getX(), this.getY());
	}

	@Override public void setWidth(int width) {
		buttons.forEach(x -> x.setWidth(width));
		background.setWidth(width + offset * 2);
	}

	@Override public void setHeight(int height) {
		buttons.forEach(x -> x.setHeight(height));
		background.setHeight(((height) * buttons.size()) + offset * 2);
	}

	@Override public void setColor(@NonNull Color color) {
		buttons.forEach(x -> x.setColor(color));
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

	@Override public void setPosition(int x, int y) {
		background.setPosition(x - offset, y - offset);
		for (val button : buttons) {
			button.setPosition(x, y);
			y += button.getHeight();
		}
	}
}
