package com.github.frontear.infinity.modules.ux;

import com.github.frontear.infinity.modules.Module;
import java.awt.Color;
import lombok.NonNull;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public final class ModuleLabel extends AbstractLabel {
    private static final int INACTIVE;
    private static final int ACTIVE;

    static {
        INACTIVE = new Color(54, 71, 96).getRGB();
        ACTIVE = new Color(7, 152, 252).getRGB();
    }

    private final Module module;

    public ModuleLabel(@NonNull final Module module, final int x, final int y, final int width,
        final int height) {
        super(module.getPropertyName(), x, y, width, height);
        this.module = module;
    }

    @Override
    public void render(final MatrixStack matrices, final int mouseX, final int mouseY,
        final float delta) {
        DrawableHelper.fill(matrices, x, y, x + width, y + height,
            module.isActive() ? ACTIVE : INACTIVE);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(final double mouseX, final double mouseY, final int button) {
        if (mouseX >= x && mouseY >= y && mouseX < x + width
            && mouseY < y + height) { // boundary check
            if (button == 0) { // left click
                module.toggle();
            }
        }

        return true;
    }
}
