package org.frontear.infinity.modules.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.render.BlockEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

// implementation in MixinMinecraft
@FieldDefaults(level = AccessLevel.PRIVATE) public final class Xray extends Module {
	boolean last_fullbright;

	public Xray() {
		super(Keyboard.KEY_X, false, Category.RENDER);
	}

	@SubscribeEvent public void onBlock(BlockEvent event) {
		event.setRender(event.getBlock() instanceof BlockOre || event.getBlock() instanceof BlockRedstoneOre);
		if (event.shouldRender()) {
			event.setSideCheck(false); // necessary so that block renders like a full cube
		}
	}


	@Override protected void onToggle(boolean active) {
		val instance = Infinity.inst().getModules().get(Fullbright.class);
		if (active) {
			last_fullbright = instance.isActive();
		}

		mc.renderGlobal.loadRenderers();

		instance.setActive(active || last_fullbright);
	}
}
