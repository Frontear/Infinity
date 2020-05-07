package com.github.frontear.infinity.modules.impl;

import com.github.frontear.efkolia.api.events.Listener;
import com.github.frontear.efkolia.utilities.randomizer.LocalRandom;
import com.github.frontear.efkolia.utilities.timing.Timer;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.event.state.TickEvent;
import com.github.frontear.infinity.mixins.IMinecraftClient;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.utils.keyboard.Keyboard;
import java.util.concurrent.TimeUnit;
import lombok.*;

@ModuleInfo(bind = Keyboard.KEY_R, friendly = true, category = ModuleCategory.COMBAT)
public final class AutoClicker extends Module {
    private final Timer timer;
    private final int min, max;

    public AutoClicker(@NonNull final InfinityMod infinity) {
        super(infinity);

        this.timer = new Timer();
        this.min = 11;
        this.max = 13;
    }

    @Listener
    private void onTick(@NonNull final TickEvent event) {
        if (event.isPre() && client.player != null && !client.player.isUsingItem()) {
            if (client.player.getAttackCooldownProgress(0.0F) == 1.0F) { // weapon is ready to swing
                val ms = timer.getElapsed(TimeUnit.MILLISECONDS) + LocalRandom
                    .nextInt(-25, 25); // force more randomness
                val elapsed = ms >= 1000 / LocalRandom.nextInt(min, max); // randomized intervals
                val attacking = client.options.keyAttack.isPressed();

                if (!attacking || elapsed) {
                    timer.reset();

                    if (attacking) {
                        logger.debug("Sending attack [%dms]", ms);
                        IMinecraftClient.from(client).doAttack();
                    }
                }
            }
        }
    }
}
