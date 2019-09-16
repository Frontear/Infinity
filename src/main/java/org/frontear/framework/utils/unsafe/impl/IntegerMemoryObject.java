package org.frontear.framework.utils.unsafe.impl;

import org.frontear.framework.utils.unsafe.MemoryObject;
import sun.misc.Unsafe;

public class IntegerMemoryObject extends MemoryObject<Integer> {
	public IntegerMemoryObject(long address, Unsafe unsafe) {
		super(address, unsafe);
	}

	public Integer get() {
		return unsafe.getInt(address);
	}

	public void set(Integer value) {
		unsafe.putInt(address, value);
	}
}
