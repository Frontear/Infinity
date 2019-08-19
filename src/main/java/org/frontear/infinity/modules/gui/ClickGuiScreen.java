package org.frontear.infinity.modules.gui;

import net.minecraft.client.gui.GuiScreen;
import org.frontear.framework.ui.Drawable;
import org.frontear.infinity.ui.Button;

import java.awt.*;

public class ClickGuiScreen extends GuiScreen {
	private Drawable button;

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		button.setPosition(mouseX, mouseY);
		button.draw();
	}

	@Override public void initGui() {
		this.button = new Button("Hello", 0, 0, 160, 40, Color.DARK_GRAY);
	}
}