package io.github.frontear.infinity;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InfinityMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Infinity");

    @Override
    public void onInitialize() {
        LOGGER.info("Hello from Fabric!");
    }
}
