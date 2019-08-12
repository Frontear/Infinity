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

public abstract class Client implements IClient {
	private final ModInfo info;
	private final Logger logger;
	private final Config config;

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

	@Override public ModInfo getModInfo() {
		return info;
	}

	@Override public Logger getLogger() {
		return logger;
	}

	@Override public Config getConfig() {
		return config;
	}
}