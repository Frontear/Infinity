package org.frontear.framework.environment;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface ModdingEnvironment {
	ModdingEnvironment FORGE = new ModdingEnvironment() {
		@Override public String getAuthorProperty() {
			return "authorList";
		}

		@Override public String getInfoJsonFile() {
			return "mcmod.info";
		}

		@Override public JsonObject getInfoJsonObject(JsonElement element) {
			return element.getAsJsonArray().get(0).getAsJsonObject(); // mcmod.info are wrapped in a large json array
		}
	};

	ModdingEnvironment FABRIC = new ModdingEnvironment() {
		@Override public String getAuthorProperty() {
			return "authors";
		}

		@Override public String getInfoJsonFile() {
			return "fabric.mod.json";
		}

		@Override public JsonObject getInfoJsonObject(JsonElement element) {
			return element.getAsJsonObject();
		}
	};

	String getAuthorProperty();

	String getInfoJsonFile();

	JsonObject getInfoJsonObject(final JsonElement element);
}
