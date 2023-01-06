package io.github.frontear.infinity.tweaks;

import io.github.frontear.infinity.tweaks.impl.AutoClicker;
import io.github.frontear.infinity.tweaks.impl.Fullbright;
import io.github.frontear.infinity.tweaks.impl.NoFOV;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class TweakManager {
    private final static Map<Class<? extends AbstractTweak>, AbstractTweak> tweaks = new HashMap<>(); // TODO: memory implications with high object counts

    static {
        tweaks.put(AutoClicker.class, new AutoClicker());
        tweaks.put(Fullbright.class, new Fullbright());
        tweaks.put(NoFOV.class, new NoFOV());
    }

    public static void handleKeyBinds(int key, int action) {
        var client = Minecraft.getInstance();

        if (client.screen == null && action == GLFW.GLFW_PRESS) {
            for (var tweak : tweaks.values()) {
                if (tweak.getKeyBind() == key) {
                    tweak.toggle();
                }
            }
        }
    }

    public static boolean isTweakEnabled(Class<? extends AbstractTweak> type) {
        if (tweaks.containsKey(type)) {
            return tweaks.get(type).isEnabled();
        }

        throw new NoSuchElementException("Cannot find the specified tweak (" + type.getSimpleName() + "). You may have forgotten to initialize it in the static constructor.");
    }
}
