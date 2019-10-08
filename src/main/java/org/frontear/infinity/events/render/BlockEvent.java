package org.frontear.infinity.events.render;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.eventhandler.Event;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class BlockEvent extends Event {
	private static Block block;
	@NonFinal boolean render, side_check;

	public BlockEvent(Block block, boolean render, boolean side_check) {
		BlockEvent.block = block;
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
