package com.github.frontear.infinity.modules;

import com.github.frontear.framework.config.impl.Config;
import com.github.frontear.framework.manager.impl.Manager;
import com.github.frontear.infinity.Infinity;
import com.github.frontear.infinity.events.input.KeyEvent;
import com.github.frontear.infinity.events.render.OverlayEvent;
import com.github.frontear.infinity.modules.impl.Ghost;
import com.github.frontear.infinity.ui.renderer.TextPositions;
import java.awt.Color;
import lombok.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class ModuleManager extends Manager<Module> {
    public ModuleManager(@NonNull final Config config) {
        super("com.github.frontear.infinity.modules.impl");

        getObjects().forEach(config::register);
    }

    @SubscribeEvent
    public void onKey(final KeyEvent event) {
        val ghost = get(Ghost.class).isActive();

        if (event.isPressed()) {
            getObjects().filter(x -> x.getBind() == event.getKey())
                .filter(x -> !ghost || x.isSafe())
                .forEach(Module::toggle);
        }
    }

    @SubscribeEvent
    public void onRender(final OverlayEvent event) {
        getObjects().filter(Module::isActive).forEach(x -> Infinity.inst().getTextRenderer()
            .render(TextPositions.RIGHT,
                "${x.getName()} [${org.lwjgl.input.Keyboard.getKeyName(x.getBind())}]", Color.WHITE,
                false, 1f));
    }
}
