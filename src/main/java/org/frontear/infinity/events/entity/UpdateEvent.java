package org.frontear.infinity.events.entity;

import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

public final class UpdateEvent extends Event {
	@Getter private final Entity entity;
	private final boolean pre;

	public UpdateEvent(Entity entity, boolean pre) {
		this.entity = entity;
		this.pre = pre;
	}

	public boolean isPost() {
		return !isPre();
	}

	public boolean isPre() {
		return pre;
	}
}
