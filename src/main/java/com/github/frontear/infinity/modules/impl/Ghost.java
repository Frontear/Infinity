package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.Infinity;
import com.github.frontear.infinity.modules.*;
import com.google.common.collect.Sets;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class Ghost extends Module {

    Set<Module> unsafe = Sets.newHashSet();

    public Ghost() {
        super(Keyboard.KEY_G, true, Category.NONE);
    }

    @Override
    public void load(final Module self) {
        this.setBind(self.getBind());
        this.setActive(false);
    }

    @Override
    protected void onToggle(final boolean active) {
        Display.setTitle(active ? "Minecraft ${net.minecraftforge.fml.common.Loader.MC_VERSION}"
            : Infinity.inst()
                .getInfo().getFullname());
        if (active) {
            val stream = Infinity.inst().getModules().getObjects().filter(x -> !x.isSafe())
                .filter(Module::isActive)
                .toArray(Module[]::new);
            logger.debug("Found ${stream.length} unsafe modules");
            for (Module module : stream) {
                module.toggle();
                unsafe.add(module);
            }
        }
        else {
            unsafe.forEach(Module::toggle);
            unsafe.clear();
        }
    }
}
