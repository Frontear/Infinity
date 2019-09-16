package org.frontear.framework.utils.unsafe.impl;

import org.frontear.framework.utils.unsafe.MemoryObject;
import sun.misc.Unsafe;

public class CharacterMemoryObject extends MemoryObject<Character> {
	public CharacterMemoryObject(long address, Unsafe unsafe) {
		super(address, unsafe);
	}

	@Override public Character get() {
		return unsafe.getChar(address);
	}

	@Override public void set(Character value) {
		unsafe.putChar(address, value);
	}
}
