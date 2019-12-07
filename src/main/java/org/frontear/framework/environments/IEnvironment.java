package org.frontear.framework.environments;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Contains a broad implementation of modding environment specific information. All specific
 * implementation pertaining to only a single environment should be propagated from this to the
 * classes which extend it, and each class must safely provide access to mimic the same
 * functionality in a seamless manner.
 */
public interface IEnvironment {
    String getName();

    String getAuthorProperty();

    String getInfoFilename();

    JsonObject getInfoJsonObject(final JsonElement info);
}
