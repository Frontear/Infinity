package com.github.frontear.infinity;

import com.github.frontear.framework.client.impl.Client;
import com.github.frontear.framework.environments.impl.forge.ForgeEnvironment;
import com.github.frontear.infinity.commands.CommandManager;
import com.github.frontear.infinity.events.client.*;
import com.github.frontear.infinity.events.render.OverlayEvent;
import com.github.frontear.infinity.modules.ModuleManager;
import com.github.frontear.infinity.ui.renderer.*;
import java.awt.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.Display;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class Infinity extends Client {
    private static Infinity inst;

    @Getter ModuleManager modules;
    @Getter CommandManager commands;
    @Getter TextRenderer textRenderer; // prefer this renderer only for when you need to render Infinity stuff directly

    private Infinity() {
        super(new ForgeEnvironment());

        MinecraftForge.EVENT_BUS.register(this.modules = new ModuleManager(getConfig()));
        MinecraftForge.EVENT_BUS.register(this.commands = new CommandManager(getInfo()));
        MinecraftForge.EVENT_BUS.register(this.textRenderer = new TextRenderer());
    }

    public static Infinity inst() {
        return inst == null ? inst = new Infinity() : inst;
    }

    @SubscribeEvent
    public void onOverlay(final OverlayEvent event) {
        textRenderer.render(TextPositions.LEFT, getInfo().getName(), Color.WHITE, true, 2.25f);
    }

    @SubscribeEvent
    public void onStartup(final StartupEvent event) {
        getLogger().debug("Hello ${getInfo().getName()}!");
        Display.setTitle(getInfo().getFullname());

        getConfig().load();
    }

    @SubscribeEvent
    public void onShutdown(final ShutdownEvent event) {
        getLogger().debug("Goodbye ${getInfo().getName()}!");

        getConfig().save();
    }
}
