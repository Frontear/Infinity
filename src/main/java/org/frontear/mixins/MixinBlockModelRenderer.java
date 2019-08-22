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
	/**
	 * @param blockAccessIn   Block access class
	 * @param modelIn         Block model
	 * @param blockStateIn    State of the block
	 * @param blockPosIn      Position of the block
	 * @param worldRendererIn The global world renderer
	 *
	 * @return true if rendered successfully, else false
	 *
	 * @author Frontear
	 * @see BlockEvent
	 */
	@Overwrite public boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn) {
		Block block = blockStateIn.getBlock();
		block.setBlockBoundsBasedOnState(blockAccessIn, blockPosIn);
		BlockEvent event = new BlockEvent(block, true, true);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.shouldRender()) {
			return this.renderModel(blockAccessIn, modelIn, blockStateIn, blockPosIn, worldRendererIn, event
					.checkSide()); // todo: fire event from deeper into the BlockModelRenderer, as the other methods are public, meaning rendering could be happening at a deeper level without us realizing
		}

		return false;
	}

	@Shadow public abstract boolean renderModel(IBlockAccess blockAccessIn, IBakedModel modelIn, IBlockState blockStateIn, BlockPos blockPosIn, WorldRenderer worldRendererIn, boolean checkSides);
}
