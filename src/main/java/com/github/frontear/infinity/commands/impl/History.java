package com.github.frontear.infinity.commands.impl;

import com.github.frontear.efkolia.utilities.network.Connection;
import com.github.frontear.efkolia.utilities.network.responses.JsonResponse;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.commands.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import lombok.*;

@Deprecated
@CommandInfo(desc = "Retrieves a specified player's old names")
public final class History extends Command {
    private final JsonResponse response = new JsonResponse();

    public History(@NonNull final InfinityMod infinity) {
        super(infinity);
    }

    @Override
    public void process(final String[] args) throws Exception {
        val unique_id = Connection
            .get("https://api.mojang.com/users/profiles/minecraft/" + args[0], response)
            .getAsJsonObject().get("id")
            .getAsString();
        val old_names = Connection
            .get("https://api.mojang.com/user/profiles/" + unique_id + "/names", response)
            .getAsJsonArray();

        if (old_names.size() > 1) {
            println("History for " + args[0]);

            for (int i = 0; i < old_names.size(); i++) {
                val object = old_names.get(i).getAsJsonObject();

                println((i + 1) + ". " + object.get("name").getAsString() + " on " + formatTime(
                    object.has("changedToAt") ? object.get("changedToAt").getAsLong() : 0));
            }
        }
        else {
            println("This player has never changed their name");
        }
    }

    @Override
    public String getUsage() {
        return "history <username>";
    }

    private String formatTime(final long time) {
        return time == 0 ? "unknown"
            : ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())
                .format(DateTimeFormatter.BASIC_ISO_DATE);
    }
}
