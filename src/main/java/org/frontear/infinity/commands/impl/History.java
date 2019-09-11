package org.frontear.infinity.commands.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.gson.JsonParser;
import lombok.*;
import net.minecraft.util.EnumChatFormatting;
import org.apache.commons.io.IOUtils;
import org.frontear.infinity.commands.Command;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;

public final class History extends Command {
	public History() {
		super("Look up the username history of a player. Name is case-sensitive", 1);
	}

	@Override public void process(@NonNull String[] args) throws Exception {
		val username = args[0];

		new Thread(() -> {
			val parser = new JsonParser();
			val uuid = parser.parse(get("https://api.mojang.com/users/profiles/minecraft/$username")).getAsJsonObject()
					.get("id").getAsString();
			val names = parser.parse(get("https://api.mojang.com/user/profiles/$uuid/names")).getAsJsonArray();
			val history = Lists.<Object[]>newArrayList(); // ugh

			names.forEach(x -> {
				val object = x.getAsJsonObject();
				history.add(new Object[] { object.get("name").getAsString(), object.has("changedToAt") ? object
						.get("changedToAt").getAsLong() : 0 });
			});

			if (history.size() > 1) {
				sendMessage("Name history for $username:");
				for (var i = 0; i < history.size(); i++) {
					val data = history.get(i);
					sendMessage("    ${i + 1}. ${data[0]}: ${normalizeDate(data[1])}");
				}
			}
			else {
				sendMessage("This player has never changed their name", EnumChatFormatting.RED);
			}
		}).start(); // this can take some time, as it's contacting an API
	}

	private String get(String url) {
		val connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("GET");

		val string = new StringBuilder();
		IOUtils.readLines(connection.getInputStream()).forEach(string::append);

		return string.toString();
	}

	private String normalizeDate(Object time) {
		Preconditions.checkArgument(time instanceof Long);
		return ((Long) time) == 0 ? "unknown" : ZonedDateTime
				.ofInstant(Instant.ofEpochMilli((Long) time), ZoneId.systemDefault())
				.format(DateTimeFormatter.ofPattern("E, MMM d, Y, hh:mm:ss a"));
	}
}
