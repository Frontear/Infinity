package org.frontear.mixins;

import net.minecraft.block.Block;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.entity.UpdateEvent;
import org.frontear.infinity.modules.impl.SafeWalk;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(Entity.class) public abstract class MixinEntity {
	@Shadow public boolean noClip;
	@Shadow public World worldObj;
	@Shadow public double posX;
	@Shadow public double posY;
	@Shadow public double posZ;
	@Shadow public double motionX;
	@Shadow public double motionY;
	@Shadow public double motionZ;
	@Shadow public boolean onGround;
	@Shadow public float stepHeight;
	@Shadow public boolean isCollidedHorizontally;
	@Shadow public boolean isCollidedVertically;
	@Shadow public boolean isCollided;
	@Shadow public Entity ridingEntity;
	@Shadow public float distanceWalkedModified;
	@Shadow public float distanceWalkedOnStepModified;
	@Shadow public int fireResistance;
	@Shadow public boolean isDead;
	@Shadow public float prevDistanceWalkedModified;
	@Shadow public double prevPosX;
	@Shadow public double prevPosY;
	@Shadow public double prevPosZ;
	@Shadow public float prevRotationPitch;
	@Shadow public float rotationPitch;
	@Shadow public float prevRotationYaw;
	@Shadow public float rotationYaw;
	@Shadow public int timeUntilPortal;
	@Shadow public float fallDistance;
	@Shadow protected boolean isInWeb;
	@Shadow protected Random rand;
	@Shadow protected boolean inPortal;
	@Shadow protected int portalCounter;
	@Shadow protected boolean isImmuneToFire;
	@Shadow protected boolean firstUpdate;
	@Shadow private int nextStepDistance;
	@Shadow private int fire;
	private SafeWalk instance;

	/**
	 * @author Frontear
	 * @see UpdateEvent
	 */
	@Overwrite public void onEntityUpdate() {
		this.worldObj.theProfiler.startSection("entityBaseTick");
		MinecraftForge.EVENT_BUS.post(new UpdateEvent(self(), true));

		if (this.ridingEntity != null && this.ridingEntity.isDead) {
			this.ridingEntity = null;
		}

		this.prevDistanceWalkedModified = this.distanceWalkedModified;
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.prevRotationPitch = this.rotationPitch;
		this.prevRotationYaw = this.rotationYaw;

		if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
			this.worldObj.theProfiler.startSection("portal");
			MinecraftServer minecraftserver = ((WorldServer) this.worldObj).getMinecraftServer();
			int i = this.getMaxInPortalTime();

			if (this.inPortal) {
				if (minecraftserver.getAllowNether()) {
					if (this.ridingEntity == null && this.portalCounter++ >= i) {
						this.portalCounter = i;
						this.timeUntilPortal = this.getPortalCooldown();
						int j;

						if (this.worldObj.provider.getDimensionId() == -1) {
							j = 0;
						}
						else {
							j = -1;
						}

						this.travelToDimension(j);
					}

					this.inPortal = false;
				}
			}
			else {
				if (this.portalCounter > 0) {
					this.portalCounter -= 4;
				}

				if (this.portalCounter < 0) {
					this.portalCounter = 0;
				}
			}

			if (this.timeUntilPortal > 0) {
				--this.timeUntilPortal;
			}

			this.worldObj.theProfiler.endSection();
		}

		this.spawnRunningParticles();
		this.handleWaterMovement();

		if (this.worldObj.isRemote) {
			this.fire = 0;
		}
		else if (this.fire > 0) {
			if (this.isImmuneToFire) {
				this.fire -= 4;

				if (this.fire < 0) {
					this.fire = 0;
				}
			}
			else {
				if (this.fire % 20 == 0) {
					this.attackEntityFrom(DamageSource.onFire, 1.0F);
				}

				--this.fire;
			}
		}

		if (this.isInLava()) {
			this.setOnFireFromLava();
			this.fallDistance *= 0.5F;
		}

		if (this.posY < -64.0D) {
			this.kill();
		}

		if (!this.worldObj.isRemote) {
			this.setFlag(0, this.fire > 0);
		}

		this.firstUpdate = false;

		MinecraftForge.EVENT_BUS.post(new UpdateEvent(self(), false));
		this.worldObj.theProfiler.endSection();
	}

	private Entity self() { return (Entity) (Object) this; }

	@Shadow public abstract int getMaxInPortalTime();

	@Shadow public abstract int getPortalCooldown();

	@Shadow public abstract void travelToDimension(int dimensionId);

	@Shadow public abstract void spawnRunningParticles();

	@Shadow public abstract boolean handleWaterMovement();

	@Shadow public abstract boolean attackEntityFrom(DamageSource source, float amount);

	@Shadow public abstract boolean isInLava();

	@Shadow protected abstract void setOnFireFromLava();

	@Shadow protected abstract void kill();

	@Shadow protected abstract void setFlag(int flag, boolean set);

	/**
	 * @param entity The entity instance
	 *
	 * @return Whether the entity should be bound within the current block or not
	 *
	 * @author Frontear
	 * @see SafeWalk
	 */
	@Redirect(method = "moveEntity",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/entity/Entity;isSneaking()Z")) private boolean isSneaking(Entity entity) {
		if (instance == null) {
			instance = Infinity.inst().getModules().get(SafeWalk.class);
		}

		return instance.isActive() || isSneaking();
	}

	@Shadow public abstract boolean isSneaking();

	@Shadow public abstract AxisAlignedBB getEntityBoundingBox();

	@Shadow public abstract void setEntityBoundingBox(AxisAlignedBB bb);

	@Shadow protected abstract void resetPositionToBB();

	@Shadow protected abstract void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos);

	@Shadow protected abstract boolean canTriggerWalking();

	@Shadow public abstract boolean isInWater();

	@Shadow public abstract void playSound(String name, float volume, float pitch);

	@Shadow protected abstract String getSwimSound();

	@Shadow protected abstract void playStepSound(BlockPos pos, Block blockIn);

	@Shadow protected abstract void doBlockCollisions();

	@Shadow public abstract void addEntityCrashInfo(CrashReportCategory category);

	@Shadow public abstract boolean isWet();

	@Shadow protected abstract void dealFireDamage(int amount);

	@Shadow public abstract void setFire(int seconds);
}
