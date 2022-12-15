package io.github.frontear.infinity;

import io.github.frontear.infinity.tweaks.AbstractTweak;
import io.github.frontear.infinity.tweaks.TweakManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfinityMod implements ClientModInitializer {
    // TODO: although i like the static usage instead of instance + getInstance() nonsense, how much exposure to we want to allow to our internals to other classes (mixins)
    private static final TweakManager tweakManager;
    static {
        tweakManager = new TweakManager();
    }

    @Override
    public void onInitializeClient() {} // exists to ensure fabric loads our mixins

    public static boolean isModEnabled(Class<? extends AbstractTweak> type) {
        return tweakManager.find(type).isEnabled();
    }

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(String.format("Infinity/%s", name));
    }
}
