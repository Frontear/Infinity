package org.frontear.infinity.events.entity;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

public class UpdateEvent extends Event {
	private final Entity entity;
	private final boolean pre;

	public UpdateEvent(Entity entity, boolean pre) {
		this.entity = entity;
		this.pre = pre;
	}

	public Entity getEntity() {
		return entity;
	}

	public boolean isPre() {
		return pre;
	}

	public boolean isPost() {
		return !pre;
	}
}
