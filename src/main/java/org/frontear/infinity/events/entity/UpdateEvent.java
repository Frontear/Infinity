package org.frontear.infinity.events.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class UpdateEvent extends Event {
	@Getter Entity entity;
	boolean pre;

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
