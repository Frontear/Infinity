package org.frontear.infinity.commands.gui;

import net.minecraft.client.gui.GuiScreen;
import org.frontear.infinity.commands.ui.Console;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class ConsoleGuiScreen extends GuiScreen {
	private Console console;

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		console.draw();
	}

	@Override protected void keyTyped(char typedChar, int keyCode) throws IOException {
		console.textboxKeyTyped(typedChar, keyCode);

		super.keyTyped(typedChar, keyCode);
	}

	@Override public void initGui() {
		final int width = 320, height = 180;

		Keyboard.enableRepeatEvents(true);
		this.console = new Console(fontRendererObj, this.width - width - 2, 2, width, height);
	}

	@Override public void updateScreen() {
		console.updateCursorCounter();
	}

	@Override public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
}
