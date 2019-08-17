package org.frontear.infinity.modules.impl;

import com.google.common.collect.Sets;
import net.minecraftforge.fml.common.Loader;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.Set;

public class Ghost extends Module {
	private static Ghost self;
	private Set<Module> unsafe = Sets.newHashSet();

	public Ghost() {
		super(Keyboard.KEY_G, true);

		self = this;
	}

	public static boolean active() {
		return self.isActive();
	}

	// we don't want to save active state, since Ghost should not persist through instances, and should rather manually be called
	@Override public void load(Module self) {
		super.setBind(super.getBind());
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
