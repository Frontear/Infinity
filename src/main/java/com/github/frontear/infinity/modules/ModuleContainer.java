package com.github.frontear.infinity.modules;

import com.github.frontear.efkolia.impl.container.Container;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.event.KeyEvent;
import lombok.NonNull;

public final class ModuleContainer extends Container<Module> {
    public ModuleContainer(@NonNull final InfinityMod mod) {
        super(mod, ModuleContainer.class.getPackage().getName() + ".impl", mod);

        stream().forEach(mod.getConfig()::register);

        mod.getExecutor().register(KeyEvent.class, e -> {
            if (e.isPressed()) {
                stream().filter(x -> x.getBind() == e.getKey()).forEach(Module::toggle);
            }
        });
    }
}
