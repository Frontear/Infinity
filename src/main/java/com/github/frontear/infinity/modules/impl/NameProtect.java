package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.events.render.FontEvent;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.utils.ChatUtils;
import lombok.val;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.input.Keyboard;

public final class NameProtect extends Module {
    public NameProtect() {
        super(Keyboard.KEY_N, false, Category.RENDER);
    }

    @SubscribeEvent
    public void onFont(final FontEvent event) {
        val username = mc.getSession().getUsername();
        if (StringUtils.containsIgnoreCase(event.getText(), username)) {
            event.setText(StringUtils
                .replacePattern(event.getText(), "(?i)$username",
                    protect(username, event.getText())));
        }
    }

    private String protect(final String username, String text) {
        val before = StringUtils
            .substringBefore(text, username); // all the text before our username
        val style = ChatUtils.styleFrom(before); // sets the style from that past text
        val obfuscated = (ChatComponentText) new ChatComponentText(username)
            .setChatStyle(style.createDeepCopy().setObfuscated(true));
        val protect = ChatUtils
            .append(obfuscated,
                (ChatComponentText) new ChatComponentText("").setChatStyle(style.createDeepCopy()))
            .getFormattedText();

        return protect.substring(0, protect
            .lastIndexOf(
                String.valueOf(EnumChatFormatting.RESET))); // removes the trailing reset code
    }
}
