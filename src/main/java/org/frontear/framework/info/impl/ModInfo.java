package org.frontear.framework.info.impl;

import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.frontear.framework.client.impl.Client;
import org.frontear.framework.environments.IEnvironment;
import org.frontear.framework.info.IModInfo;

/**
 * An implementation of {@link IModInfo}
 */
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class ModInfo implements IModInfo {
    @Getter String name, version, fullname, authors;

    // todo: allow custom property definitions (allow user to specify which property contains which information)

    /**
     * Loads a json file as a {@link JsonObject} to parse for {@link ModInfo} properties. It assumes
     * that certain properties exist (name, version, authorList/authors) If these properties do not
     * exist, this will error
     *
     * @param json {@link JsonObject} which is created when loading the specified json file in
     *             {@link Client} construction
     */
    public ModInfo(@NonNull final JsonObject json, @NonNull final IEnvironment environment) {
        this.name = json.get("name").getAsString();
        this.version = json.get("version").getAsString();
        this.fullname = "$name v$version";
        {
            val authorList = json.get(environment.getAuthorProperty()).getAsJsonArray();
            this.authors = authorList.joinToString(", ").replaceLast(", ", ", and ");
        }
    }
}
