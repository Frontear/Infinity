package com.github.frontear.infinity.commands.impl;

import com.github.frontear.infinity.commands.Command;
import lombok.NonNull;

public final class Clear extends Command {
    public Clear() {
        super("Removes all messages from the minecraft chat");
    }

    @Override
    public void process(@NonNull final String[] args) throws Exception {
        mc.ingameGUI.getChatGUI().clearChatMessages();
    }
}
