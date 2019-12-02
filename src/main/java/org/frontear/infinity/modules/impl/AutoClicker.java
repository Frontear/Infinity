package org.frontear.infinity.modules.impl;

import java.util.concurrent.TimeUnit;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.frontear.framework.utils.rand.Random;
import org.frontear.framework.utils.time.Timer;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class AutoClicker extends Module {

    Timer timer = new Timer();
    int[] cps = { 11,
        13 }; // autoclicker does not actually hit these speeds, since they serve more so as a range

    public AutoClicker() {
        super(Keyboard.KEY_C, true, Category.COMBAT);
    }

    @SubscribeEvent
    public void onTick(final TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.player instanceof EntityPlayerSP
            && !event.player
            .isUsingItem()) {
            val ms = timer.getElapsed(TimeUnit.MILLISECONDS);
            val elapsed = ms >= 1000 / Random.nextInt(cps[0], cps[1]);
            val attacking = mc.gameSettings.keyBindAttack.isKeyDown();

            if (!attacking || elapsed) {
                timer.reset();

                if (attacking) {
                    logger.debug("Sending attack [${ms}ms]");
                    mc.clickMouse();
                }
            }
        }
    }
}
