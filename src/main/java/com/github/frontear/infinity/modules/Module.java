package com.github.frontear.infinity.modules;

import com.github.frontear.efkolia.api.configuration.IConfigurable;
import com.github.frontear.efkolia.impl.logging.Logger;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.utils.keyboard.Keyboard;
import com.google.gson.annotations.Expose;
import lombok.*;
import net.minecraft.client.MinecraftClient;

public abstract class Module implements IConfigurable<Module> {
    protected final MinecraftClient client;
    protected final InfinityMod infinity;
    protected final Logger logger;

    @Getter private final ModuleCategory category;
    @Getter private final boolean friendly;
    @Expose @Getter @Setter private Keyboard bind;
    @Expose @Getter private boolean active;

    public Module(@NonNull final InfinityMod infinity) {
        val info = getClass().getAnnotation(ModuleInfo.class);

        this.logger = infinity.getLogger(getPropertyName());
        this.client = MinecraftClient.getInstance();
        this.infinity = infinity;

        this.category = info.category();
        this.friendly = info.friendly();
        this.bind = info.bind();
        this.active = false;
    }

    public boolean toggle() {
        val active = isActive();
        setActive(!active);

        return active != isActive();
    }

    public void setActive(final boolean active) {
        val executor = infinity.getExecutor();
        if (active) {
            executor.register(this);
        }
        else {
            executor.unregister(this);
        }

        this.active = active;
    }

    @Override
    public void load(@NonNull final Module value) {
        this.setActive(value.isActive());
        this.setBind(value.getBind());
    }

    @Override
    public String getPropertyName() {
        return getClass().getSimpleName();
    }
}
