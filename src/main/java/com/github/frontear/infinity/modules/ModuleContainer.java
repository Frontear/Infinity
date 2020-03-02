package com.github.frontear.infinity.modules;

import com.github.frontear.efkolia.impl.container.Container;
import com.github.frontear.infinity.InfinityMod;
import lombok.NonNull;

public final class ModuleContainer extends Container<Module> {
    public ModuleContainer(@NonNull final InfinityMod mod) {
        super(mod, ModuleContainer.class.getPackage().getName() + ".impl", mod);

        stream().forEach(mod.getConfig()::register);
    }
}
