package org.frontear.infinity.modules.impl;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.frontear.infinity.events.render.FontEvent;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public final class NameProtect extends Module {
	public NameProtect() {
		super(Keyboard.KEY_N, false);
	}

	@SubscribeEvent public void onFont(FontEvent event) {
		final String username = mc.getSession().getUsername();
		if (StringUtils.containsIgnoreCase(event.getText(), username)) {
			event.setText(StringUtils
					.replacePattern(event.getText(), String.format("(?i)%s", username), protect(username)));
		}
	}

	// todo: update to use EnumChatFormatting#OBFUSCATED instead
	private String protect(String username) {
		return Arrays.stream(ArrayUtils.toObject(username.toCharArray())).map(String::valueOf)
				.collect(Collectors.collectingAndThen(Collectors.toList(), x -> {
					Collections.shuffle(x);
					return String.join("", x);
				}));
	}
}
