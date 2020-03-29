package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.event.state.LoadEvent;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.utils.key.Keyboard;
import java.util.*;
import lombok.*;

@ModuleInfo(bind = Keyboard.KEY_G, friendly = true, category = ModuleCategory.RENDER)
public final class Ghost extends Module {
    private final List<Module> unfriendly;

    public Ghost(final @NonNull InfinityMod infinity) {
        super(infinity);

        this.unfriendly = new ArrayList<>();

        infinity.getExecutor().register(LoadEvent.class, e -> {
            val modules = infinity.getModules().stream();

            modules.filter(x -> !x.isFriendly()).forEach(unfriendly::add);
        });
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
}
