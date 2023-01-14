package io.github.frontear.infinity.mixins;

import com.mojang.authlib.GameProfile;
import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.SafeWalk;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LocalPlayer.class)
abstract class SafeWalkMixin extends Player {
    @SuppressWarnings("unused")
    public SafeWalkMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile, @Nullable ProfilePublicKey profilePublicKey) {
        super(level, blockPos, f, gameProfile, profilePublicKey);
    }

    @Override
    protected boolean isStayingOnGroundSurface() {
        return TweakManager.isTweakEnabled(SafeWalk.class) || super.isStayingOnGroundSurface();
    }
}
