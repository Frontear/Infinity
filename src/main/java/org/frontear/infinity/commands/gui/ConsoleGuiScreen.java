package org.frontear.infinity.commands.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatComponentText;
import org.frontear.wrapper.IMinecraftWrapper;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class ConsoleGuiScreen extends GuiScreen {
	private final ChatComponentText prefix;
	private GuiTextField field;

	public ConsoleGuiScreen(ChatComponentText prefix) {
		this.prefix = prefix;
	}

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		field.drawTextBox();
		field.setFocused(true);
	}

	@Override protected void keyTyped(char typedChar, int keyCode) throws IOException {
		field.textboxKeyTyped(typedChar, keyCode);

		super.keyTyped(typedChar, keyCode);
	}

	@Override public void initGui() {
		Keyboard.enableRepeatEvents(true);
		this.field = new ConsoleTextField(fontRendererObj, this.width / 2 - 110, 2, 220, 12, this);
	}

	@Override public void updateScreen() {
		field.updateCursorCounter();
	}

	@Override public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	void process(String text) {
		IMinecraftWrapper.getMinecraft().getChat()
				.printChatMessage(new ChatComponentText("").appendSibling(prefix).appendText(text));
	}
}
