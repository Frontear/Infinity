package org.frontear.framework.client;

import org.frontear.framework.config.IConfig;
import org.frontear.framework.info.IModInfo;
import org.frontear.framework.logger.ILogger;

/**
 * Represents a client object. This will be loaded from {@link org.frontear.ForgeMod}
 */
public interface IClient {
	/**
	 * Contains all the important information loaded from the mcmod.info file
	 * @return {@link IModInfo}
	 */
	IModInfo getModInfo();

	/**
	 * A basic logging system, backed by {@link org.apache.logging.log4j.Logger}
	 * @return {@link ILogger}
	 */
	ILogger getLogger();

	/**
	 * A simple yet very effective configuration system which will contain all your important data
	 * @return {@link IConfig}
	 */
	IConfig getConfig();
}
