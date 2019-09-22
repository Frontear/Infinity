package org.frontear.infinity.modules.impl;

import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class Ghost extends Module {
	Set<Module> unsafe = Sets.newHashSet();

	public Ghost() {
		super(Keyboard.KEY_G, true, Category.NONE);
	}

	@Override public void load(Module self) {
		this.setBind(self.getBind());
		this.setActive(false);
	}

	@Override protected void onToggle(boolean active) {
		Display.setTitle(active ? "Minecraft ${net.minecraftforge.fml.common.Loader.MC_VERSION}" : Infinity.inst()
				.getInfo().getFullname());
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
