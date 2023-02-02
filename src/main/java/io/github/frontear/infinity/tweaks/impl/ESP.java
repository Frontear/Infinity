package io.github.frontear.infinity.tweaks.impl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.frontear.infinity.tweaks.AbstractTweak;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class ESP extends AbstractTweak {
    @Expose
    @SerializedName("tracer")
    private boolean tracers = true;
    @Expose
    @SerializedName("colors")
    private Map<String, float[]> colors = new HashMap<>(); // TODO: allow custom entities and colors set by user

    public ESP() {
        super(GLFW.GLFW_KEY_H);

        for (var type : Registry.ENTITY_TYPE) {
            var key = Registry.ENTITY_TYPE.getKey(type).toString();

            if (type == EntityType.PLAYER) {
                colors.put(key, Color.WHITE.getRGBComponents(null));
            }
            else if (type.getCategory() != MobCategory.MISC) {
                switch (type.getCategory()) {
                    case MONSTER -> colors.put(key, Color.RED.getRGBComponents(null));
                    case AXOLOTLS, UNDERGROUND_WATER_CREATURE, WATER_CREATURE, WATER_AMBIENT ->
                            colors.put(key, Color.CYAN.getRGBComponents(null));
                    default -> colors.put(key, Color.ORANGE.getRGBComponents(null));
                }
            }
        }
    }

    public float[] getColor(Entity entity) {
        var key = Registry.ENTITY_TYPE.getKey(entity.getType()).toString();
        return colors.getOrDefault(key, Color.WHITE.getRGBComponents(null));
    }

    public boolean shouldDrawTracers() {
        return tracers;
    }
}