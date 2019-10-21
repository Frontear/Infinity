package org.frontear.framework.environments;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface IEnvironment {
    String getName();

    String getAuthorProperty();

    String getInfoFilename();

    JsonObject getInfoJsonObject(final JsonElement info);
}
