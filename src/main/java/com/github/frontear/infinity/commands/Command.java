package com.github.frontear.infinity.commands;

import com.github.frontear.efkolia.impl.configuration.Configurable;
import com.github.frontear.efkolia.impl.logging.Logger;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.internal.NotNull;
import lombok.*;
import net.minecraft.client.MinecraftClient;

public abstract class Command extends Configurable<Command> {
    protected final Logger logger;
    protected final MinecraftClient client;
    protected final InfinityMod infinity;

    @Getter
    private final String desc;
    @Getter
    private final int args;

    public Command(@NonNull final InfinityMod infinity) {
        val info = getClass().getAnnotation(CommandInfo.class);

        this.logger = infinity.getLogger(getPropertyName());
        this.client = MinecraftClient.getInstance();
        this.infinity = infinity;

        this.desc = info.desc();
        this.args = info.args();
    }

    @Override
    public void load(final Command value) {
    }

    public abstract void process(@NotNull final String[] args) throws Exception;

    public abstract String getUsage();

    public void println(@NonNull final String line) {
        infinity.getCommands().screen.hud
            .println(this.getPropertyName().toLowerCase() + ": " + line);
    }
}
