package org.frontear.mixins;

import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.infinity.events.entity.UpdateEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class) public abstract class MixinEntity {
	/**
	 * @param entity The entity that will be updated
	 *
	 * @author Frontear
	 * @see UpdateEvent
	 */
	@Redirect(method = "onUpdate",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/entity/Entity;onEntityUpdate()V")) private void onEntityUpdate(Entity entity) {
		MinecraftForge.EVENT_BUS.post(new UpdateEvent(entity, true));
		entity.onEntityUpdate();
		MinecraftForge.EVENT_BUS.post(new UpdateEvent(entity, false));
	}
}
