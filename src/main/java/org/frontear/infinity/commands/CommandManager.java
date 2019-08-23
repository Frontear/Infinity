package org.frontear.infinity.commands;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.framework.info.impl.ModInfo;
import org.frontear.framework.manager.impl.Manager;
import org.frontear.infinity.commands.gui.ConsoleGuiScreen;
import org.frontear.infinity.events.input.KeyEvent;
import org.frontear.wrapper.IMinecraftWrapper;
import org.lwjgl.input.Keyboard;

public final class CommandManager extends Manager<Command> {
	private final KeyBinding bind;
	private final ChatComponentText prefix;

	public CommandManager(ModInfo info) {
		super("org.frontear.infinity.commands.impl");

		this.bind = new KeyBinding("Console", Keyboard.KEY_GRAVE, info.getName());
		ClientRegistry.registerKeyBinding(bind);

		this.prefix = new ChatComponentText("");
		prefix.appendSibling(new ChatComponentText("[")
				.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
		prefix.appendSibling(new ChatComponentText(info.getName())
				.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GOLD)));
		prefix.appendSibling(new ChatComponentText("]")
				.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GRAY)));
		prefix.appendText(" ");
	}

	@SubscribeEvent public void onKey(KeyEvent event) {
		if (event.isPressed() && event.getKey() == bind.getKeyCode()) {
			IMinecraftWrapper.getMinecraft().displayGuiScreen(new ConsoleGuiScreen(prefix));
		}
	}
}
