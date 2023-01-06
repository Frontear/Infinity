package io.github.frontear.infinity.tweaks.impl;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.frontear.infinity.tweaks.AbstractTweak;

public class NoFOV extends AbstractTweak {
    public NoFOV() {
        super(InputConstants.KEY_V);
    }
}
