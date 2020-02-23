package com.github.frontear.framework.environments.impl.fabric;

import com.github.frontear.framework.environments.IEnvironment;
import com.google.gson.*;

public final class FabricEnvironment implements IEnvironment {
    @Override
    public String getName() {
        return "Fabric";
    }

    @Override
    public String getAuthorProperty() {
        return "authors";
    }

    @Override
    public String getInfoFilename() {
        return "fabric.mod.json";
    }

    @Override
    public JsonObject getInfoJsonObject(final JsonElement info) {
        return info.getAsJsonObject();
    }
}
