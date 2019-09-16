package org.frontear.framework.utils.unsafe.impl;

import org.frontear.framework.utils.unsafe.MemoryObject;
import sun.misc.Unsafe;

public class FloatMemoryObject extends MemoryObject<Float> {
	public FloatMemoryObject(long address, Unsafe unsafe) {
		super(address, unsafe);
	}

	@Override public Float get() {
		return unsafe.getFloat(address);
	}

	@Override public void set(Float value) {
		unsafe.putFloat(address, value);
	}
}
