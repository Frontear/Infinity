package org.frontear.infinity.events.entity;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.Event;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class UpdateEvent extends Event {
    private static Entity entity;
    boolean pre;

    public UpdateEvent(@NonNull final Entity entity, boolean pre) {
        UpdateEvent.entity = entity;
        this.pre = pre;
    }

    public Entity getEntity() {
        return entity;
    }

    public boolean isPost() {
        return !isPre();
    }

    public boolean isPre() {
        return pre;
    }
}
