package org.frontear.framework.utils.unsafe.impl;

import org.frontear.framework.utils.unsafe.MemoryObject;
import sun.misc.Unsafe;

public class ByteMemoryObject extends MemoryObject<Byte> {
	public ByteMemoryObject(long address, Unsafe unsafe) {
		super(address, unsafe);
	}

	@Override public Byte get() {
		return unsafe.getByte(address);
	}

	@Override public void set(Byte value) {
		unsafe.putByte(address, value);
	}
}
