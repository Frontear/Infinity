package org.frontear.framework.config.impl;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.frontear.framework.config.IConfig;
import org.frontear.framework.config.IConfigurable;
import org.frontear.framework.logger.impl.Logger;

/**
 * An implementation of {@link IConfig}
 */
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class Config implements IConfig {
    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
        .serializeNulls()
        .enableComplexMapKeySerialization().setPrettyPrinting().create();
    Logger logger = new Logger();
    Set<IConfigurable<?>> configurables = Sets.newLinkedHashSet();
    File config_file;

    /**
     * Sets the file that will be written to via {@link Gson}
     *
     * @param config_file The file that will contain all {@link IConfigurable} objects
     */
    public Config(@NonNull File config_file) {
        this.config_file = config_file;
    }

    /**
     * @see IConfig#register(IConfigurable)
     */
    @Override
    public void register(@NonNull IConfigurable<?> object) {
        logger.debug("Registering configurable '%s', successful: %b", object.getName(),
            configurables.add(object));
    }

    /**
     * @see IConfig#unregister(IConfigurable)
     */
    @Override
    public void unregister(@NonNull IConfigurable<?> object) {
        logger.debug("Unregistering configurable '%s', successful: %b", object.getName(),
            configurables.remove(object));
    }

    /**
     * Reads the config file, parses it into a {@link JsonObject} using {@link
     * JsonParser#parse(Reader)}, and calls {@link IConfigurable#load(IConfigurable)} for each
     * {@link IConfigurable} it can find, by comparing {@link IConfigurable#getName()}
     *
     * @see IConfig#load()
     */
    @Override
    public void load() {
        try (val reader = new FileReader(config_file)) {
            logger.debug("Loading config from %s", config_file.getAbsolutePath());
            val config = new JsonParser().parse(reader).getAsJsonObject();

            configurables.forEach(x -> {
                logger.debug("Lookup for '%s' in config", x.getName());
                if (config.has(x.getName())) {
                    logger.debug("Loading '%s'", x.getName());
                    x.load(gson.fromJson(config.get(x.getName()), (Type) x.getClass()));
                }
                else {
                    logger.debug("Could not find '%s' in config (it will be added on saving)",
                        x.getName());
                }
            });

            logger.info("Loaded successfully!");
        }
        catch (FileNotFoundException e) {
            logger.debug("%s does not exist (will create)", config_file.getAbsolutePath());
            this.save();
        }
    }

    /**
     * Creates an empty {@link JsonObject}, adds all {@link IConfigurable} to the object by
     * converting them via {@link Gson#toJsonTree(Object)}, then saves it to the config file
     *
     * @see IConfig#save()
     */
    @Override
    public void save() {
        try (val writer = new PrintWriter(config_file)) {
            val config = new JsonObject();
            configurables.forEach(x -> {
                logger.debug("Saving '%s' to config", x.getName());
                config.add(x.getName(), gson.toJsonTree(x));
            });

            logger.debug("Writing config to %s", config_file.getAbsolutePath());
            gson.toJson(config, writer);
            logger.info("Saved successfully!");
        }
    }
}
