package org.frontear.infinity.commands;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import org.frontear.infinity.Infinity;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public abstract class Command {
    protected Minecraft mc = Minecraft.getMinecraft();
    @Getter String description;
    @Getter int arguments; // represents only the mandatory arguments

    public Command(@NonNull String description) {
        this(description, 0);
    }

    public Command(@NonNull String description, int arguments) {
        this.description = description;
        this.arguments = arguments;
    }

    public abstract void process(@NonNull String[] args) throws Exception; // catch all

    protected final void sendMessage(@NonNull String message, EnumChatFormatting... format) {
        Infinity.inst().getCommands().sendMessage(message, format);
    }

    public String getName() {
        return this.getSimpleName();
    }
}
