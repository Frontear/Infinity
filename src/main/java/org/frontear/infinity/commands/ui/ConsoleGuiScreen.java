package org.frontear.infinity.commands.ui;

import java.io.IOException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.val;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import org.frontear.framework.graphics.IRenderer;
import org.frontear.framework.graphics.impl.Renderer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class ConsoleGuiScreen extends GuiScreen {
    IRenderer renderer = new Renderer();
    @NonFinal Console console;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        renderer.begin();
        {
            console.render();
        }
        renderer.end();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        console.textboxKeyTyped(typedChar, keyCode);

        super.keyTyped(typedChar, keyCode);
    }

    @Override
    public void initGui() {
        val width = 320;
        val height = 180;
        val x = this.width - width - 2;
        val y = 2;

        Keyboard.enableRepeatEvents(true);
        if (console == null) {
            this.console = new Console(fontRendererObj, x, y, width, height,
                renderer);
        }
        else {
            console.setPosition(x, y); // for when the minecraft window height/width change
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        val i = Mouse.getEventDWheel();
        if (i != 0) {
            console.scroll(Math.max(-1, Math.min(1, i))); // between -1 and 1
        }
    }

    @Override
    public void updateScreen() {
        console.updateCursorCounter();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    public void print(ChatComponentText text) {
        console.print(text);
    }
}
