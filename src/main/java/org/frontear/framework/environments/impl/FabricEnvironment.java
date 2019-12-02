package org.frontear.framework.environments.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.frontear.framework.environments.IEnvironment;

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
