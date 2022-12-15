package io.github.frontear.infinity.tweaks;

import io.github.frontear.infinity.tweaks.impl.AutoClicker;
import io.github.frontear.infinity.tweaks.impl.Fullbright;

import java.util.*;

public class TweakManager {
    private final static Map<Class<? extends AbstractTweak>, AbstractTweak> tweaks = new HashMap<>(); // TODO: memory implications with high object counts

    static {
        tweaks.put(AutoClicker.class, new AutoClicker());
        tweaks.put(Fullbright.class, new Fullbright());
    }

    public static boolean isModEnabled(Class<? extends AbstractTweak> type) {
        if (tweaks.containsKey(type)) {
            return tweaks.get(type).isEnabled();
        }

        throw new NoSuchElementException("Cannot find the specified mod (" + type.getSimpleName() + "). You may have forgotten to initialize it in the static constructor.");
    }
}
