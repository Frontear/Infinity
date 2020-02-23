package com.github.frontear.framework.client;

import com.github.frontear.MinecraftMod;
import com.github.frontear.framework.config.IConfig;
import com.github.frontear.framework.info.IModInfo;
import com.github.frontear.framework.logger.ILogger;

/**
 * Represents a client object. This will be loaded from {@link MinecraftMod}
 */
public interface IClient {
    /**
     * Contains all the important information loaded from the specified json file
     *
     * @return {@link IModInfo}
     */
    IModInfo getInfo();

    /**
     * A basic logging system, backed by {@link org.apache.logging.log4j.Logger}
     *
     * @return {@link ILogger}
     */
    ILogger getLogger();

    /**
     * A simple yet very effective configuration system which will contain all your important data
     *
     * @return {@link IConfig}
     */
    IConfig getConfig();
}
