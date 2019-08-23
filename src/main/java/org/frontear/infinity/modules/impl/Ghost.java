package org.frontear.infinity.modules.impl;

import com.google.common.collect.Sets;
import net.minecraftforge.fml.common.Loader;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.Set;

public final class Ghost extends Module {
	private static Ghost self = null;
	private Set<Module> unsafe = Sets.newHashSet();

	public Ghost() {
		super(Keyboard.KEY_G, true, Category.NONE);
		if (self == null) {
			self = this; // oh god gson
		}
	}

	public static boolean active() {
		return self.isActive();
	}

	@Override public void load(Module self) {
		this.setBind(self.getBind());
		this.setActive(false);
	}

	@Override protected void onToggle(boolean active) {
		Display.setTitle(active ? String.format("Minecraft %s", Loader.MC_VERSION) : Infinity.inst().getModInfo()
				.getFullname());
		if (active) {
			Infinity.inst().getModules().getObjects().filter(x -> !x.isSafe()).filter(Module::isActive).forEach(x -> {
				x.toggle();
				unsafe.add(x);
			});
		}
		else {
			unsafe.forEach(Module::toggle);
			unsafe.clear();
		}
	}
}
