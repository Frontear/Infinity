package org.frontear.mixins.impl;

import lombok.val;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.entity.UpdateEvent;
import org.frontear.infinity.modules.impl.SafeWalk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class) public abstract class MixinEntity {
	/**
	 * @author Frontear
	 * @reason {@link UpdateEvent}
	 */
	@Inject(method = "onEntityUpdate",
			id = "updateEvent",
			at = { @At(value = "HEAD",
					id = "pre"), @At(value = "TAIL",
					id = "post") }) private void onEntityUpdate(CallbackInfo info) {
		val self = (Entity) (Object) this;

		if (info.getId().equals("updateEvent:pre")) {
			MinecraftForge.EVENT_BUS.post(new UpdateEvent(self, true));
		}
		else if (info.getId().equals("updateEvent:post")) {
			MinecraftForge.EVENT_BUS.post(new UpdateEvent(self, false));
		}
	}

	/**
	 * @param entity The entity instance
	 *
	 * @return Whether the entity should be bound within the current block or not
	 *
	 * @author Frontear
	 * @reason {@link SafeWalk}
	 */
	@Redirect(method = "moveEntity",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/entity/Entity;isSneaking()Z")) private boolean isSneaking(Entity entity) {
		return Infinity.inst().getModules().get(SafeWalk.class).isActive() || entity.isSneaking();
	}
}