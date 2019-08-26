package org.frontear.infinity.modules.ui.screen;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import org.frontear.infinity.Infinity;

import java.awt.*;

public final class InfinityScreen extends GuiScreen {
	private final GuiScreen parent;

	public InfinityScreen(GuiScreen parent) {
		this.parent = parent;
	}

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		this.drawCenteredString(fontRendererObj, Infinity.inst().getModInfo().getName(), width / 2, 15, Color.WHITE
				.getRGB());

		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override protected void actionPerformed(GuiButton button) {
		if (button.enabled) {
			switch (button.id) {
				case 200:
					mc.displayGuiScreen(parent);
					break;
				default:
					Infinity.inst().getLogger()
							.warn("Nothing implemented for button \"%s\" id: %d", button.displayString, button.id);
			}
		}
	}

	@Override public void initGui() {
		/*
		this.buttonList.add(new GuiButton(-1, this.width / 2 - 155, this.height / 6 + 24 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-2, this.width / 2 + 5, this.height / 6 + 24 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-3, this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-4, this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-5, this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-6, this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-7, this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-8, this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, "..."));
		*/
		this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 174 - 6, 150, 20, I18n
				.format("gui.done")));
	}
}