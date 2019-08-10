package org.frontear.infinity.config;

import com.google.common.collect.Sets;
import com.google.gson.*;
import org.frontear.framework.config.IConfig;
import org.frontear.framework.config.IConfigurable;
import org.frontear.infinity.logger.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Set;

public class Config implements IConfig {
	private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting()
			.create();
	private static final Logger logger = new Logger("Config");
	private final Set<IConfigurable<?>> configurables = Sets.newHashSet();
	private final File config_file;

	public Config(File config_file) {
		this.config_file = config_file;
	}

	@Override public void register(IConfigurable<?> object) {
		logger.debug("Registering configurable '%s', successful: %b", object.getName(), configurables.add(object));
	}

	@Override public void unregister(IConfigurable<?> object) {
		logger.debug("Unregistering configurable '%s', successful: %b", object.getName(), configurables.remove(object));
	}

	@Override public void load() {
		try (Reader reader = new FileReader(config_file)) {
			logger.debug("Loading config from %s", config_file.getAbsolutePath());
			final JsonObject config = gson.fromJson(reader, JsonObject.class);

			configurables.forEach(x -> {
				logger.debug("Lookup for '%s' in config", x.getName());
				if (config.has(x.getName())) {
					logger.debug("Loading '%s'", x.getName());
					x.load(gson.fromJson(config.get(x.getName()), (Type) x.getClass()));
				}
				else {
					logger.debug("Could not find '%s' in config (it will be added on saving)", x.getName());
				}
			});

			logger.info("Loaded successfully!");
		}
		catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				logger.debug("%s does not exist (will create)", config_file.getAbsolutePath());
				save();
			}
			else {
				e.printStackTrace();
			}
		}
	}

	@Override public void save() {
		try (Writer writer = new PrintWriter(config_file)) {
			final JsonObject config = new JsonObject();
			configurables.forEach(x -> {
				logger.debug("Saving '%s' to config", x.getName());
				config.add(x.getName(), gson.toJsonTree(x));
			});

			logger.debug("Writing config to %s", config_file.getAbsolutePath());
			gson.toJson(config, writer);
			logger.info("Saved successfully!");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
