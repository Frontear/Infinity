package org.frontear.infinity.commands;

import lombok.NonNull;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.frontear.framework.info.impl.ModInfo;
import org.frontear.framework.manager.impl.Manager;
import org.frontear.infinity.commands.gui.ConsoleGuiScreen;
import org.frontear.infinity.events.input.KeyEvent;
import org.frontear.infinity.modules.impl.Ghost;
import org.frontear.infinity.utils.ChatUtils;
import org.frontear.wrapper.IMinecraftWrapper;
import org.lwjgl.input.Keyboard;

import java.util.Optional;

public final class CommandManager extends Manager<Command> {
	private final KeyBinding bind;
	private final ChatComponentText prefix;
	private final ConsoleGuiScreen console = new ConsoleGuiScreen();

	public CommandManager(@NonNull ModInfo info) {
		super("org.frontear.infinity.commands.impl");

		this.bind = new KeyBinding("Console", Keyboard.KEY_GRAVE, info.getName());
		ClientRegistry.registerKeyBinding(bind);

		final EnumChatFormatting gray = EnumChatFormatting.GRAY;
		this.prefix = ChatUtils
				.append(ChatUtils.make("[", gray), ChatUtils.make(info.getName(), EnumChatFormatting.GOLD), ChatUtils
						.make("] ", gray));
	}

	@SubscribeEvent public void onKey(KeyEvent event) {
		if (!Ghost.active() && event.isPressed() && event.getKey() == bind.getKeyCode()) {
			IMinecraftWrapper.getMinecraft().displayGuiScreen(console);
		}
	}

	public void processMessage(String text) {
		final String[] split = text.split(" ");
		final Optional<Command> potential = getObjects().filter(x -> x.getName().equalsIgnoreCase(split[0]))
				.findFirst();

		if (potential.isPresent()) {
			final Command command = potential.get();
			final String[] args = ArrayUtils.remove(split, 0); // 0th index is just the command name
			if (args.length < command.getArguments()) {
				sendMessage(String
						.format("Too few arguments (min %d)", command.getArguments()), EnumChatFormatting.RED);
			}
			else {
				try {
					command.process(args);
				}
				catch (Exception e) {
					sendMessage(String.format("An unknown error has occured [%s]", e.getClass()
							.getSimpleName()), EnumChatFormatting.RED);
					e.printStackTrace();
				}
			}
		}
		else {
			sendMessage("Unknown command", EnumChatFormatting.ITALIC, EnumChatFormatting.RED);
		}
	}

	void sendMessage(String message, EnumChatFormatting... format) {
		final ChatComponentText text = ChatUtils.make(message, format);

		console.print(ChatUtils.append(prefix, text));
	}
}
