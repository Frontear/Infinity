package org.frontear.framework.utils.unsafe.impl;

import org.frontear.framework.utils.unsafe.MemoryObject;
import sun.misc.Unsafe;

public class ShortMemoryObject extends MemoryObject<Short> {
	public ShortMemoryObject(long address, Unsafe unsafe) {
		super(address, unsafe);
	}

	@Override public Short get() {
		return unsafe.getShort(address);
	}

	@Override public void set(Short value) {
		unsafe.putShort(address, value);
	}
}
