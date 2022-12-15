package io.github.frontear.infinity.tweaks;

import io.github.frontear.infinity.tweaks.impl.AutoClicker;
import io.github.frontear.infinity.tweaks.impl.Fullbright;

import java.util.*;

public class TweakManager {
    // TODO: do we want this to be static too?
    private final Map<Class<? extends AbstractTweak>, AbstractTweak> tweaks = new HashMap<>();
    public TweakManager() {
        // TODO: is automatic initialization better? it adds clutter, but it would simplify creation here
        tweaks.put(AutoClicker.class, new AutoClicker());
        tweaks.put(Fullbright.class, new Fullbright());
    }

    // TODO: how much exposure do we want to allow outside classes to have of our tweaks?
    public <T extends AbstractTweak> T find(Class<T> type) {
        if (tweaks.containsKey(type)) {
            return (T) tweaks.get(type); // this is fine
        }

        throw new NoSuchElementException("Can't find the specified tweak. You probably forgot to add it in the constructor.");
    }
}
