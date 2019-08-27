package org.frontear.infinity.modules.impl;

import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;
import org.frontear.infinity.events.render.FontEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.frontear.infinity.utils.ChatUtils;
import org.lwjgl.input.Keyboard;

public final class NameProtect extends Module {
	public NameProtect() {
		super(Keyboard.KEY_N, false, Category.RENDER);
	}

	@SubscribeEvent public void onFont(FontEvent event) {
		final String username = mc.getSession().getUsername();
		if (StringUtils.containsIgnoreCase(event.getText(), username)) {
			event.setText(StringUtils
					.replacePattern(event.getText(), String.format("(?i)%s", username), protect(username, event
							.getText())));
		}
	}

	private String protect(String username, String text) {
		final String before = StringUtils.substringBefore(text, username); // all the text before our username
		final ChatStyle style = ChatUtils.styleFrom(before); // sets the style from that past text
		final ChatComponentText obfuscated = (ChatComponentText) new ChatComponentText(username)
				.setChatStyle(style.createDeepCopy().setObfuscated(true));
		final String protect = ChatUtils
				.append(obfuscated, (ChatComponentText) new ChatComponentText("").setChatStyle(style.createDeepCopy()))
				.getFormattedText();

		return protect.substring(0, protect
				.lastIndexOf(String.valueOf(EnumChatFormatting.RESET))); // removes the trailing reset code
	}
}
