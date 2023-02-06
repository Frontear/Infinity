package io.github.frontear.infinity.mixins.tweaks.xray;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.Xray;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;

// FIXME: very dangerous high-level changes. be on the lookout for possible issues in extraneous locations
@Mixin(BlockState.class)
abstract class BlockStateMixin extends BlockBehaviour.BlockStateBase {
    protected BlockStateMixin(Block block, ImmutableMap<Property<?>, Comparable<?>> immutableMap, MapCodec<BlockState> mapCodec) {
        super(block, immutableMap, mapCodec);
    }

    @Override
    public boolean canOcclude() {
        if (TweakManager.get(Xray.class).isEnabled()) {
            return false;
        }

        return super.canOcclude();
    }
}