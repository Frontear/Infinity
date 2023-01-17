package io.github.frontear.infinity.tweaks.impl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.frontear.infinity.tweaks.AbstractTweak;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.lwjgl.glfw.GLFW;

import java.util.Set;

public final class Xray extends AbstractTweak {
    @Expose
    @SerializedName("list")
    private static Set<String> exclusions = Set.of(
            Registry.BLOCK.getKey(Blocks.COAL_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.COPPER_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.DIAMOND_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.EMERALD_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.GOLD_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.IRON_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.LAPIS_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.REDSTONE_ORE).toString(),

            Registry.BLOCK.getKey(Blocks.DEEPSLATE_COAL_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.DEEPSLATE_COPPER_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.DEEPSLATE_DIAMOND_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.DEEPSLATE_EMERALD_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.DEEPSLATE_GOLD_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.DEEPSLATE_IRON_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.DEEPSLATE_LAPIS_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.DEEPSLATE_REDSTONE_ORE).toString(),

            Registry.BLOCK.getKey(Blocks.RAW_COPPER_BLOCK).toString(),
            Registry.BLOCK.getKey(Blocks.RAW_GOLD_BLOCK).toString(),
            Registry.BLOCK.getKey(Blocks.RAW_IRON_BLOCK).toString(),

            Registry.BLOCK.getKey(Blocks.ANCIENT_DEBRIS).toString(),
            Registry.BLOCK.getKey(Blocks.NETHER_GOLD_ORE).toString(),
            Registry.BLOCK.getKey(Blocks.NETHER_QUARTZ_ORE).toString()
    );

    public Xray() {
        super(GLFW.GLFW_KEY_X);
    }

    // TODO: allow users to pick, while providing a default list of vanilla ores
    public static boolean isExcluded(Block block) {
        return exclusions.contains(Registry.BLOCK.getKey(block).toString());
    }

    @Override
    public void toggle() {
        super.toggle();

        Minecraft.getInstance().levelRenderer.allChanged();
    }
}
