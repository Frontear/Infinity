package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.Module;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.utils.keyboard.Keyboard;
import java.util.*;
import lombok.*;

@ModuleInfo(bind = Keyboard.KEY_G, friendly = true, category = ModuleCategory.NONE)
public final class Ghost extends Module {
    private final List<Module> unfriendly;
    private boolean added;

    public Ghost(@NonNull final InfinityMod infinity) {
        super(infinity);

        this.unfriendly = new ArrayList<>();
        this.added = false;
    }

    @Override
    public boolean toggle() {
        val toggled = super.toggle();

        if (!added) {
            infinity.getModules().stream()
                .filter(x -> x.getCategory() != ModuleCategory.NONE && !x.isFriendly())
                .forEach(unfriendly::add);

            added = true;
        }

        if (toggled) {
            client.updateWindowTitle();
            unfriendly.forEach(Module::toggle);
        }

        return toggled;
    }
}
