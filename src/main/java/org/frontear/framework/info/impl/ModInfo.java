package org.frontear.framework.info.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.frontear.framework.client.impl.Client;
import org.frontear.framework.info.IModInfo;

/**
 * An implementation of {@link IModInfo}
 */
public final class ModInfo implements IModInfo {
	public static final byte FORGE = 0x0, FABRIC = 0x1;

	private final String name, version, fullname, authors;

	// todo: allow custom property definitions (allow user to specify which property contains which information)

	/**
	 * Loads a json file which should be parsed as an {@link JsonObject} It assumes that certain properties exist (name,
	 * version, authorList) If these properties do not exist, this will error
	 *
	 * @param json {@link JsonObject} which is created when loading the mcmod,info in {@link Client} construction
	 */
	public ModInfo(JsonObject json, byte type) {
		this.name = json.get("name").getAsString();
		this.version = json.get("version").getAsString();
		this.fullname = String.format("%s v%s", name, version);
		{
			final JsonArray authorList = json.get(type == FORGE ? "authorList" : type == FABRIC ? "authors" : null)
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
	 * @return The name found from the mcmod.info
	 *
	 * @see IModInfo#getName()
	 */
	@Override public String getName() {
		return name;
	}

	/**
	 * @return The version found from the mcmod.info
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
	 * @return The author(s) found from the mcmod.info, using the Oxford Comma
	 *
	 * @see IModInfo#getAuthors()
	 */
	@Override public String getAuthors() {
		return authors;
	}
}
