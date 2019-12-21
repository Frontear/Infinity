package org.frontear.infinity.utils;

import com.google.common.collect.*;
import java.util.*;
import java.util.regex.Pattern;
import lombok.*;
import lombok.experimental.UtilityClass;
import net.minecraft.util.*;
import org.frontear.framework.logger.impl.Logger;

@UtilityClass
public class ChatUtils {
    private final String FORMAT_SYMBOL = "§";
    private final Map<Character, EnumChatFormatting> formats;
    private final Logger logger = new Logger();
    private final Pattern FORMAT_PATTERN = Pattern.compile("$FORMAT_SYMBOL.");

    static {
        val temp = Maps.<Character, EnumChatFormatting>newHashMap();
        for (val format : EnumChatFormatting.values()) {
            temp.put(format.formattingCode, format);
        }

        formats = ImmutableMap.copyOf(temp);
    }

    public ChatComponentText make(@NonNull final String text, final EnumChatFormatting... formats) {
        val component = new ChatComponentText(text);
        setStyle(component.getChatStyle(), formats);

        return component;
    }

    private void setStyle(final ChatStyle style, final EnumChatFormatting... formats) {
        if (formats != null) {
            Arrays.stream(formats).filter(Objects::nonNull).forEach(x -> {
                if (x == EnumChatFormatting.RESET) {
                    defaultStyle(style);
                }
                else if (x.isFancyStyling()) {
                    switch (x) {
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
                            logger.fatal(new UnsupportedOperationException(), "Invalid format %s",
                                x.name());
                    }
                }
                else if (x.isColor()) {
                    style.setColor(x);
                }
            });
        }
        else {
            defaultStyle(style); // no formats, let's at least cover some basic stuff
        }
    }

    private void defaultStyle(final ChatStyle style) {
        style.setColor(null);
        style.setObfuscated(false);
        style.setBold(false);
        style.setStrikethrough(false);
        style.setUnderlined(false);
        style.setItalic(false);
    }

    public ChatStyle styleFrom(@NonNull final String formatted) {
        val style = new ChatStyle();
        defaultStyle(style);

        if (!formatted.isEmpty() && formatted.contains(FORMAT_SYMBOL)) {
            val matcher = FORMAT_PATTERN.matcher(formatted);
            while (matcher.find()) {
                val found = matcher.group();
                setStyle(style, formats.get(found.charAt(1)));
            }
        }

        return style;
    }

    public ChatComponentText append(final ChatComponentText parent,
        final ChatComponentText... siblings) {
        val component = new ChatComponentText(
            ""); // acts as the parent, since if we directly append to parent, it'll remain there forever, and we don't want that

        component.appendSibling(parent);
        component.setChatStyle(
            parent.getChatStyle().createDeepCopy()); // we apply the parent styles onto our siblings
        Arrays.stream(siblings).forEach(component::appendSibling);

        return component;
    }

    public String replaceSymbol(@NonNull final String string) {
        return string.replace("&", FORMAT_SYMBOL);
    }
}
