package org.frontear.framework.config;

import lombok.NonNull;

/**
 * A configuration service which serializes registered objects (via {@link
 * IConfig#register(IConfigurable)})
 */
public interface IConfig {
    /**
     * Register an object for serialization
     *
     * @param object the configurable object that will be serialized when {@link IConfig#save()} is
     *               invoked
     */
    void register(final @NonNull IConfigurable<?> object);

    /**
     * Remove an object from registration
     *
     * @param object the configurable object that is to be removed
     */
    void unregister(final @NonNull IConfigurable<?> object);

    /**
     * Read the config file and attempt to deserialize a json property, then apply any {@link
     * com.google.gson.annotations.Expose} fields via {@link IConfigurable#load(IConfigurable)}
     */
    void load();

    /**
     * Serialize all registered {@link IConfigurable} into json objects via {@link
     * com.google.gson.Gson#toJsonTree(Object)}, then save them into the config file
     */
    void save();
}
