package org.frontear.mixins.impl;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.impl.Ghost;
import org.frontear.infinity.ui.InfinityScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiOptions.class)
public abstract class MixinGuiOptions extends GuiScreen {
    /**
     * Attempts to add a new button for the {@link org.frontear.framework.client.impl.Client} into
     * the {@link GuiScreen#buttonList}
     *
     * @author Frontear
     * @reason {@link InfinityScreen}
     */
    @Inject(method = "initGui",
        at = @At("TAIL"))
    private void initGui(final CallbackInfo info) {
        if (!Infinity.inst().getModules().get(Ghost.class).isActive()) {
            this.buttonList
                .add(new GuiButton(-1, this.width / 2 + 5, this.height / 6 + 24 - 6, 150, 20,
                    "${Infinity.inst().getInfo().getName()} settings..."));
        }
    }

    /**
     * This is called when the {@link org.lwjgl.input.Mouse} sends some input
     *
     * @param button The button that is currently being hovered upon by the {@link
     *               org.lwjgl.input.Mouse}
     *
     * @author Frontear
     * @reason {@link InfinityScreen}
     */
    @Inject(method = "actionPerformed",
        at = @At("TAIL"))
    private void actionPerformed(final GuiButton button, final CallbackInfo info) {
        if (button.enabled) {
            if (button.id == -1) {
                this.mc.gameSettings.saveOptions();
                this.mc.displayGuiScreen(new InfinityScreen(this));
            }
        }
    }
}
