package org.frontear.infinity.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

/**
 * This will only exist for testing the UI, and for nothing else
 */
public class UITestScreen extends GuiScreen {
	private final GuiScreen lastScreen;

	public UITestScreen(GuiScreen lastScreen) {
		this.lastScreen = lastScreen;
	}

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		// gl stuff here

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
	}
}
