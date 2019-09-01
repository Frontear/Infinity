package org.frontear.infinity.modules.impl;

import com.google.common.collect.Sets;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.frontear.framework.utils.system.LocalMachine;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.Set;

public final class Ghost extends Module {
	private static Ghost self = null;
	private final LocalMachine machine = new LocalMachine();
	private Set<Module> unsafe = Sets.newHashSet();
	private boolean obs = false;

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

	@Override public void setActive(boolean active) {
		super.setActive(obs || active);
		MinecraftForge.EVENT_BUS.register(this); // todo: prevent nonsense like this
	}

	@SubscribeEvent public void onTick(TickEvent.ClientTickEvent event) {
		this.obs = machine.getProcesses().containsValue("obs");
		if (!isActive() && obs) {
			this.setActive(true);
			Infinity.inst().getLogger()
					.warn("OBS Studio was detected. For your protection, Ghost will not disable until it is closed");
		}
	}
}
