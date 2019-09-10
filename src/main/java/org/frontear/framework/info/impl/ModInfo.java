package org.frontear.framework.info.impl;

import com.google.gson.JsonObject;
import lombok.*;
import org.frontear.framework.client.impl.Client;
import org.frontear.framework.environment.ModEnvironment;
import org.frontear.framework.info.IModInfo;

/**
 * An implementation of {@link IModInfo}
 */
public final class ModInfo implements IModInfo {
	@Getter private final String name, version, fullname, authors;

	// todo: allow custom property definitions (allow user to specify which property contains which information)

	/**
	 * Loads a json file as a {@link JsonObject} to parse for {@link ModInfo} properties. It assumes that certain
	 * properties exist (name, version, authorList/authors) If these properties do not exist, this will error
	 *
	 * @param json {@link JsonObject} which is created when loading the specified json file in {@link Client}
	 *             construction
	 */
	public ModInfo(@NonNull JsonObject json) {
		this.name = json.get("name").getAsString();
		this.version = json.get("version").getAsString();
		this.fullname = String.format("%s v%s", name, version);
		{
			val authorList = json.get(ModEnvironment.getAuthorProperty()).getAsJsonArray();
			val str = new StringBuilder();
			authorList.forEach(str::append);
			this.authors = replaceLast(String.join(", ", str.toString()), ", ", ", and ");
		}
	}

	@SuppressWarnings("SameParameterValue") private String replaceLast(String string, String lookup, String replacement) {
		val lastIndexOf = string.lastIndexOf(lookup);
		if (lastIndexOf > -1) { // found last instance of 'lookup'
			return string.substring(0, lastIndexOf) + replacement + string.substring(lastIndexOf + lookup.length());
		}
		else {
			return string;
		}
	}
}
