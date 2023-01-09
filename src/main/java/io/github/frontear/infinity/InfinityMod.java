package io.github.frontear.infinity;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfinityMod implements ClientModInitializer {
    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(String.format("Infinity/%s", name));
    }

    @Override
    public void onInitializeClient() {
    } // exists to ensure fabric loads our mixins
}
