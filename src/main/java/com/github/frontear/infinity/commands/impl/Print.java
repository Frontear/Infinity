package com.github.frontear.infinity.commands.impl;

import com.github.frontear.infinity.commands.Command;
import com.github.frontear.infinity.events.render.FontEvent;
import com.github.frontear.infinity.utils.ChatUtils;
import java.util.Arrays;
import lombok.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.StringUtils;

public final class Print extends Command {
    public Print() {
        super("Allows you to print a formatted message to the client side chat", 1);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void process(@NonNull final String[] args) throws Exception {
        val message = new StringBuilder();
        Arrays.stream(args).map(x -> "${x.trim()} ").forEach(message::append);

        val text = ChatUtils.replaceSymbol(message.toString());
        Minecraft.getMinecraft().ingameGUI.getChatGUI()
            .printChatMessage(new ChatComponentText(text));
    }

    @SubscribeEvent
    public void onFont(final FontEvent event) {
        if (StringUtils.startsWithIgnoreCase(event.getText(), getName())) {
            event.setText(
                ChatUtils.replaceSymbol(event.getText())); // let user see what they are typing
        }
    }
}
