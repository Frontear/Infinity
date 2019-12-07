package org.frontear.framework.environments.impl.forge;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.frontear.framework.environments.IEnvironment;

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
