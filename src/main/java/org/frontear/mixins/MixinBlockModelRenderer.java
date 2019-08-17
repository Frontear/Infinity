package org.frontear.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.infinity.events.render.BlockEvent;
import org.spongepowered.asm.mixin.*;

@Mixin(BlockModelRenderer.class) public abstract class MixinBlockModelRenderer {
	@SuppressWarnings("OverwriteAuthorRequired") @Overwrite public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn) {
		Block block = blockStateIn.getBlock();
		block.setBlockBoundsBasedOnState(blockAccessIn, blockPosIn);
		BlockEvent event = new BlockEvent(block, true, true);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.shouldRender()) {
			return this
					.renderModel(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, event.checkSide());
		}

		return false;
	}

	@Shadow public abstract boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides);
}
