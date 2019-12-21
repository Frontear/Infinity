package com.github.frontear.mixins.impl;

import lombok.val;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.infinity.events.render.BlockEvent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(BlockModelRenderer.class)
public abstract class MixinBlockModelRenderer {
    /**
     * @param self       Instance of this
     * @param access     Block access class
     * @param model      Block model
     * @param state      State of the block
     * @param pos        Position of the block
     * @param renderer   The global world renderer
     * @param checkSides Calculate whether sides should be rendered if visible
     *
     * @return true if rendered successfully, else false
     *
     * @author Frontear
     * @reason {@link BlockEvent}
     */
    @Redirect(
        method = "renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;Lnet/minecraft/client/renderer/WorldRenderer;)Z",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModel(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/BlockPos;Lnet/minecraft/client/renderer/WorldRenderer;Z)Z"))
    private boolean renderModel(final BlockModelRenderer self, final IBlockAccess access,
        final IBakedModel model, final IBlockState state, final BlockPos pos,
        final WorldRenderer renderer, final boolean checkSides) {
        val block = state.getBlock();
        val event = new BlockEvent(block, true, checkSides);
        MinecraftForge.EVENT_BUS.post(event);

        if (event.shouldRender()) {
            return this.renderModel(access, model, state, pos, renderer, event.checkSide());
        }

        return false;
    }

    @Shadow
    public abstract boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn,
        IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn,
        boolean checkSides);
}
