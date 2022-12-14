package io.github.frontear.infinity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfinityMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {} // exists to ensure fabric loads our mixins

    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(String.format("Infinity/%s", name));
    }
}
