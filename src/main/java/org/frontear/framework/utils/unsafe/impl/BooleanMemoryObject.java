package org.frontear.framework.utils.unsafe.impl;

import org.frontear.framework.utils.unsafe.MemoryObject;
import sun.misc.Unsafe;

public class BooleanMemoryObject extends MemoryObject<Boolean> {
	public BooleanMemoryObject(long address, Unsafe unsafe) {
		super(address, unsafe);
	}

	@Override public Boolean get() {
		return unsafe.getByte(address) != 0;
	}

	@Override public void set(Boolean value) {
		unsafe.putByte(address, (byte) (value ? 1 : 0));
	}
}
