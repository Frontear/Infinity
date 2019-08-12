package org.frontear.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.infinity.events.ChatEvent;
import org.spongepowered.asm.mixin.*;

@Mixin(GuiScreen.class) public class MixinGuiScreen {
	@Shadow public Minecraft mc;

	/**
	 * @reason {@link org.frontear.infinity.events.ChatEvent}
	 * @author Frontear
	 */
	@Overwrite public void sendChatMessage(String msg, boolean addToChat) {
		final String old_msg = msg;
		{
			ChatEvent event = new ChatEvent(msg, addToChat);
			MinecraftForge.EVENT_BUS.post(event);
			msg = event.getMessage();
			addToChat = event.shouldAddToChat();
		}

		if (addToChat) {
			this.mc.ingameGUI.getChatGUI().addToSentMessages(old_msg);
		}
		if (net.minecraftforge.client.ClientCommandHandler.instance.executeCommand(mc.thePlayer, msg) != 0) {
			return;
		}
		if (!msg.isEmpty()) {
			this.mc.thePlayer.sendChatMessage(msg);
		}
	}
}
