package org.frontear.framework.utils.unsafe.impl;

import org.frontear.framework.utils.unsafe.MemoryObject;
import sun.misc.Unsafe;

public class DoubleMemoryObject extends MemoryObject<Double> {
	public DoubleMemoryObject(long address, Unsafe unsafe) {
		super(address, unsafe);
	}

	@Override public Double get() {
		return unsafe.getDouble(address);
	}

	@Override public void set(Double value) {
		unsafe.putDouble(address, value);
	}
}
