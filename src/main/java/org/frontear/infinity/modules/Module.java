package org.frontear.infinity.modules;

import com.google.gson.annotations.Expose;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.framework.config.IConfigurable;
import org.frontear.wrapper.IMinecraftWrapper;

public abstract class Module implements IConfigurable<Module> {
	protected static final IMinecraftWrapper mc = IMinecraftWrapper.getMinecraft();
	private final boolean safe; // safe to use during Ghost
	private final Category category;
	@Expose private int bind;
	@Expose private boolean active = false;

	public Module(int bind, boolean safe, Category category) {
		this.bind = bind;
		this.safe = safe;
		this.category = category;
	}

	@Override public void load(Module self) {
		this.setBind(self.getBind());
		this.setActive(self.isActive());
	}

	public int getBind() {
		return bind;
	}

	public void setBind(int bind) {
		this.bind = bind;
	}

	public boolean isActive() {
		return active;
	}

	protected void onToggle(boolean active) {}

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

	public boolean isSafe() {
		return safe;
	}

	public void toggle() {
		setActive(!isActive());
	}

	public Category getCategory() {
		return category;
	}
}
