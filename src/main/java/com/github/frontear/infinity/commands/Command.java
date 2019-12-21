package com.github.frontear.infinity.commands;

import com.github.frontear.infinity.Infinity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public abstract class Command {
    protected Minecraft mc = Minecraft.getMinecraft();
    @Getter String description;
    @Getter int arguments; // represents only the mandatory arguments

    public Command(@NonNull final String description) {
        this(description, 0);
    }

    public Command(@NonNull final String description, final int arguments) {
        this.description = description;
        this.arguments = arguments;
    }

    public abstract void process(@NonNull final String[] args) throws Exception; // catch all

    protected final void sendMessage(@NonNull final String message,
        final EnumChatFormatting... format) {
        Infinity.inst().getCommands().sendMessage(message, format);
    }

    public String getName() {
        return this.getSimpleName();
    }
}
