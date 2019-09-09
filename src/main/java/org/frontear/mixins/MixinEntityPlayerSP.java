package org.frontear.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityPlayerSP.class) public abstract class MixinEntityPlayerSP extends Entity {
	public MixinEntityPlayerSP(World worldIn) {
		super(worldIn);
	}

	/**
	 * @author prplz
	 * @reason Please see https://prplz.io/mousedelayfix/ for more information
	 */
	@Override public Vec3 getLook(float partialTicks) {
		float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
		float f1 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * partialTicks;

		return this.getVectorForRotation(f, f1);
	}
}
