package org.frontear.infinity.commands;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;
import org.frontear.framework.info.impl.ModInfo;
import org.frontear.framework.manager.impl.Manager;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.commands.ui.ConsoleGuiScreen;
import org.frontear.infinity.events.input.KeyEvent;
import org.frontear.infinity.modules.impl.Ghost;
import org.frontear.infinity.utils.ChatUtils;
import org.lwjgl.input.Keyboard;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class CommandManager extends Manager<Command> {
    KeyBinding bind;
    ChatComponentText prefix;
    ConsoleGuiScreen console = new ConsoleGuiScreen();

    public CommandManager(@NonNull ModInfo info) {
        super("org.frontear.infinity.commands.impl");

        this.bind = new KeyBinding("Console", Keyboard.KEY_GRAVE, info.getName());
        ClientRegistry.registerKeyBinding(bind);

        val gray = EnumChatFormatting.GRAY;
        this.prefix = ChatUtils
            .append(ChatUtils.make("[", gray),
                ChatUtils.make(info.getName(), EnumChatFormatting.GOLD), ChatUtils
                    .make("] ", gray));
    }

    @SubscribeEvent
    public void onKey(KeyEvent event) {
        if (!Infinity.inst().getModules().get(Ghost.class).isActive() && event.isPressed()
            && event.getKey() == bind
            .getKeyCode()) {
            Minecraft.getMinecraft().displayGuiScreen(console);
        }
    }

    public void processMessage(String text) {
        val split = text.split(" ");
        val potential = getObjects().filter(x -> x.getName().equalsIgnoreCase(split[0]))
            .findFirst();

        if (potential.isPresent()) {
            val command = potential.get();
            val args = ArrayUtils.remove(split, 0); // 0th index is just the command name
            if (args.length < command.getArguments()) {
                sendMessage("Too few arguments (min ${command.getArguments()})",
                    EnumChatFormatting.RED);
            }
            else {
                try {
                    command.process(args);
                }
                catch (Exception e) {
                    sendMessage("An unknown error has occured [${e.getSimpleName()}]",
                        EnumChatFormatting.RED);
                    e.printStackTrace();
                }
            }
        }
        else {
            sendMessage("Unknown command", EnumChatFormatting.ITALIC, EnumChatFormatting.RED);
        }
    }

    void sendMessage(String message, EnumChatFormatting... format) {
        val text = ChatUtils.make(message, format);

        console.print(ChatUtils.append(prefix, text));
    }
}
