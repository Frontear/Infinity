package org.frontear.infinity.commands;

import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.frontear.framework.manager.impl.Manager;
import org.frontear.infinity.events.ChatEvent;
import org.frontear.wrapper.IMinecraftWrapper;

import java.util.concurrent.atomic.AtomicBoolean;

public class CommandManager extends Manager<Command> {
	private static final ChatComponentText unknown = (ChatComponentText) new ChatComponentText("Unknown command.")
			.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED));
	private final String prefix = ".";

	public CommandManager() {
		reflectionSearch("org.frontear.infinity.commands.impl");
	}

	@SubscribeEvent public void onChat(ChatEvent event) {
		if (event.getMessage().startsWith(prefix)) {
			final String message = event.getMessage().trim().replaceFirst(prefix, "");
			final String[] split = message.split(" ");
			final AtomicBoolean found = new AtomicBoolean();

			getObjects().forEachRemaining(x -> {
				if (x.getName().equalsIgnoreCase(split[0])) {
					found.set(true);
					x.processCommand(ArrayUtils.remove(split, 0)); // we don't need first element
				}
			});

			if (!found.get()) {
				IMinecraftWrapper.getMinecraft().getChatGUI().printChatMessage(unknown);
				event.addToChat(false);
			}

			event.setMessage("");
		}
	}
}
