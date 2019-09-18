package org.frontear.mixins;

import net.minecraft.client.Minecraft;
import org.frontear.framework.utils.unsafe.MemoryPool;

public interface IMinecraft {
	static IMinecraft cast() {
		return (IMinecraft) Minecraft.getMinecraft();
	}

	MemoryPool getPool();
}
