package org.frontear.framework.utils.unsafe.impl;

import org.frontear.framework.utils.unsafe.MemoryObject;
import sun.misc.Unsafe;

public class LongMemoryObject extends MemoryObject<Long> {
	public LongMemoryObject(long address, Unsafe unsafe) {
		super(address, unsafe);
	}

	@Override public Long get() {
		return unsafe.getLong(address);
	}

	@Override public void set(Long value) {
		unsafe.putLong(address, value);
	}
}
