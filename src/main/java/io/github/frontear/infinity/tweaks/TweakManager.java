package io.github.frontear.infinity.tweaks;

import io.github.frontear.infinity.tweaks.impl.AutoClicker;
import io.github.frontear.infinity.tweaks.impl.FullBright;
import io.github.frontear.infinity.tweaks.impl.NoFOV;
import io.github.frontear.infinity.tweaks.impl.SafeWalk;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class TweakManager {
    private final static Map<Class<? extends AbstractTweak>, AbstractTweak> tweaks = new HashMap<>(); // TODO: memory implications with high object counts

    static {
        tweaks.put(AutoClicker.class, new AutoClicker());
        tweaks.put(FullBright.class, new FullBright());
        tweaks.put(NoFOV.class, new NoFOV());
        tweaks.put(SafeWalk.class, new SafeWalk());
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

    public static boolean isTweakEnabled(Class<? extends AbstractTweak> type) {
        if (tweaks.containsKey(type)) {
            return tweaks.get(type).enabled;
        }

        throw new NoSuchElementException("Cannot find the specified tweak (" + type.getSimpleName() + "). You may have forgotten to initialize it in the static constructor.");
    }
}
