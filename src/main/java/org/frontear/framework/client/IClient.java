package org.frontear.framework.client;

import org.frontear.framework.config.IConfig;
import org.frontear.framework.info.IModInfo;
import org.frontear.framework.logger.ILogger;

public interface IClient {
	IModInfo getModInfo();

	ILogger getLogger();

	IConfig getConfig();
}
