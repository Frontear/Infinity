package org.frontear.framework.client.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.frontear.framework.client.IClient;
import org.frontear.framework.config.impl.Config;
import org.frontear.framework.info.impl.ModInfo;
import org.frontear.framework.logger.impl.Logger;
import org.frontear.wrapper.IMinecraftWrapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * An implementation of {@link IClient}
 */
public abstract class Client implements IClient {
	public static final boolean DEBUG = Boolean.parseBoolean(System
			.getProperty("frontear.debug", "false")); // either get value of frontear.debug, or return false if it doesn't exist
	private final ModInfo info;
	private final Logger logger;
	private final Config config;

	/**
	 * This is marked protected to prevent outside construction, and is to specify that this can only be managed through
	 * singletons
	 */
	protected Client() {
		{
			final InputStream mcmod = Objects
					.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("mcmod.info"));
			final Reader reader = new BufferedReader(new InputStreamReader(mcmod, StandardCharsets.UTF_8));
			final JsonObject object = new JsonParser().parse(reader).getAsJsonArray().get(0)
					.getAsJsonObject(); // mcmod.info files are wrapped in a list

			this.info = new ModInfo(object);
		}
		this.logger = new Logger(info.getName());
		this.config = new Config(new File(IMinecraftWrapper.getMinecraft().getDirectory(), info.getName()
				.toLowerCase() + ".json"));
	}

	// todo: error handling if mcmod.info doesn't exist

	/**
	 * Information for this is received from the mcmod.info. As a result, this file MUST exist
	 *
	 * @see IClient#getModInfo()
	 */
	@Override public ModInfo getModInfo() {
		return info;
	}

	/**
	 * The logger is given it's name from {@link ModInfo#getName()}
	 *
	 * @see IClient#getLogger()
	 */
	@Override public Logger getLogger() {
		return logger;
	}

	/**
	 * The config creates a json where the filename is {@link ModInfo#getName()} + .json
	 *
	 * @see IClient#getConfig()
	 */
	@Override public Config getConfig() {
		return config;
	}
}
