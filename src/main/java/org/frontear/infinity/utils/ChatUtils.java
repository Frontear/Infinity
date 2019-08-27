package org.frontear.infinity.utils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.util.*;
import org.apache.commons.lang3.StringUtils;
import org.frontear.framework.logger.impl.Logger;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ChatUtils {
	private static final String FORMAT_SYMBOL = String.valueOf('§');
	private static final Map<Character, EnumChatFormatting> formats;
	private static final Logger logger = new Logger();

	static {
		final Map<Character, EnumChatFormatting> temp = Maps.newHashMap();
		for (EnumChatFormatting format : EnumChatFormatting.values()) {
			temp.put(format.formattingCode, format);
		}

		formats = ImmutableMap.copyOf(temp);
	}

	public static ChatComponentText textFrom(String formatted) {
		final String[] split = formatted.split(FORMAT_SYMBOL);
		final ChatComponentText text = new ChatComponentText("");

		for (String string : split) {
			if (!string.isEmpty()) {
				final ChatComponentText sibling = new ChatComponentText(string.substring(1));
				setStyle(sibling.getChatStyle(), formats.get(string.charAt(0)));
				text.appendSibling(sibling);
			}
		}

		text.setChatStyle(styleFrom(text.getFormattedText()
				.substring(0, text.getFormattedText().length() - 2))); // ignore the reset flag at the end

		return text;
	}

	private static void setStyle(ChatStyle style, EnumChatFormatting format) {
		if (format != null) {
			if (format == EnumChatFormatting.RESET) {
				// based of rootStyle
				style.setColor(null);
				style.setObfuscated(false);
				style.setBold(false);
				style.setStrikethrough(false);
				style.setUnderlined(false);
				style.setItalic(false);
			}
			else if (format.isColor()) {
				style.setColor(format);
			}
			else if (format.isFancyStyling()) {
				switch (format) {
					case OBFUSCATED:
						style.setObfuscated(true);
						return;
					case BOLD:
						style.setBold(true);
						return;
					case STRIKETHROUGH:
						style.setStrikethrough(true);
						return;
					case UNDERLINE:
						style.setUnderlined(true);
						return;
					case ITALIC:
						style.setItalic(true);
						return;
					default:
						logger.fatal(new UnsupportedOperationException(), "Invalid option for ChatUtils#setStyle(ChatStyle, EnumChatFormatting) format %s", format
								.name());
				}
			}
		}
	}

	public static ChatStyle styleFrom(String formatted) {
		final ChatStyle style = new ChatStyle();
		final Matcher matcher = Pattern.compile(String.format("%s.", Pattern.quote(FORMAT_SYMBOL))).matcher(StringUtils
				.substringBefore(formatted, String
						.valueOf(EnumChatFormatting.RESET))); // removes the final reset code that exists from most ChatComponentText styles
		while (matcher.find()) {
			setStyle(style, formats.get(matcher.group().charAt(1)));
		}

		return style;
	}

	public static ChatComponentText append(ChatComponentText head, ChatComponentText... siblings) {
		final ChatComponentText parent = head.createCopy();
		Arrays.stream(siblings).forEach(parent::appendSibling);

		return parent;
	}

	public static ChatComponentText makeText(String message, EnumChatFormatting... format) {
		final ChatComponentText text = new ChatComponentText(message);
		if (format != null) {
			Arrays.stream(format).forEach(x -> setStyle(text.getChatStyle(), x));
		}

		return text;
	}
}
