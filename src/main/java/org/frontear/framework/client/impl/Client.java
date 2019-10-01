package org.frontear.framework.client.impl;

import com.google.gson.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.frontear.framework.client.IClient;
import org.frontear.framework.config.impl.Config;
import org.frontear.framework.environment.ModEnvironment;
import org.frontear.framework.info.impl.ModInfo;
import org.frontear.framework.logger.impl.Logger;
import org.frontear.framework.utils.time.Timer;

import java.io.File;
import java.util.zip.ZipFile;

/**
 * An implementation of {@link IClient}
 */
@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public abstract class Client implements IClient {
	/**
	 * Represents whether <i>-Dfrontear.debug=true</i> is passed as a JVM argument
	 */
	public static final boolean DEBUG = Boolean.parseBoolean(System
			.getProperty("frontear.debug", "false")); // either get value of frontear.debug, or return false if it doesn't exist
	/**
	 * Represents the time since the {@link Client} first loaded (immediate call to the constructor)
	 */
	public static final Timer UPTIME = new Timer();

	/**
	 * This is the expected/recommended working directory for the client. Should always point to the minecraft data folder
	 */
	public static final String WORKING_DIRECTORY = DEBUG ? System.getProperty("java.io.tmpdir") : System
			.getProperty("user.dir");
	@Getter ModInfo info;
	@Getter Logger logger;
	@Getter Config config;

	/**
	 * This is marked protected to prevent outside construction, and is to specify that this can only be managed through
	 * singletons
	 */
	protected Client() {
		UPTIME.reset(); // intentional, as we want to only know exactly how long it has been since client started to load

		this.info = this.construct();
		this.logger = new Logger(info.getName());
		val file = new File(WORKING_DIRECTORY, "${info.getName().toLowerCase()}.json");
		if (DEBUG) {
			//noinspection ResultOfMethodCallIgnored
			Runtime.getRuntime().addShutdownHook(new Thread(file::delete)); // see: deleteOnExit is evil
		}
		this.config = new Config(file);

		if (DEBUG) {
			logger.debug("Starting %s in debug mode (no changes will be saved)", this.getSimpleName());
		}
	}

	/*
	This mainly exists due to the Fabric API loading classes but not resources until later, causing discrepancies with resources attempting to be loaded
	 */
	private ModInfo construct() {
		if (DEBUG) {
			val info = new JsonObject();
			info.addProperty("name", "Developer");
			info.addProperty("version", "0.0");
			info.add(ModEnvironment.getAuthorProperty(), new JsonArray());

			return new ModInfo(info);
		}
		else {
			val jar = new ZipFile(new File(StringUtils
					.substringBetween(this.getClass().getProtectionDomain().getCodeSource().getLocation()
							.getPath(), "file:", "!").replaceAll("%20", " ")));
			val stream = jar.getInputStream(jar.getEntry(ModEnvironment.getInfoJsonFilename()));
			val element = new JsonParser().parse(stream.bufferedReader());

			return new ModInfo(ModEnvironment.getInfoJsonObject(element));
		}
	}
}
