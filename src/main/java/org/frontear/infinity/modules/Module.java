package org.frontear.infinity.modules;

import com.google.gson.annotations.Expose;
import lombok.*;
import lombok.experimental.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.framework.config.IConfigurable;
import org.frontear.framework.logger.impl.Logger;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public abstract class Module implements IConfigurable<Module> {
    protected static final Minecraft mc = Minecraft.getMinecraft();
    protected Logger logger = new Logger();
    @Getter boolean safe; // safe to use during Ghost
    @Getter Category category;
    @Expose @NonFinal @Getter @Setter int bind;
    @Expose @NonFinal @Getter boolean active = false;

    public Module(final int bind, final boolean safe, @NonNull final Category category) {
        this.bind = bind;
        this.safe = safe;
        this.category = category;
    }

    @Override
    public void load(final Module self) {
        this.setBind(self.getBind());
        this.setActive(self.isActive());
    }

    public void setActive(final boolean active) {
        if (active) {
            MinecraftForge.EVENT_BUS.register(this);
        }
        else {
            MinecraftForge.EVENT_BUS.unregister(this);
        }

        if (this.active != active) {
            onToggle(this.active = active);
        }
    }

    protected void onToggle(final boolean active) {
    }

    public void toggle() {
        setActive(!isActive());
    }
}
