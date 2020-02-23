package com.github.frontear.framework.environments.impl.forge;

import com.github.frontear.framework.environments.IEnvironment;
import com.google.gson.*;

public final class ForgeEnvironment implements IEnvironment {
    @Override
    public String getName() {
        return "Forge";
    }

    @Override
    public String getAuthorProperty() {
        return "authorList";
    }

    @Override
    public String getInfoFilename() {
        return "mcmod.info";
    }

    @Override
    public JsonObject getInfoJsonObject(final JsonElement info) {
        return info.getAsJsonArray().get(0).getAsJsonObject(); // forge wraps the file in a list
    }
}
