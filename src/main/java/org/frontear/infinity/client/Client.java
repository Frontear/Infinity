package org.frontear.infinity.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import org.frontear.framework.client.IClient;
import org.frontear.framework.config.IConfig;
import org.frontear.framework.info.IModInfo;
import org.frontear.framework.logger.ILogger;
import org.frontear.infinity.config.Config;
import org.frontear.infinity.info.ModInfo;
import org.frontear.infinity.logger.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Client implements IClient {
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
		this.config = new Config(new File(Minecraft.getMinecraft().mcDataDir, info.getName().toLowerCase() + ".json"));
	}

	@Override public IModInfo getModInfo() {
		return info;
	}

	@Override public ILogger getLogger() {
		return logger;
	}

	@Override public IConfig getConfig() {
		return config;
	}
}
