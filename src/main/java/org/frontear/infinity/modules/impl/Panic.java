package org.frontear.infinity.modules.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class Panic extends Module {


    public Panic() {
        super(Keyboard.KEY_P, true, Category.NONE);
    }

    @Override
    public void toggle() {
        val stream = Infinity.inst().getModules().getObjects().filter(Module::isActive)
            .toArray(Module[]::new);
        logger.debug("Disabling ${stream.length} modules");
        for (Module module : stream) {
            module.toggle();
        }

        super.setActive(false);
    }
}
