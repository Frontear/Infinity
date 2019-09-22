package org.frontear.infinity.modules.ui;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.frontear.framework.ui.Drawable;
import org.frontear.framework.ui.impl.Rectangle;
import org.frontear.infinity.modules.Module;
import org.frontear.infinity.modules.gui.KeyBindScreen;

import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class Button extends Drawable {
	protected static final Minecraft mc = Minecraft.getMinecraft();
	private static final Color DEFAULT = new Color(54, 71, 96);
	private static final Color ACTIVE = new Color(7, 152, 252);
	Rectangle rectangle;
	GuiScreen parent;
	@NonFinal Module module;
	@NonFinal int color;

	public Button(@NonNull Module module, int x, int y, int width, int height, @NonNull GuiScreen parent) {
		val color = module.isActive() ? ACTIVE : DEFAULT;
		this.rectangle = new Rectangle(x, y, width, height, color);
		this.module = module;
		this.color = contrast(color);
		this.parent = parent;
	}

	// https://stackoverflow.com/a/13030061/9091276
	private int contrast(Color color) {
		val y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000f;
		return (y >= 128 ? Color.BLACK : Color.WHITE).getRGB();
	}

	@Override public int getX() {
		return rectangle.getX();
	}

	@Override public int getY() {
		return rectangle.getY();
	}

	@Override public int getWidth() {
		return rectangle.getWidth();
	}

	@Override public void setWidth(int width) {
		rectangle.setWidth(width);
	}

	@Override public int getHeight() {
		return rectangle.getHeight();
	}

	@Override public void setHeight(int height) {
		rectangle.setHeight(height);
	}

	@Override public void setColor(Color color) {
		rectangle.setColor(color);
		this.color = contrast(color);
	}

	@Override public void draw() {
		val text = module.getName();
		this.setColor(module.isActive() ? ACTIVE : DEFAULT);

		rectangle.draw();
		mc.fontRendererObj
				.drawString(text, ((rectangle.getX() + (rectangle.getX() + rectangle.getWidth())) - mc.fontRendererObj
						.getStringWidth(text)) / 2, (((rectangle.getY() + (rectangle.getY() + rectangle
						.getHeight())) - (mc.fontRendererObj.FONT_HEIGHT + 1)) / 2), color);
	}

	@Override protected void render(int x, int y, int width, int height) {
		throw new UnsupportedOperationException();
	}

	@Override protected void click(int mouseX, int mouseY, boolean hover, int button) {
		if (hover) {
			if (button == 0) {
				module.toggle();
			}
			else if (button == 1) {
				mc.displayGuiScreen(new KeyBindScreen(module, parent));
			}
		}
	}

	@Override public void setPosition(int x, int y) {
		rectangle.setPosition(x, y);
	}
}