package org.frontear.framework.info.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.frontear.MinecraftMod;
import org.frontear.framework.client.impl.Client;
import org.frontear.framework.environment.ModdingEnvironment;
import org.frontear.framework.info.IModInfo;

/**
 * An implementation of {@link IModInfo}
 */
public final class ModInfo implements IModInfo {
	private final String name, version, fullname, authors;

	// todo: allow custom property definitions (allow user to specify which property contains which information)

	/**
	 * Loads a json file as a {@link JsonObject} to parse for {@link ModInfo} properties. It assumes that certain
	 * properties exist (name, version, authorList/authors) If these properties do not exist, this will error
	 *
	 * @param json {@link JsonObject} which is created when loading the specified json file in {@link Client}
	 *             construction
	 */
	public ModInfo(JsonObject json) {
		this.name = json.get("name").getAsString();
		this.version = json.get("version").getAsString();
		this.fullname = String.format("%s v%s", name, version);
		{
			final JsonArray authorList = json
					.get(MinecraftMod.getEnvironment() == ModdingEnvironment.FORGE ? "authorList" : "authors")
					.getAsJsonArray();
			final StringBuilder str = new StringBuilder();
			authorList.forEach(str::append);
			this.authors = replaceLast(String.join(", ", str.toString()), ", ", ", and ");
		}
	}

	@SuppressWarnings("SameParameterValue") private String replaceLast(String string, String lookup, String replacement) {
		int lastIndexOf = string.lastIndexOf(lookup);
		if (lastIndexOf > -1) { // found last instance of 'lookup'
			return string.substring(0, lastIndexOf) + replacement + string.substring(lastIndexOf + lookup.length());
		}
		else {
			return string;
		}
	}

	/**
	 * @return The name found from the specified json file
	 *
	 * @see IModInfo#getName()
	 */
	@Override public String getName() {
		return name;
	}

	/**
	 * @return The version found from the specified json file
	 *
	 * @see IModInfo#getVersion()
	 */
	@Override public String getVersion() {
		return version;
	}

	/**
	 * @return {@link ModInfo#getName()} + "v" + {@link ModInfo#getVersion()}
	 *
	 * @see IModInfo#getFullname()
	 */
	@Override public String getFullname() {
		return fullname;
	}

	/**
	 * @return The author(s) found from the specified json file, using the Oxford Comma
	 *
	 * @see IModInfo#getAuthors()
	 */
	@Override public String getAuthors() {
		return authors;
	}
}
