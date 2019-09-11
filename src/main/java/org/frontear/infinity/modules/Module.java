package org.frontear.infinity.modules;

import com.google.gson.annotations.Expose;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.framework.config.IConfigurable;
import org.frontear.wrapper.IMinecraftWrapper;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public abstract class Module implements IConfigurable<Module> {
	protected static final IMinecraftWrapper mc = IMinecraftWrapper.getMinecraft();
	@Getter boolean safe; // safe to use during Ghost
	@Getter Category category;
	@Expose @NonFinal @Getter @Setter int bind;
	@Expose @NonFinal @Getter boolean active = false;

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
