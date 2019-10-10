package org.frontear.framework.environment;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.frontear.MinecraftMod;

public final class ModEnvironment {
    public static final byte FORGE = 0x0, FABRIC = 0x1;
    private static final byte ENVIRONMENT = MinecraftMod.getEnvironment();
    private static final UnsupportedOperationException unsupported = new UnsupportedOperationException(
        "Environment 0x${Integer.toHexString(ENVIRONMENT)} is not supported [Use ModEnvironment environment values]");

    public static String getAuthorProperty() {
        switch (ENVIRONMENT) {
            case FORGE:
                return "authorList";
            case FABRIC:
                return "authors";
            default:
                throw unsupported;
        }
    }

    public static String getInfoJsonFilename() {
        switch (ENVIRONMENT) {
            case FORGE:
                return "mcmod.info";
            case FABRIC:
                return "fabric.mod.json";
            default:
                throw unsupported;
        }
    }

    public static JsonObject getInfoJsonObject(final JsonElement element) {
        switch (ENVIRONMENT) {
            case FORGE:
                return element.getAsJsonArray().get(0)
                    .getAsJsonObject(); // mcmod.info is wrapped in a list
            case FABRIC:
                return element.getAsJsonObject();
            default:
                throw unsupported;
        }
    }
}
