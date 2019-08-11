package org.frontear.infinity.ui.test;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.frontear.framework.ui.AbstractDrawable;
import org.frontear.infinity.ui.Rectangle;

import java.awt.*;

/**
 * This will only exist for testing the UI, and for nothing else
 */
public class UITestScreen extends GuiScreen {
	private final GuiScreen lastScreen;
	private AbstractDrawable drawable;

	public UITestScreen(GuiScreen lastScreen) {
		this.lastScreen = lastScreen;
	}

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		drawable.setPosition(mouseX, mouseY);
		drawable.draw();

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			if (button.id == 200) {
				this.mc.gameSettings.saveOptions();
				this.mc.displayGuiScreen(lastScreen);
			}
		}
	}

	@Override public void initGui() {
		this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 27, I18n.format("gui.done")));
		this.drawable = new Rectangle(10, 10, 100, 100, Color.WHITE);
	}
}
