package org.frontear.infinity.commands.impl;

import com.google.common.collect.Lists;
import com.google.gson.*;
import lombok.NonNull;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.IOUtils;
import org.frontear.infinity.commands.Command;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class History extends Command {
	public History() {
		super("Look up the username history of a player. Name is case-sensitive", 1);
	}

	@Override public void process(@NonNull String[] args) throws Exception {
		final String username = args[0];

		new Thread(() -> {
			final JsonParser parser = new JsonParser();
			final String uuid = parser.parse(get("https://api.mojang.com/users/profiles/minecraft/" + username))
					.getAsJsonObject().get("id").getAsString();
			final JsonArray names = parser.parse(get("https://api.mojang.com/user/profiles/" + uuid + "/names"))
					.getAsJsonArray();
			final List<Object[]> history = Lists.newArrayList(); // ugh

			names.forEach(x -> {
				final JsonObject object = x.getAsJsonObject();
				history.add(new Object[] { object.get("name").getAsString(), object.has("changedToAt") ? object
						.get("changedToAt").getAsLong() : 0 });
			});

			if (history.size() > 1) {
				sendMessage(String.format("Name history for %s:", username));
				for (int i = 0; i < history.size(); i++) {
					final Object[] data = history.get(i);
					sendMessage(String.format("    %d. %s: %s", i + 1, data[0], normalizeDate((Long) data[1])));
				}
			}
			else {
				sendMessage("This player has never changed their name", EnumChatFormatting.RED);
			}
		}).start(); // this can take some time, as it's contacting an API
	}

	private String get(String url) {
		try {
			final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setRequestMethod("GET");

			final StringBuilder string = new StringBuilder();
			IOUtils.readLines(connection.getInputStream()).forEach(string::append);

			return string.toString();
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	private String normalizeDate(long time) {
		return time == 0 ? "unknown" : ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
				.format(DateTimeFormatter.ofPattern("E, MMM d, Y, hh:mm:ss a"));
	}
}
