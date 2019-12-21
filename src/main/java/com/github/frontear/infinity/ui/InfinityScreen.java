package com.github.frontear.infinity.ui;

import com.github.frontear.infinity.Infinity;
import com.github.frontear.infinity.modules.impl.ClickGui;
import java.awt.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.impl.ClickGui;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class InfinityScreen extends GuiScreen {
    GuiScreen parent;

    public InfinityScreen(@NonNull final GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(fontRendererObj, Infinity.inst().getInfo().getName(), width / 2, 15,
            Color.WHITE
                .getRGB());

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(@NonNull final GuiButton button) {
        if (button.enabled) {
            switch (button.id) {
                case -1:
                    Infinity.inst().getModules().get(ClickGui.class).toggle();
                    break;
                case 200:
                    mc.displayGuiScreen(parent);
                    break;
                default:
                    Infinity.inst().getLogger()
                        .warn(
                            "Nothing implemented for button \"${button.displayString}\" id: ${button.id}");
            }
        }
    }

    @Override
    public void initGui() {
        this.buttonList.add(
            new GuiButton(-1, this.width / 2 - 155, this.height / 6 + 24 - 6, 150, 20, "ClickGUI"));
		/*
		this.buttonList.add(new GuiButton(-2, this.width / 2 + 5, this.height / 6 + 24 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-3, this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-4, this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-5, this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-6, this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-7, this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, "..."));
		this.buttonList.add(new GuiButton(-8, this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, "..."));
		*/
        this.buttonList
            .add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 174 - 6, 150, 20, I18n
                .format("gui.done")));
    }
}
