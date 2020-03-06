package com.github.frontear.infinity.modules.impl;

import com.github.frontear.efkolia.api.events.Listener;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.event.state.LoadEvent;
import com.github.frontear.infinity.modules.*;
import java.util.*;
import lombok.*;
import org.lwjgl.glfw.GLFW;

@ModuleInfo(bind = GLFW.GLFW_KEY_G, friendly = true, category = ModuleCategory.RENDER)
public final class Ghost extends Module {
    private final List<Module> unfriendly = new ArrayList<>();

    public Ghost(final @NonNull InfinityMod infinity) {
        super(infinity);
    }

    @Override
    public boolean toggle() {
        val toggled = super.toggle();

        if (toggled) {
            client.updateWindowTitle();
            unfriendly.forEach(Module::toggle);
        }

        return toggled;
    }

    @Listener
    private void onLoad(@NonNull final LoadEvent event) {
        val modules = infinity.getModules().stream();

        modules.filter(x -> !x.isFriendly()).forEach(unfriendly::add);
    }
}
