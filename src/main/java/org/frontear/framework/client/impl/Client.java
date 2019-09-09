package org.frontear.framework.client.impl;

import com.google.gson.*;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.frontear.framework.client.IClient;
import org.frontear.framework.config.impl.Config;
import org.frontear.framework.environment.ModEnvironment;
import org.frontear.framework.info.impl.ModInfo;
import org.frontear.framework.logger.impl.Logger;
import org.frontear.framework.utils.time.Timer;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipFile;

/**
 * An implementation of {@link IClient}
 */
public abstract class Client implements IClient {
	/**
	 * Represents whether <i>-Dfrontear.debug=true</i> is passed as a JVM argument
	 */
	public static final boolean DEBUG = Boolean.parseBoolean(System
			.getProperty("frontear.debug", "false")); // either get value of frontear.debug, or return false if it doesn't exist
	/**
	 * Represents the time since the {@link Client} first loaded (immediate call to the constructor)
	 */
	public static final Timer UPTIME = new Timer();
	@Getter private final ModInfo info;
	@Getter private final Logger logger;
	@Getter private final Config config;

	/**
	 * This is marked protected to prevent outside construction, and is to specify that this can only be managed through
	 * singletons
	 */
	protected Client() {
		UPTIME.reset(); // intentional, as we want to only know exactly how long it has been since client started to load

		this.info = this.construct();
		this.logger = new Logger(info.getName());
		this.config = new Config(new File(".", info.getName().toLowerCase() + ".json"));
	}

	/*
	This mainly exists due to the Fabric API loading classes but not resources until later, causing discrepancies with resources attempting to be loaded
	 */
	private ModInfo construct() {
		JsonObject info;
		try {
			final ZipFile jar = new ZipFile(new File(StringUtils
					.substringBetween(this.getClass().getProtectionDomain().getCodeSource().getLocation()
							.getPath(), "file:", "!")));
			final InputStream stream = jar.getInputStream(jar.getEntry(ModEnvironment.getInfoJsonFilename()));
			final JsonElement element = new JsonParser()
					.parse(new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)));
			info = ModEnvironment.getInfoJsonObject(element);
		}
		catch (NullPointerException | IOException e) {
			e.printStackTrace();
			{
				info = new JsonObject();
				info.addProperty("name", "null");
				info.addProperty("version", "null");
				info.add(ModEnvironment.getAuthorProperty(), new JsonArray());
			}
		}
		return new ModInfo(info);
	}
}
