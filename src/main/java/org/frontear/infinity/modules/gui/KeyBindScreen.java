package org.frontear.infinity.modules.gui;

import static org.lwjgl.opengl.GL11.glScalef;

import java.awt.Color;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class KeyBindScreen extends GuiScreen {
    Module module;
    GuiScreen parent;

    public KeyBindScreen(@NonNull Module module, @NonNull GuiScreen parent) {
        this.module = module;
        this.parent = parent;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        val scale = 2;
        glScalef(scale, scale, 1f);
        {
            this.drawCenteredString(fontRendererObj, "Press any key to bind", (width / 2) / scale,
                15 / scale, Color.WHITE
                    .getRGB());
        }
        glScalef(1f / scale, 1f / scale, 1f);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode != Keyboard.KEY_ESCAPE) {
            module.setBind(keyCode);
        }

        mc.displayGuiScreen(parent);
    }

    @Override
    protected void actionPerformed(@NonNull GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == -1) {
                this.keyTyped('\0', Keyboard.KEY_NONE);
            }
        }
    }

    @Override
    public void initGui() {
        this.buttonList
            .add(new GuiButton(-1, this.width / 2 - 100, this.height / 4, 200, 20, "Disable bind"));
    }
}
