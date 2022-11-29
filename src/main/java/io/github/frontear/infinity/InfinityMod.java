package io.github.frontear.infinity;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfinityMod implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Infinity");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Hello Fabric world!");
    }
}
