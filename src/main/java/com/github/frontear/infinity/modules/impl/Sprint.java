package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.events.entity.UpdateEvent;
import com.github.frontear.infinity.modules.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public final class Sprint extends Module {
    public Sprint() {
        super(Keyboard.KEY_LSHIFT, true, Category.PLAYER);
    }

    @SubscribeEvent
    public void onUpdate(final UpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayerSP && event.isPost()
            && !((EntityPlayerSP) event.getEntity())
            .isPotionActive(Potion.blindness)) {
            event.getEntity().setSprinting(true);
        }
    }
}
