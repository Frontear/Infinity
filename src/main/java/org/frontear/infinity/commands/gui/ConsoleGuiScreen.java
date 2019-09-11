package org.frontear.infinity.commands.gui;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import org.frontear.infinity.commands.ui.Console;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;

@FieldDefaults(level = AccessLevel.PRIVATE) public final class ConsoleGuiScreen extends GuiScreen {
	Console console;

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		console.draw();
	}

	@Override protected void keyTyped(char typedChar, int keyCode) throws IOException {
		console.textboxKeyTyped(typedChar, keyCode);

		super.keyTyped(typedChar, keyCode);
	}

	@Override public void initGui() {
		val width = 320;
		val height = 180;

		Keyboard.enableRepeatEvents(true);
		if (console == null) {
			this.console = new Console(fontRendererObj, this.width - width - 2, 2, width, height);
		}
		else {
			console.setPosition(this.width - width - 2, 2);
		}
	}

	@Override public void handleMouseInput() throws IOException {
		super.handleMouseInput();

		val i = Mouse.getEventDWheel();
		if (i != 0) {
			console.scroll(Math.max(-1, Math.min(1, i))); // between -1 and 1
		}
	}

	@Override public void updateScreen() {
		console.updateCursorCounter();
	}

	@Override public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	public void print(ChatComponentText text) {
		console.print(text);
	}
}
