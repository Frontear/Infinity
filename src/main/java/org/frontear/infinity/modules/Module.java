package org.frontear.infinity.modules;

import com.google.gson.annotations.Expose;
import lombok.*;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.framework.config.IConfigurable;
import org.frontear.wrapper.IMinecraftWrapper;

public abstract class Module implements IConfigurable<Module> {
	protected static final IMinecraftWrapper mc = IMinecraftWrapper.getMinecraft();
	@Getter private final boolean safe; // safe to use during Ghost
	@Getter private final Category category;
	@Expose @Getter @Setter private int bind;
	@Expose @Getter private boolean active = false;

	public Module(int bind, boolean safe, @NonNull Category category) {
		this.bind = bind;
		this.safe = safe;
		this.category = category;
	}

	@Override public void load(Module self) {
		this.setBind(self.getBind());
		this.setActive(self.isActive());
	}

	public void setActive(boolean active) {
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

	protected void onToggle(boolean active) {}

	public void toggle() {
		setActive(!isActive());
	}
}
