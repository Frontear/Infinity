package org.frontear.infinity.modules.impl;

import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.execution.ShutdownEvent;
import org.frontear.infinity.events.render.BlockEvent;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

public class Xray extends Module {
	private int last_ambient;
	private boolean last_fullbright;

	public Xray() {
		super(Keyboard.KEY_X, false);
	}

	@SubscribeEvent public void onBlock(BlockEvent event) {
		event.setRender(event.getBlock() instanceof BlockOre || event.getBlock() instanceof BlockRedstoneOre);
		if (event.shouldRender()) {
			event.setSideCheck(false); // necessary so that block renders like a full cube
		}
	}

	@SubscribeEvent public void onShutdown(ShutdownEvent event) {
		mc.getGameSettings().ambientOcclusion = last_ambient; // don't want to save 0
		mc.getGameSettings().saveOptions();
	}


	@Override protected void onToggle(boolean active) {
		Fullbright instance = Infinity.inst().getModules().get(Fullbright.class);
		if (active) {
			last_ambient = mc.getGameSettings().ambientOcclusion;
			last_fullbright = instance.isActive();
		}

		mc.getGameSettings().ambientOcclusion = active ? 0 : last_ambient;
		mc.getRenderGlobal().loadRenderers();

		instance.setActive(active || last_fullbright);
	}
}
