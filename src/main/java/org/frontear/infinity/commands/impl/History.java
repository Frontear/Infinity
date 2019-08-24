package org.frontear.infinity.commands.impl;

import net.minecraft.util.EnumChatFormatting;
import org.frontear.infinity.commands.Command;
import org.shanerx.mojang.Mojang;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class History extends Command {
	public History() {
		super("Look up the username history of a player. Name is case-sensitive", 1);
	}

	@Override protected void process(String[] args) throws Exception {
		final AtomicReference<Exception> carrier = new AtomicReference<>(null);
		final Mojang mojang = new Mojang().connect();
		final String username = args[0];

		if (mojang.getStatus(Mojang.ServiceType.API_MOJANG_COM) == Mojang.ServiceStatus.GREEN) {
			new Thread(() -> {
				try {
					final Map<String, Long> history = mojang.getNameHistoryOfPlayer(mojang.getUUIDOfUsername(username));
					if (history.size() > 1) {
						sendMessage(String.format("History for %s:", username));
						history.forEach((k, v) -> sendMessage(String.format("	- %s: %s", k, normalizeDate(v))));
					}
					else {
						sendMessage(String.format("%s has never changed their username", username));
					}
				}
				catch (Exception e) {
					sendMessage(String.format("Failed to get username history [%s]", e.getClass()
							.getSimpleName()), EnumChatFormatting.RED);
					e.printStackTrace();
				}
			}).start(); // this can take some time, as it's contacting an API
		}
		else {
			sendMessage("The mojang api service is currently unavailable", EnumChatFormatting.RED);
		}
	}

	private String normalizeDate(long time) {
		return ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
				.format(DateTimeFormatter.ofPattern("E, MMM d, Y, hh:mm:ss a"));
	}
}
