package com.github.frontear.mixins.impl;

import com.github.frontear.framework.client.impl.Client;
import com.github.frontear.infinity.Infinity;
import com.github.frontear.infinity.modules.impl.Ghost;
import com.github.frontear.infinity.ui.InfinityScreen;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiOptions.class)
public abstract class MixinGuiOptions extends GuiScreen {
    /**
     * Attempts to add a new button for the {@link Client} into the {@link GuiScreen#buttonList}
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
