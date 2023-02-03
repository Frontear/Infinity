package io.github.frontear.infinity.tweaks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class TweakManager {
    private static final Map<String, JsonObject> config = new HashMap<>();
    private static final Map<Class<? extends AbstractTweak>, AbstractTweak> tweaks = new HashMap<>();
    private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().serializeNulls().create();

    static {
        var path = FabricLoader.getInstance().getConfigDir().resolve("infinity.json");

        if (Files.exists(path)) {
            try (var reader = Files.newBufferedReader(path)) {
                var map = gson.fromJson(reader, JsonObject.class);

                if (map != null) {
                    for (var entry : map.entrySet()) {
                        config.put(entry.getKey(), entry.getValue().getAsJsonObject());
                    }
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try (var writer = Files.newBufferedWriter(path)) {
                var map = new JsonObject();

                for (var tweak : tweaks.entrySet()) {
                    map.add(tweak.getKey().getSimpleName(), gson.toJsonTree(tweak.getValue()));
                }

                gson.toJson(map, writer);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public static void renderEnabled(PoseStack poseStack) {
        var font = Minecraft.getInstance().font;

        var x = 0;
        var y = 0;

        var offset = 2;

        poseStack.pushPose();
        poseStack.scale(1.075f, 1.075f, 1.075f);

        for (var tweak : tweaks.values()) {
            if (tweak.isEnabled()) {
                font.drawShadow(poseStack, tweak.getClass().getSimpleName(), x + offset, y + offset, Color.WHITE.getRGB());
                y += font.lineHeight + offset;
            }
        }

        poseStack.popPose();
    }

    public static void handleKeyBinds(int key, int action) {
        var client = Minecraft.getInstance();

        if (client.screen == null && action == GLFW.GLFW_PRESS) {
            for (var tweak : tweaks.values()) {
                if (tweak.keybind == key) {
                    tweak.toggle();
                }
            }
        }
    }

    // TODO: consider the problems with allowing access outside of the controlled environment
    public static <T extends AbstractTweak> T get(Class<T> key) {
        if (!tweaks.containsKey(key)) {
            var tweak = gson.fromJson(config.getOrDefault(key.getSimpleName(), null), key);
            if (tweak == null) {
                try {
                    tweak = key.getConstructor().newInstance();
                }
                catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }

            tweaks.put(key, tweak);
        }

        return (T) tweaks.get(key);
    }
}
