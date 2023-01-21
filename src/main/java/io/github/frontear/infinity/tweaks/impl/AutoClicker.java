package io.github.frontear.infinity.tweaks.impl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.frontear.infinity.tweaks.AbstractTweak;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public final class AutoClicker extends AbstractTweak {
    @Expose
    @SerializedName("minimumClicksPerSecond")
    private int minCPS = 7;

    @Expose
    @SerializedName("maximumClicksPerSecond")
    private int maxCPS = 11;

    private long nanoTime = -1;
    private long targetNanos = -1;

    public AutoClicker() {
        super(GLFW.GLFW_KEY_R);
    }

    public boolean canAttack() {
        if (targetNanos == -1 || nanoTime == -1) {
            var isFirstCall = targetNanos == -1;

            // todo: assumption that max > min ALWAYS (orders are reversed because we reciprocate it)
            targetNanos = (long) (ThreadLocalRandom.current().nextDouble(1.0 / maxCPS, 1.0 / minCPS) * TimeUnit.SECONDS.toNanos(1L));
            nanoTime = System.nanoTime();

            if (isFirstCall) {
                return true; // otherwise run cps calculations
            }
        }

        if (System.nanoTime() - nanoTime >= targetNanos) {
            nanoTime = -1;
            return true;
        }

        return false;
    }
}
