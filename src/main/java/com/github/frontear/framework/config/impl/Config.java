package com.github.frontear.framework.config.impl;

import com.github.frontear.framework.config.*;
import com.github.frontear.framework.logger.impl.Logger;
import com.google.common.collect.Sets;
import com.google.gson.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    public Config(@NonNull final File config_file) {
        this.config_file = config_file;
    }

    /**
     * @see IConfig#register(IConfigurable)
     */
    @Override
    public void register(@NonNull final IConfigurable<?> object) {
        logger.debug(
            "Registering configurable '${object.getName()}', successful: ${configurables.add(object)}");
    }

    /**
     * @see IConfig#unregister(IConfigurable)
     */
    @Override
    public void unregister(@NonNull final IConfigurable<?> object) {
        logger.debug(
            "Unregistering configurable '${object.getName()}', successful: ${configurables.add(object)}");
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
            logger.debug("Loading config from ${config_file.getAbsolutePath()}");
            val config = new JsonParser().parse(reader).getAsJsonObject();

            configurables.forEach(x -> {
                logger.debug("Lookup for '${x.getName()}' in config");
                if (config.has(x.getName())) {
                    logger.debug("Loading '${x.getName()}'");
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
            logger.debug("${config_file.getAbsolutePath()} does not exist (will create)");
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
                logger.debug("Saving '${x.getName()}' to config");
                config.add(x.getName(), gson.toJsonTree(x));
            });

            logger.debug("Writing config to ${config_file.getAbsolutePath()}");
            gson.toJson(config, writer);
            logger.info("Saved successfully!");
        }
    }
}
