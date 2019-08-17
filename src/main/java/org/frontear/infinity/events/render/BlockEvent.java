package org.frontear.infinity.events.render;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.eventhandler.Event;

public final class BlockEvent extends Event {
	private final Block block;
	private boolean render, side_check;

	public BlockEvent(Block block, boolean render, boolean side_check) {
		this.block = block;
		this.render = render;
		this.side_check = side_check;
	}

	public Block getBlock() {
		return block;
	}

	public boolean shouldRender() {
		return render;
	}

	public void setRender(boolean render) {
		this.render = render;
	}

	public boolean checkSide() {
		return side_check;
	}

	public void setSideCheck(boolean side_check) {
		this.side_check = side_check;
	}
}
