package org.frontear.infinity.modules.gui;

import net.minecraft.client.gui.GuiScreen;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.glScalef;

public final class KeyBindScreen extends GuiScreen {
	private final Module module;
	private final GuiScreen parent;

	public KeyBindScreen(Module module, GuiScreen parent) {
		this.module = module;
		this.parent = parent;
	}

	@Override public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();

		final int scale = 2;
		glScalef(scale, scale, 1f);
		{
			this.drawCenteredString(fontRendererObj, "Press any key to bind", (width / 2) / scale, 15 / scale, Color.WHITE
					.getRGB());
		}
		glScalef(1f / scale, 1f / scale, 1f);
	}

	@Override protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_ESCAPE) {
			keyCode = Keyboard.KEY_NONE;
		}

		module.setBind(keyCode);
		mc.displayGuiScreen(parent);
	}
}
