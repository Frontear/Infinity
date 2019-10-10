package org.frontear.infinity.commands.impl;

import lombok.NonNull;
import org.frontear.infinity.commands.Command;

public class Clear extends Command {
    public Clear() {
        super("Removes all messages from the minecraft chat");
    }

    @Override
    public void process(@NonNull String[] args) throws Exception {
        mc.ingameGUI.getChatGUI().clearChatMessages();
    }
}
