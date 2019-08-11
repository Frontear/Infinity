package org.frontear.framework.info.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.frontear.framework.info.IModInfo;

public final class ModInfo implements IModInfo {
	private final String name, version, fullname, authors;

	public ModInfo(JsonObject mcmod) {
		this.name = mcmod.get("name").getAsString();
		this.version = mcmod.get("version").getAsString();
		this.fullname = String.format("%s %s", name, version);

		{
			final JsonArray authorList = mcmod.get("authorList").getAsJsonArray();
			final StringBuilder str = new StringBuilder();
			authorList.forEach(str::append);
			this.authors = replaceLast(String.join(", ", str.toString()), ", ", ", and ");
		}
	}

	private String replaceLast(String string, String lookup, String replacement) {
		int lastIndexOf = string.lastIndexOf(lookup);
		if (lastIndexOf > -1) { // found last instance of 'lookup'
			return string.substring(0, lastIndexOf) + replacement + string.substring(lastIndexOf + lookup.length());
		}
		else {
			return string;
		}
	}

	@Override public String getName() {
		return name;
	}

	@Override public String getVersion() {
		return version;
	}

	@Override public String getFullname() {
		return fullname;
	}

	@Override public String getAuthors() {
		return authors;
	}
}
